package org.mei.securesim.test.pingpong;

import java.io.Serializable;
import org.mei.securesim.core.nodes.cpu.CPUProcess;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.engine.DefaultMessage;

/**
 * This extension of the {@link Application} baseclass does everything we expect
 * from the broadcast application, simply forwards the message once, and that is
 * it.
 */
public class PingPongApplication extends Application implements Serializable {

    private static long countMessages = 0;
    private int CLOCK_TICK = Simulator.ONE_SECOND * 100000;
    public static int PING = 0;
    public static int PONG = 1;
    private int NO_REPLY = -1;

    public void handleMessage(DefaultMessage msg) {

        PingPongMessageWrapper m = new PingPongMessageWrapper();
        m.wrap(msg);
        if (m.getDest() == getNode().getId()) {
            // é para mim
            if (m.type == PING) {
            //    System.out.println("Recebi um PING: " + getNode().getId()+  " from " + m.getSource());
                sendPongMessage(m.source, m.getId());
            } else {
              //  System.out.println("Recebi um PONG: " + getNode().getId() +  " from " + m.getSource());
                boolean r = sendPingMessage(m.source);
                if (getHostNode().getId() == 1) {
                    if (!r) {
                        System.out.println("não consegui enviar");
                    }
                }
            }
        } else {
            boolean sendMessage = sendMessage(msg);

        }
    }

    @Override
    public void generateEvent() {
        sendPingMessage(2);
    }

    /**
     * @param node
     *            the Node on which this application runs.
     */
    public PingPongApplication() {
        super();
    }

    /**
     * Stores the sender from which it first receives the message, and passes
     * the message.
     */
    @Override
    @SuppressWarnings("element-type-mismatch")
    public void receiveMessage(Object message) {
        final Object msg = message;
        getHostNode().getCPU().execute(new CPUProcess() {

            public void run() {
                DefaultMessage m = (DefaultMessage) msg;
                handleMessage(m);
            }
        });
    }

    /**
     * Sets the sent flag to true.
     */
    @Override
    public void sendMessageDone() {
        getHostNode().sentMenssage(true);


    }

    private PingPongNode getHostNode() {
        return (PingPongNode) getNode();
    }

    public static PingPongApplication createInstance() {
        return new PingPongApplication();
    }

    public boolean sendPingMessage(int to) {
        PingPongMessageWrapper w = new PingPongMessageWrapper();
        byte[] payload = w.createPayload(PING, countMessages++, getNode().getId(), to, NO_REPLY);
        sendPPMessage(new DefaultMessage(payload));
        return true;
    }

    private boolean sendPongMessage(int to, long replyID) {
        PingPongMessageWrapper w = new PingPongMessageWrapper();
        byte[] payload = w.createPayload(PONG, countMessages++, getNode().getId(), to, replyID);
        sendPPMessage(new DefaultMessage(payload));
        return true;


    }

    class SendPingPongMessageEvent extends Event {

        DefaultMessage message;

        public SendPingPongMessageEvent(long time, DefaultMessage message) {
            super(time);
            this.message = message;
        }

        @Override
        public void execute() {
            //     System.out.println("Message sented");
            sendMessage(message);
        }
    }

    private void sendPPMessage(DefaultMessage m) {
        getHostNode().getSimulator().addEvent(new SendPingPongMessageEvent(getHostNode().getSimulator().getSimulationTime() + (10000 + (int) Simulator.randomGenerator.random().nextDouble() * CLOCK_TICK), m));
    }
}
