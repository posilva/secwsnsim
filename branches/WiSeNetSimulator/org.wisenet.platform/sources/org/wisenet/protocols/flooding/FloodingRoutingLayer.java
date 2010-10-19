/**
 * 
 */
package org.wisenet.protocols.flooding;

import java.util.HashSet;
import org.wisenet.protocols.common.attacks.BlackholeRoutingAttack;
import org.wisenet.protocols.flooding.messages.FloodingMessage;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.core.energy.EnergyConsumptionAction;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksEntry;

/**
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 *
 */
public class FloodingRoutingLayer extends RoutingLayer {

    /**
     *
     */
    protected HashSet<Long> receivedMessages = new HashSet<Long>();

    /**
     *
     */
    public FloodingRoutingLayer() {
        super();

    }

    /**
     * Stores the sender from which it first receives the message, and passes
     * the message.
     */
    @Override
    public void onReceiveMessage(Object message) {

        final FloodingMessage msg = (FloodingMessage) message;
        getNode().getCPU().execute(new EnergyConsumptionAction() {

            /**
             * 
             */
            public void execute() {

                FloodingMessage m = msg;
                if (!receivedMessages.contains(m.getMessageNumber())) {
                    receivedMessages.add(m.getMessageNumber());
                    if (m.getDestinationId().equals(getUniqueId())) {
                        Application app = getNode().getApplication();
                        if (app != null) {
                            app.receiveMessage(msg);
                            done(msg);  // must implement this method to notify
                            // the routing layer about message reception
                        }
                    } else {
                        routeMessage(msg);

                    }
                }
            }

            public int getNumberOfUnits() {
                return msg.size();
            }
        });
    }

    @Override
    public boolean onSendMessage(Object message, Application app) {
        System.out.println("Message Number: " + ((Message) message).getMessageNumber());
        application = app;
        receivedMessages.add(((Message) message).getMessageNumber());
        FloodingMessage msg = encapsulateMessage((Message) message);
        send(msg);
        return true;
    }

    /**
     * Sets the sent flag to true.
     */
    @Override
    public void sendMessageDone() {
        if (application != null) {
            application.sendMessageDone();
        }
        application = null;
    }

    @Override
    public void onStartUp() {
        setStable(true);
    }

    @Override
    public void onRouteMessage(Object message) {
        send(message);
    }

    @Override
    protected String getRoutingTable() {
        return "";
    }

    @Override
    protected void setupAttacks() {
    }

    @Override
    public void newRound() {
    }

    @Override
    public void sendMessageToAir(Object message) {
        getNode().getMacLayer().sendMessage(message, FloodingRoutingLayer.this);
    }

    @Override
    protected void onStable(boolean oldValue) {
    }

    @Override
    protected void initAttacks() {
        AttacksEntry entry = new AttacksEntry(false, "Blackhole Attack", new BlackholeRoutingAttack(this));
        attacks.addEntry(entry);
        getController().registerAttack(entry);

    }

    private FloodingMessage encapsulateMessage(Message message) {

        FloodingMessage m = new FloodingMessage();
        m.setUniqueId(message.getUniqueId());
        m.setSourceId(message.getSourceId());
        m.setDestinationId(message.getDestinationId());
        byte[] new_payload = message.getPayload();
        m.setPayload(new_payload);
        getController().addMessageSentCounter((short) 0);
        return m;
    }

    @Override
    public Object getUniqueId() {
        return getNode().getId();
    }
}
