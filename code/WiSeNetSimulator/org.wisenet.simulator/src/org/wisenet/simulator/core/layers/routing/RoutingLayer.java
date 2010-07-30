package org.wisenet.simulator.core.layers.routing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument;
import org.wisenet.simulator.components.instruments.IInstrumentHandler;
import org.wisenet.simulator.components.instruments.IInstrumentMessage;
import org.wisenet.simulator.components.instruments.reliability.ReliabilityInstrument;
import org.wisenet.simulator.core.application.Application;
import org.wisenet.simulator.core.engine.Message;
import org.wisenet.simulator.core.layers.Layer;

public abstract class RoutingLayer extends Layer {

    protected List<String> protocolPhase = new LinkedList<String>();
    protected String currentPhase = null;
    protected boolean inAttackMode = false;
    protected Application application = null;
    protected boolean autostarted;
    protected boolean stable = false;
    protected RoutingLayerController routingController;
    protected List<String> attacksLabels = new ArrayList<String>();
    protected List<Boolean> attacksStatus = new ArrayList<Boolean>();

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public List<String> getAttacksLabels() {
        return attacksLabels;
    }

    public List<Boolean> getAttacksStatus() {
        return attacksStatus;
    }

    public boolean isInAttackMode() {
        return inAttackMode;
    }

    public void setInAttackMode(boolean inAttackMode) {
        this.inAttackMode = inAttackMode;
    }

    public boolean isStable() {
        return stable;
    }

    public RoutingLayerController getRoutingController() {
        return routingController;
    }

    public void setRoutingController(RoutingLayerController routingController) {
        this.routingController = routingController;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
        if (routingController != null) {
            if (stable) {
                routingController.registerAsStable(this);
            } else {
                routingController.unregisterAsStable(this);
            }
        }
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;


    }

    public RoutingLayer() {
        super();
    }

    /**
     * if a routing layer is marked as setup when the simulator start the
     * routing layer begins to operate
     * @return
     */
    public boolean isAutostarted() {
        return autostarted;


    }

    public void setAutostarted(boolean autostarted) {
        this.autostarted = autostarted;


    }

    /**
     * This method enables the instrumentation to perform the
     * controllers notification
     * @param object
     */
    public void receiveMessageHandler(Object message) {
        try {
            Message m = (Message) ((Message) message).clone();
            receiveMessage(m);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(RoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method enables the instrumentation to perform the
     * controllers notification
     * @param object
     */
    public boolean sendMessageHandler(Object message, Application app) {
        return sendMessage(message, app);
    }

    public void routeMessage(Object message) {

        onRouteMessage(message);
    }

    public final void receiveMessage(Object message) {
        if (this instanceof IInstrumentHandler) {
            if (message instanceof IInstrumentMessage) {
                CoverageInstrument.getInstance().notifyMessageReceived((IInstrumentMessage) message, (IInstrumentHandler) this);
                ReliabilityInstrument.getInstance().notifyMessageReceived((IInstrumentMessage) message, (IInstrumentHandler) this);
            }
        }
//        if (beforeReceiveMessage(message)) {
            onReceiveMessage(message);
//            afterReceiveMessage();
//        }
    }

    public boolean sendMessage(Object message, Application app) {
        if (this instanceof IInstrumentHandler) {
            if (message instanceof IInstrumentMessage) {
                CoverageInstrument.getInstance().notifyMessageSent((IInstrumentMessage) message, (IInstrumentHandler) this);
                ReliabilityInstrument.getInstance().notifyMessageSent((IInstrumentMessage) message, (IInstrumentHandler) this);
            }
        }
        boolean result = onSendMessage(message, app);
        return result;
    }

    protected void sendMessageDown(Object message){

        if (beforeSendMessageToAir(message)){
            sendMessageToAir(message);
        }



    }


    protected abstract String getRoutingTable();

    protected abstract void onReceiveMessage(Object message);

    protected abstract boolean onSendMessage(Object message, Application app);

    public abstract void setup();

    protected abstract void onRouteMessage(Object message);

    public abstract void sendMessageDone();

    protected abstract void setupAttacks();

    public abstract void newRound();

    public abstract void sendMessageToAir(Object message);

    protected boolean beforeSendMessageToAir(Object message) {
        return true;
    }




}
