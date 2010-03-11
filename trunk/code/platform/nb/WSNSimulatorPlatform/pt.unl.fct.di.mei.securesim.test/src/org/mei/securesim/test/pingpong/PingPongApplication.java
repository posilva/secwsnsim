package org.mei.securesim.test.pingpong;

import org.mei.securesim.cpu.CPUProcess;
import org.mei.securesim.core.Application;
import org.mei.securesim.core.Event;
import org.mei.securesim.core.Simulator;

/**
 * This extension of the {@link Application} baseclass does everything we expect
 * from the broadcast application, simply forwards the message once, and that is
 * it.
 */
public class PingPongApplication extends Application {

    private static long countMessages = 0;
    private int CLOCK_TICK = Simulator.ONE_SECOND * 100000;

    public void handleMessage(PingPongMessage m, Object message) {
        if (m.destID == getNode().getId()) {
            // é para mim
            if (m.type == TypeOfMessage.PING) {
                sendPongMessage(m);
            } else {
                boolean r = sendPingMessage(m.sourceID);
                if (getHostNode().getId() == 1) {
                    if (!r) {
                        System.out.println("não consegui enviar");
                    }
                }
            }
        } else {
            sendMessage(message);

        }
    }

    @Override
    public void generateEvent() {
        sendPingMessage(2);
    }

    public enum TypeOfMessage {

        PING,
        PONG
    };

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
                PingPongMessage m = (PingPongMessage) msg;
                handleMessage(m, msg);
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

    public class Message {

        public long id = countMessages++;
        public int sourceID;
        public int destID;

        public Message(int sourceID, int destID) {
            this.sourceID = sourceID;
            this.destID = destID;
        }
    }

    public class PingPongMessage extends Message {

        public long replyToID;
        public TypeOfMessage type = TypeOfMessage.PING;

        public PingPongMessage(int sourceID, int destID) {
            super(sourceID, destID);
        }

    }

    public boolean sendPingMessage(int to) {

        boolean result;
        PingPongMessage msg = new PingPongMessage(getNode().getId(), to);
        sendPPMessage(msg);
        return true;


    }

    private boolean sendPongMessage(PingPongMessage m) {
        boolean result;
        PingPongMessage msg = new PingPongMessage(getNode().getId(), m.sourceID);
        msg.type = TypeOfMessage.PONG;
        msg.replyToID = m.id;
        sendPPMessage(msg);
        return true;


    }

    class SendPingPongMessageEvent extends Event {

        PingPongMessage message;

        public SendPingPongMessageEvent(long time, PingPongMessage message) {
            super(time);
            this.message = message;
        }

        @Override
        public void execute() {
            //     System.out.println("Message sented");
            sendMessage(message);
        }
    }

    private void sendPPMessage(PingPongMessage m) {
        getHostNode().getSimulator().addEvent(new SendPingPongMessageEvent(getHostNode().getSimulator().getSimulationTime() + (10000 + (int) Simulator.random.nextDouble() * CLOCK_TICK), m));
    }
}
