package org.mei.securesim.components.instruments.latency;

import java.util.Hashtable;

import org.mei.securesim.components.instruments.IInstrumentHandler;
import org.mei.securesim.components.instruments.IInstrumentMessage;
import org.mei.securesim.components.instruments.IProbingResult;
import org.mei.securesim.components.instruments.Instrument;
import org.mei.securesim.components.instruments.InstrumentEvent;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.core.engine.Simulator;

/**
 * NEsta classe a intenção é registar o numero de mensagens enviadas e verificar se
 * são todas recebidas
 * @author posilva
 */
public class LatencyInstrument extends Instrument {

    private static LatencyInstrument instance;
    protected Hashtable sendingObject = new Hashtable();
    private long delayToSentMessages;
    private long intervalToSentMessages;
    private int timesToSentMessages;
    private int howManyMessagesToSentPerSender;

    /**
     * 
     */
    class LatencyEntry {

        int numberOfHops = 0;
        int maxNumberOfHops = Integer.MIN_VALUE;
        int minNumberOfHops = Integer.MAX_VALUE;
        int avgNumberOfHops = 0;
        long sendRealTime;
        long receiveRealTime;
        long sendSimTime;
        long receiveSimTime;
    }

    /**
     *
     * @return
     */
    public static LatencyInstrument getInstance() {
        if (instance == null) {
            instance = new LatencyInstrument();
        }
        return instance;
    }

    @Override
    public void notifyMessageReceived(IInstrumentMessage message, IInstrumentHandler handler) {
        if (sendingObject.containsKey(message.getUniqueId())) {
            LatencyEntry entry = (LatencyEntry) sendingObject.get(message.getUniqueId());

        }
    }

    /**
     * 
     */
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
                    message.setSourceId(sender.getUniqueId());
                    message.setDestinationId(receiver.getUniqueId());
                    if (isAutonumber()) {
                        message.setUniqueId(messageUniqueId++);
                    }
                    numberOfMessagesToSend += getTimesToSentMessages();
                    final long interval = getIntervalToSentMessages() * Simulator.ONE_SECOND;
                    final long messageDelay = getDelayToSentMessages() * Simulator.ONE_SECOND;
                    SimulationController.getInstance().getSimulation().getSimulator().addEvent(new InstrumentEvent(
                            message,
                            sender,
                            getTimesToSentMessages(),
                            messageDelay,
                            interval));
                }
            }
        }
        log("Probing: Number of messages sent for probing:" + numberOfMessagesToSend);
    }

    /**
     * Save a message by ID
     * @param message
     */
    protected void saveMessage(IInstrumentMessage message, IInstrumentHandler handler) {
        /**
         * Se ainda não se deu o registo do nó
         */
        if (handler.getUniqueId() == message.getSourceId()) {
            if (!sendingObject.containsKey(message.getUniqueId())) {
                log("Saved Message: " + message.getUniqueId() + " from " + message.getSourceId() + " to " + message.getDestinationId());
                sendingObject.put(message.getUniqueId(), 0);
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
        if (handler.getUniqueId().equals(message.getDestinationId())) { // é tratado pelo destino
            if (sendingObject.containsKey(message.getUniqueId())) { // se foi registado o envio
                log("Received Message: " + message.getUniqueId() + " from " + message.getSourceId() + " to " + message.getDestinationId());
                Integer c = (Integer) sendingObject.get(message.getUniqueId());
                // adicionar ao contador
                c = c != null ? c + 1 : 1;
                sendingObject.put(message.getUniqueId(), c);
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

    public long getIntervalToSentMessages() {
        return intervalToSentMessages;
    }

    public void setIntervalToSentMessages(long intervalToSentMessages) {
        this.intervalToSentMessages = intervalToSentMessages;

    }

    public int getTimesToSentMessages() {
        return timesToSentMessages;
    }

    public void setTimesToSentMessages(int timesToSentMessages) {
        this.timesToSentMessages = timesToSentMessages;
    }

    public int getHowManyMessagesToSentPerSender() {
        return howManyMessagesToSentPerSender;
    }

    public void setHowManyMessagesToSentPerSender(int howManyMessagesToSentPerSender) {
        this.howManyMessagesToSentPerSender = howManyMessagesToSentPerSender;
    }

    @Override
    public IProbingResult getLastProbeResult() {
        LatencyProbingResult result = new LatencyProbingResult();

        return result;
    }

    public class LatencyProbingResult implements IProbingResult {

        public int getNumberOfRegistredSendingObjects() {
            return sendingObject.size();
        }

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

        public double getResultValue() {
            return getPerformance();
        }
    }
}
