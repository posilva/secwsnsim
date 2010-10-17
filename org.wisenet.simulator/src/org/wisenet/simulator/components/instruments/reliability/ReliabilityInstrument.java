package org.wisenet.simulator.components.instruments.reliability;

import java.util.Hashtable;

import org.wisenet.simulator.components.instruments.IInstrumentHandler;
import org.wisenet.simulator.components.instruments.IInstrumentMessage;
import org.wisenet.simulator.components.instruments.IResult;
import org.wisenet.simulator.components.instruments.AbstractInstrument;
import org.wisenet.simulator.components.instruments.InstrumentEvent;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.Simulator;

/**
 * NEsta classe a intenção é registar o numero de mensagens enviadas e verificar se
 * são todas recebidas
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ReliabilityInstrument extends AbstractInstrument {
    // TODO: Ver para mais de 100 nós

    /**
     *
     */
    protected Hashtable sendingObject = new Hashtable();
    private long delayToSentMessages;
    private long intervalToSentMessages;
    private int timesToSentMessages;
    private int howManyMessagesToSentPerSender;

    @Override
    public void probe() {
        if (senders.isEmpty()) {
            throw new IllegalStateException("Must define senders");
        }
        if (receivers.isEmpty()) {
            throw new IllegalStateException("Must define receivers");
        }
        if (messageClass == null) {
            throw new IllegalStateException("No message class definition");
        }
        sendingObject.clear();
        int numberOfMessagesToSend = 0;
        for (Object s : senders) {
            IInstrumentHandler sender = (IInstrumentHandler) s;
            for (Object r : receivers) {
                IInstrumentHandler receiver = (IInstrumentHandler) r;
                for (int i = 0; i < howManyMessagesToSentPerSender; i++) {
                    IInstrumentMessage message = createMessageInstance();
                    ((Message) message).setSourceId(sender.getUniqueId());
                    ((Message) message).setDestinationId(receiver.getUniqueId());
                    if (isAutonumber()) {
                        ((Message) message).setUniqueId(messageUniqueId++);
                    }
                    numberOfMessagesToSend += getTimesToSentMessages();
                    final long interval = getIntervalToSentMessages() * Simulator.ONE_SECOND;
                    final long messageDelay = getDelayToSentMessages() * Simulator.ONE_SECOND;
                    getSimulation().getSimulator().addEvent(new InstrumentEvent(getSimulation().getTime(), message, sender, getTimesToSentMessages(), messageDelay, interval));
                }
            }
        }
        log("Probing: Number of messages sent for probing:" + numberOfMessagesToSend);

    }

    /**
     * Save a message by ID
     * @param message
     * @param handler
     */
    protected void saveMessage(IInstrumentMessage message, IInstrumentHandler handler) {
        /**
         * Se ainda não se deu o registo do nó
         */
        if (handler.getUniqueId().equals(((Message) message).getSourceId())) {
            if (!sendingObject.containsKey(((Message) message).getUniqueId())) {
                log("Saved Message: " + ((Message) message).getUniqueId() + " from " + ((Message) message).getSourceId() + " to " + ((Message) message).getDestinationId());
                sendingObject.put(((Message) message).getUniqueId(), 0);
            }
        }
        refreshPanel();
    }

    /**
     *
     * @param message
     * @param handler
     */
    @Override
    protected void computeMessageReception(IInstrumentMessage message, IInstrumentHandler handler) {
        if (handler.getUniqueId().equals(((Message) message).getDestinationId())) { // é tratado pelo destino
            if (sendingObject.containsKey(((Message) message).getUniqueId())) { // se foi registado o envio
                log("Received Message: " + ((Message) message).getUniqueId() + " from " + ((Message) message).getSourceId() + " to " + ((Message) message).getDestinationId());
                Integer c = (Integer) sendingObject.get(((Message) message).getUniqueId());
                // adicionar ao contador
                c = c != null ? c + 1 : 1;
                sendingObject.put(((Message) message).getUniqueId(), c);
            }
        }
        refreshPanel();
    }

    /**
     *
     */
    @Override
    public void reset() {
        sendingObject.clear();
        senders.clear();
        receivers.clear();
        refreshPanel();
    }

    /**
     *
     * @return
     */
    public double getPerformance() {
        int numberOfMessagesSent = sendingObject.size();
        int numberOfMessagesReceived = 0;
        for (Object v : sendingObject.values()) {
            Integer i = (Integer) v;
            if (i != null && i > 0) {
                numberOfMessagesReceived++;
            }

        }
        return (numberOfMessagesSent == 0) ? 0 : (numberOfMessagesReceived * 100 / numberOfMessagesSent);
    }

    /**
     *
     * @return
     */
    public long getDelayToSentMessages() {
        return delayToSentMessages;
    }

    /**
     *
     * @param delayToSentMessages
     */
    public void setDelayToSentMessages(long delayToSentMessages) {
        this.delayToSentMessages = delayToSentMessages;
    }

    /**
     *
     * @return
     */
    public long getIntervalToSentMessages() {
        return intervalToSentMessages;
    }

    /**
     *
     * @param intervalToSentMessages
     */
    public void setIntervalToSentMessages(long intervalToSentMessages) {
        this.intervalToSentMessages = intervalToSentMessages;

    }

    /**
     *
     * @return
     */
    public int getTimesToSentMessages() {
        return timesToSentMessages;
    }

    /**
     *
     * @param timesToSentMessages
     */
    public void setTimesToSentMessages(int timesToSentMessages) {
        this.timesToSentMessages = timesToSentMessages;
    }

    /**
     *
     * @return
     */
    public int getHowManyMessagesToSentPerSender() {
        return howManyMessagesToSentPerSender;
    }

    /**
     *
     * @param howManyMessagesToSentPerSender
     */
    public void setHowManyMessagesToSentPerSender(int howManyMessagesToSentPerSender) {
        this.howManyMessagesToSentPerSender = howManyMessagesToSentPerSender;
    }

    /**
     *
     * @return
     */
    @Override
    public IResult getLastProbeResult() {
        ReliabilityProbingResult result = new ReliabilityProbingResult();

        return result;
    }

    /**
     *
     */
    public class ReliabilityProbingResult implements IResult {

        /**
         *
         * @return
         */
        public int getNumberOfRegistredSendingObjects() {
            return sendingObject.size();
        }

        /**
         *
         * @return
         */
        public int getNumberOfRegistredReceivedObjects() {
            int result = 0;
            for (Object r : sendingObject.values()) {
                Integer v = (Integer) r;
                if (v > 0) {
                    result++;
                }
            }
            return result;
        }

        /**
         *
         * @return
         */
        public double getResultValue() {
            return getPerformance();
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return "" + (getNumberOfRegistredReceivedObjects() * 100 / getNumberOfRegistredSendingObjects());
        }
    }

    /**
     *
     * @param simulation
     */
    public ReliabilityInstrument(Simulation simulation) {
        super(simulation);
    }

    /**
     *
     */
    public ReliabilityInstrument() {
        super();
    }
}
