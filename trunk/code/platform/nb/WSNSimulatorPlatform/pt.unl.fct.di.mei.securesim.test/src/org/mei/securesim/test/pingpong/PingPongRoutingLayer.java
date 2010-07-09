/**
 * 
 */
package org.mei.securesim.test.pingpong;

import java.util.HashSet;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.energy.EnergyConsumptionAction;
import org.mei.securesim.core.engine.BaseMessage;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.nodes.Node;

/**
 * @author posilva
 *
 */
public class PingPongRoutingLayer extends RoutingLayer {

    private Node parent;
    protected HashSet<Long> receivedMessages = new HashSet<Long>();

    public PingPongRoutingLayer() {
        super();
    }

    /**
     * Stores the sender from which it first receives the message, and passes
     * the message.
     */
    @Override
    public void receiveMessage(Object message) {
        final BaseMessage msg = (BaseMessage) message;
        getNode().getCPU().execute(new EnergyConsumptionAction(){

            public void execute() {
                BaseMessage m = (BaseMessage) msg;
                // se não recebi a mensagem trato
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
        receivedMessages.add(getMessageID((BaseMessage) message));
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


    protected long getMessageID(BaseMessage m ){
        PingPongMessageWrapper w = new PingPongMessageWrapper();
        w.wrap(m);
        return w.getId();
    }

    @Override
    public void setup() {
        
    }

    @Override
    public void routeMessage(Object message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
