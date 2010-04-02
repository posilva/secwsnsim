/**
 * 
 */
package org.mei.securesim.test.pingpong;

import java.util.HashSet;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.DefaultMessage;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.core.nodes.cpu.CPUProcess;

/**
 * @author posilva
 *
 */
public class PingPongRoutingLayer extends RoutingLayer {

    private Node parent;
    protected HashSet<Integer> receivedMessages = new HashSet<Integer>();

    public PingPongRoutingLayer() {
        super();
    }

    /**
     * Stores the sender from which it first receives the message, and passes
     * the message.
     */
    @Override
    public void receiveMessage(Object message) {
        final Object msg = message;
        getNode().getCPU().execute(new CPUProcess() {

            @SuppressWarnings("element-type-mismatch")
            public void run() {
                DefaultMessage m = (DefaultMessage) msg;
                // se não recebi a mensagem trato
                if (!receivedMessages.contains(m.getPayload().hashCode())) {
                    receivedMessages.add(m.getPayload().hashCode());
                    Application app = getNode().getApplication();
                    if (app != null) {
                        app.receiveMessage(msg);
                    }
                } else {
                }
            }
        });
    }

    @Override
    public boolean sendMessage(Object message, Application app) {
        application = app;
        receivedMessages.add(((DefaultMessage) message).getPayload().hashCode());
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
}
