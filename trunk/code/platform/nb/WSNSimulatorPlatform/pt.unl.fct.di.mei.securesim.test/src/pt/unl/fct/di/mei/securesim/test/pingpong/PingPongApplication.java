package pt.unl.fct.di.mei.securesim.test.pingpong;

import java.util.HashSet;
import java.util.Hashtable;
import pt.unl.fct.di.mei.securesim.engine.Application;
import pt.unl.fct.di.mei.securesim.engine.Event;
import pt.unl.fct.di.mei.securesim.engine.Simulator;

/**
 * This extension of the {@link Application} baseclass does everything we expect
 * from the broadcast application, simply forwards the message once, and that is
 * it.
 */
public class PingPongApplication extends Application {

    private static long countMessages = 0;
    private int CLOCK_TICK = Simulator.ONE_SECOND*100000 ;

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

    public enum TypeOfMessage {

        PING,
        PONG
    };
    protected Hashtable sentMessages = new Hashtable();
    protected HashSet<PingPongMessage> receivedMessages = new HashSet<PingPongMessage>();

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

        if (!(message instanceof PingPongMessage)) {
            throw new IllegalStateException("Message must be a instance of " + PingPongMessage.class.getSimpleName());
        }
        PingPongMessage m = (PingPongMessage) message;
        // se não já recebi a mensagem trato
        if (!receivedMessages.contains(message)) {
            receivedMessages.add((PingPongMessage) message);
            handleMessage(m, message);
        } else {
          //  System.out.println("N: " + getHostNode().getId() + ", M: " + m.id );
        }
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

    public class PingPongMessage {

        public long id = countMessages++;
        public int sourceID;
        public int destID;
        public long replyToID;
        public TypeOfMessage type = TypeOfMessage.PING;

        public PingPongMessage(int sourceID, int destID) {
            this.sourceID = sourceID;
            this.destID = destID;
        }
    }

    public boolean sendPingMessage(int to) {

        boolean result;
        PingPongMessage msg = new PingPongMessage(getNode().getId(), to);
        sentMessages.put(msg, new Integer(to));
        receivedMessages.add(msg);
    //    System.out.println("Ping " + getNode().getId() + ": " + msg.id + " to " + to);
        sendPPMessage(msg);
        return true;


    }

    private boolean sendPongMessage(PingPongMessage m) {
        boolean result;
        PingPongMessage msg = new PingPongMessage(getNode().getId(), m.sourceID);
        msg.type = TypeOfMessage.PONG;
        msg.replyToID = m.id;
  //      System.out.println("Pong " + getNode().getId() + ": " + msg.id + " to " + m.sourceID + " Reply to " + m.id);
        sentMessages.put(msg, new Integer(m.sourceID));
        receivedMessages.add(msg);
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
        getHostNode().getSimulator().addEvent(new SendPingPongMessageEvent(getHostNode().getSimulator().getSimulationTime()+(10000 +(int) Simulator.random.nextDouble() * CLOCK_TICK), m));
    }
}
