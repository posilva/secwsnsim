package org.mei.securesim.components.instruments;

import java.util.Hashtable;

import java.util.Vector;
import org.mei.securesim.core.engine.Simulator;

/**
 *
 * @author posilva
 */
public class CoverageInstrument extends Instrument {

    protected Hashtable senderNode = new Hashtable();
    protected Hashtable receivedMessages = new Hashtable();

    public static Instrument getInstance() {
        if (instance == null) {
            instance = new CoverageInstrument();
        }
        return instance;
    }
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
        senderNode.clear();
        receivedMessages.clear();
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
                    final long messageDelay = (numberOfMessagesToSend * interval) + getDelayToSentMessages() * Simulator.ONE_SECOND;
//                    sender.probing(message);
                    SimulationController.getInstance().getSimulation().getSimulator().addEvent(new InstrumentEvent(message, sender, getTimesToSentMessages(), messageDelay, interval));
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
            if (!senderNode.containsKey(handler.getUniqueId())) {
                log("Saved Message: " + message.getUniqueId() + " from " + message.getSourceId() + " to " + message.getDestinationId());
                senderNode.put(message.getSourceId(), 0);
                receivedMessages.put(message.getDestinationId(), new Vector());
            }
        }
        refreshPanel();
    }

    @Override
    protected void computeMessageReception(IInstrumentMessage message, IInstrumentHandler handler) {
        if (senderNode.containsKey(message.getSourceId())) { // se foi registado o envio
            if (handler.getUniqueId().equals(message.getDestinationId())) { // se quem está a receber é o destino
                log("Received Message: " + message.getUniqueId() + " from " + message.getSourceId() + " to " + message.getDestinationId());
                Vector messages = (Vector) receivedMessages.get(handler.getUniqueId()); // ler as mensagens recebidas
                if (messages != null) {
                    if (messages.contains(message.getUniqueId())) { // se já esta na lista das recebidas pelo destino
                        Integer c = (Integer) senderNode.get(message.getUniqueId()); // adicionar ao contador
                        c = c != null ? c + 1 : 1;
                        senderNode.put(message.getSourceId(), c);
                    } else { // registar a primeira recepção
                        messages.add(message.getSourceId());
                        senderNode.put(message.getSourceId(), 1);
                    }
                    receivedMessages.put(handler.getUniqueId(), messages);
                } else {
                    System.err.println("This cannot ever happen: computeMessageReception::receivedMessages.get(handler.getUniqueId())==null");
                }
            }
        }
        refreshPanel();
    }

    @Override
    public void reset() {
        senderNode.clear();
        receivedMessages.clear();
        senders.clear();
        receivers.clear();
        refreshPanel();
    }

    public double getPerformance() {
        int numberOfMessagesSent = senderNode.size();
        int numberOfMessagesReceived = 0;
        for (Object v : senderNode.values()) {
            Integer i = (Integer) v;
            if (i != null && i > 0) {
                numberOfMessagesReceived++;
            }

        }
        return (numberOfMessagesSent == 0) ? 0 : (numberOfMessagesReceived * 100 / numberOfMessagesSent);
    }

    public long getDelayToSentMessages() {
        return delayToSentMessages;
    }

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
        CoverageProbingResult result = new CoverageProbingResult();

        return result;
    }

    public class CoverageProbingResult implements IProbingResult {

        public int getNumberOfRegistredSenderNodes() {
            log("" + senderNode.size());
            return senderNode.size();
        }

        public int getNumberOfRegistredReceivedNodes() {
            int result = 0;
            for (Object r : senderNode.values()) {
                Integer v = (Integer) r;
                if (v > 0) {
                    result++;
                }
            }
            return result;

        }
    }
}
