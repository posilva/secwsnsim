/**
 * 
 */
package org.mei.securesim.protocols.flooding;

import java.util.HashSet;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.energy.EnergyConsumptionAction;
import org.mei.securesim.core.engine.Message;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.protocols.flooding.messages.FloodingMessage;

/**
 * @author posilva
 *
 */
public class FloodingRoutingLayer extends RoutingLayer {

    protected HashSet<Long> receivedMessages = new HashSet<Long>();

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


                    if (m.getDestin() == (int) getNode().getId()) {
                        Application app = getNode().getApplication();
                        if (app != null) {
                            app.receiveMessage(msg);
                        }
                    } else {
                        getNode().getMacLayer().sendMessage(msg, FloodingRoutingLayer.this);
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
        return getNode().getMacLayer().sendMessage(message, this);
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
    public void setup() {
//        if (LatencyController.getInstance().getLatencyMessageClass() == null) {
//            LatencyController.getInstance().setLatencyMessageClass(LatencyTestMessage.class);
//        }
    }

    @Override
    public void onRouteMessage(Object message) {
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
}
