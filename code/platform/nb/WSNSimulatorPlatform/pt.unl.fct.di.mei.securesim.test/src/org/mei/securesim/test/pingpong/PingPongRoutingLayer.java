/**
 * 
 */
package org.mei.securesim.test.pingpong;

import java.util.HashSet;
import java.util.Hashtable;
import org.mei.securesim.core.Application;
import org.mei.securesim.core.layers.RoutingLayer;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.cpu.CPUProcess;
import org.mei.securesim.test.pingpong.PingPongApplication.Message;
import org.mei.securesim.test.pingpong.PingPongApplication.PingPongMessage;

/**
 * @author posilva
 *
 */
public class PingPongRoutingLayer extends RoutingLayer {

    private Node parent;
    protected Hashtable sentMessages = new Hashtable();
    protected HashSet<Message> receivedMessages = new HashSet<Message>();

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

                if (!(msg instanceof PingPongMessage)) {
                    throw new IllegalStateException("Message must be a instance of " + PingPongMessage.class.getSimpleName());
                }
                PingPongMessage m = (PingPongMessage) msg;
                // se não recebi a mensagem trato
                if (!receivedMessages.contains(m)) {
                    receivedMessages.add((PingPongMessage) msg);
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
        Message msg = (Message) message;

        sentMessages.put(msg, new Integer(msg.destID));
        receivedMessages.add(msg);

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
