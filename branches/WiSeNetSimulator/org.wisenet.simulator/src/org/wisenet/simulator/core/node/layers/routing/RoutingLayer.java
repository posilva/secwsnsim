package org.wisenet.simulator.core.node.layers.routing;

import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksList;
import java.util.LinkedList;
import java.util.List;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument;
import org.wisenet.simulator.components.instruments.IInstrumentHandler;
import org.wisenet.simulator.components.instruments.IInstrumentMessage;
import org.wisenet.simulator.components.instruments.latency.LatencyInstrument;
import org.wisenet.simulator.components.instruments.reliability.ReliabilityInstrument;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.node.layers.Layer;
import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksEntry;
import org.wisenet.simulator.core.node.layers.routing.attacks.IRoutingAttack;
import org.wisenet.simulator.utilities.Utilities;

public abstract class RoutingLayer extends Layer {

    protected static RoutingLayerController routingController = new RoutingLayerController();
    /**
     * List of the attacks implementation
     */
    protected AttacksList attacks;
    /**
     * This list keeps the multiple routing layer phases
     */
    protected List<String> protocolPhases = new LinkedList<String>();
    /**
     * Current phase
     */
    protected String currentPhase = null;
    /**
     * Reference to the application level
     */
    protected Application application = null;
    /**
     * Flag for controlling routing stability (ready for routing)
     */
    private boolean stable = false;

    /**
     * Default constructor
     */
    public RoutingLayer() {
        super();
        prepareRouting();
        prepareAttacks();
    }

    /**
     * Gets the current phase of the routing protocol
     * @return
     */
    public String getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Sets the current phase of the routing protocol
     * @param currentPhase
     */
    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    /**
     * Gets if the routing protocol is stable (ready to route messages)
     * @return
     */
    public boolean isStable() {
        return stable;
    }

    /**
     * Sets if the routing protocol is stable (ready to route messages)
     * @param stable
     */
    public final void setStable(boolean stable) {
        boolean changed = this.stable != stable;
        boolean oldValue = this.stable;
        this.stable = stable;
        if (routingController != null) {
            if (stable) {
                routingController.registerAsStable(this);
            } else {
                routingController.unregisterAsStable(this);
            }
        }
        /**
         * Notify stable handlers
         */
        if (changed) {
            onStable(oldValue);
        }
    }

    /**
     * Gets the routing related application
     * @return
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets the routing related application
     * @param application
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Execute procedures for enable routing attacks handler
     */
    private void prepareAttacks() {
        attacks = new AttacksList(this);
        initAttacks();
    }

    /**
     * This method enables the instrumentation to perform the
     * controllers notification
     * @param object
     */
    public void receivedMessageHandler(Object message) {
        try {
            ((Message) message).hop(); // increment one hop
            Message m = (Message) ((Message) message).clone();
            receiveMessage(m);
        } catch (CloneNotSupportedException ex) {
            Utilities.handleException(ex);
        }
    }

    /**
     * Route a message
     * @param message
     */
    protected void routeMessage(Object message) {
        onRouteMessage(message);
    }

    /**
     * Handles the message reception action
     * @param message
     */
    public final void receiveMessage(Object message) {
        if (this instanceof IInstrumentHandler) {
            if (message instanceof IInstrumentMessage) {
                getCoverageInstrument().notifyMessageReceived((IInstrumentMessage) message, (IInstrumentHandler) this);
                getReliabilityInstrument().notifyMessageReceived((IInstrumentMessage) message, (IInstrumentHandler) this);
                getLatencyInstrument().notifyMessageReceived((IInstrumentMessage) message, (IInstrumentHandler) this);
            }
        }
        onReceiveMessage(message);
    }

    /**
     * Handles a message sent that comes from a top layer (application)
     * @param message
     * @param app
     * @return
     */
    public boolean sendMessage(Object message, Application app) {
        if (this instanceof IInstrumentHandler) {
            if (message instanceof IInstrumentMessage) {
                getCoverageInstrument().notifyMessageSent((IInstrumentMessage) message, (IInstrumentHandler) this);
                getReliabilityInstrument().notifyMessageSent((IInstrumentMessage) message, (IInstrumentHandler) this);
                getLatencyInstrument().notifyMessageSent((IInstrumentMessage) message, (IInstrumentHandler) this);
            }
        }
        boolean result = onSendMessage(message, app);
        return result;
    }

    /**
     * Controls if the message can be sent to the air
     * here you can do same prepare work
     * @param message
     * @return
     */
    private Object beforeSendMessageToAir(Object message) {
        if (isUnderAttack()) {
            return doAttack(message);
        }
        return message;
    }

    /**
     * Utility method
     * @return
     */
    protected CoverageInstrument getCoverageInstrument() {
        return getNode().getSimulator().getSimulation().getCoverageInstrument();
    }

    /**
     * Utility method
     * @return
     */
    protected ReliabilityInstrument getReliabilityInstrument() {
        return getNode().getSimulator().getSimulation().getReliabilityInstrument();
    }

    /**
     * Utility method
     * @return
     */
    protected LatencyInstrument getLatencyInstrument() {
        return getNode().getSimulator().getSimulation().getLatencyInstrument();
    }

    public AttacksList getAttacks() {
        return attacks;
    }

    /**
     * Sent message to the mac layer ( MUST BE used in every routing
     * implementation because enables to intercept routing attacks feature)
     * @param message
     */
    protected final void send(Object message) {
        if (beforeSendMessageToAir(message) != null) {
            sendMessageToAir(message);
        }
    }

    /**
     * Handles a attack procedure
     * @param message
     * @return
     */
    protected Object doAttack(Object message) {
        if (!isUnderAttack()) {
            return message;
        }
        Object attackedMessage = null;
        // TODO: maybe we only considered the first enabled attack (MUST REVIEW)
        for (AttacksEntry ae : attacks.getAttacksList()) {
            attackedMessage = message;
            if (ae.isEnable()) {
                IRoutingAttack attack = (IRoutingAttack) ae.getAttack();
                attackedMessage = attack.attack(attackedMessage);
                if (attackedMessage == null) // once the message was disapear
                {
                    break;                 // we dont keep doing attacks
                }
            }
        }
        return attackedMessage;
    }

    /**
     * Returns the routing table in string format
     * @return
     */
    protected abstract String getRoutingTable();

    /**
     * Handle messageReception from bottom layer
     * @param message
     */
    protected abstract void onReceiveMessage(Object message);

    /**
     * Handle messageSent from up layer
     * @param message
     * @param app
     * @return
     */
    protected abstract boolean onSendMessage(Object message, Application app);

    /**
     * Handle a message route
     * @param message
     */
    protected abstract void onRouteMessage(Object message);

    /**
     * Setting up the attacks implemented
     */
    protected abstract void setupAttacks();

    /**
     * Occurs when the routing protocol sends message to air
     * @param message
     */
    protected abstract void sendMessageToAir(Object message);

    /**
     * Initialize a new round of the routing protocol
     */
    public abstract void newRound();

    /**
     * Handle the message sent signal
     */
    public abstract void sendMessageDone();

    /**
     * Setup stuff for routing protocol
     */
    protected abstract void setup();

    public final void startup() {
        setup();

    }

    private void prepareRouting() {
    }

    public List<String> getProtocolPhases() {
        return protocolPhases;
    }

    public static RoutingLayerController getController() {
        return routingController;
    }

    /**
     * Do some action after switch to stable
     */
    protected abstract void onStable(boolean oldValue);

    /**
     * Initialize attacks in each node
     */
    protected abstract void initAttacks();
}