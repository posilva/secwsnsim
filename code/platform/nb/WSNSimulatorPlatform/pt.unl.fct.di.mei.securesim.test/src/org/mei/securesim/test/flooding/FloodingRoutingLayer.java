/**
 * 
 */
package org.mei.securesim.test.flooding;

import org.mei.securesim.test.pingpong.*;
import java.util.HashSet;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.energy.EnergyConsumptionAction;
import org.mei.securesim.core.engine.DefaultMessage;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.nodes.Node;

/**
 * @author posilva
 *
 */
public class FloodingRoutingLayer extends RoutingLayer {

    private Node parent;
    protected HashSet<Long> receivedMessages = new HashSet<Long>();

    public FloodingRoutingLayer() {
        super();
    }

    /**
     * Stores the sender from which it first receives the message, and passes
     * the message.
     */
    @Override
    public void receiveMessage(Object message) {
        final DefaultMessage msg = (DefaultMessage) message;
        getNode().getCPU().execute(new EnergyConsumptionAction(){

            public void execute() {
                DefaultMessage m = (DefaultMessage) msg;
                // if not received the message handle it 
                if (!receivedMessages.contains(getMessageID(m))) {
                    receivedMessages.add(getMessageID(m));
                    Application app = getNode().getApplication();
                    if (app != null) {
                        app.receiveMessage(msg);
                    }
                } else {
                }
            }

            public int getNumberOfUnits() {
                return msg.size();
            }
        });
    }

    @Override
    public boolean sendMessage(Object message, Application app) {
        application = app;
        receivedMessages.add(getMessageID((DefaultMessage) message));
        return getNode().getMacLayer().sendMessage(message, this);
    }

    /**
     * Sets the sent flag to true.
     */
    @Override
    public void sendMessageDone() {
        application.sendMessageDone();
        application = null;
    }


    protected long getMessageID(DefaultMessage m ){
        PingPongMessageWrapper w = new PingPongMessageWrapper();
        w.wrap(m);
        return w.getId();
    }

    @Override
    public void autostart() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
