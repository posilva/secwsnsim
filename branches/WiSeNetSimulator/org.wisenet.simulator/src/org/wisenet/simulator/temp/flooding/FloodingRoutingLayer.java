/**
 * 
 */
package org.wisenet.simulator.temp.flooding;

import java.util.HashSet;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.core.energy.EnergyConsumptionAction;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.temp.flooding.messages.FloodingMessage;

/**
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
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
                        onRouteMessage(msg);

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
        application = app;
        receivedMessages.add(((Message) message).getMessageNumber());
        send(message);
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
    public void setup() {
//        setDebugEnabled(true);
        if (getNode().getId() % 80==0) {
            log("sending message from 80 to 1");
            send(createDummyMessage(80, 1, "HELLO WORLD"));
        }
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
    }

    private Object createDummyMessage(int source, int dest, String data) {
        log("Creating a dummy message");
        FloodingMessage m = new FloodingMessage();
        m.setType((byte) 1);
        m.setSource((short) source);
        m.setDestin((short) dest);
        m.setMessageData(data);
        return m;
    }
}
