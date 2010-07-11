package org.mei.securesim.components.instruments;

import java.util.Hashtable;
import java.util.Vector;
import org.mei.securesim.core.engine.Simulator;

/************************************
 * Instrumento de Medição de fiabilidade de uma rede
 *
 * Tendo em conta o numero as mensagens enviadas por uma simulataneamente na rede
 * qual o numero de mensagens que chega ao destino
 *
 *
 * Neste caso as mensagens são enviadas em burst que possam provocar algum stress na rede
 * e é medido o numero de mensagens que chegam face a este stress.
 *
 * Quando se estiver perante ataques ao encaminhamento o que se faz é igual
 * permitindo determinar o impacto do ataque nesta propriedade da rede.
 *
 * O funcionamento deste instrumento é o seguinte:
 * - ao enviar 10 mensagem num intervalo tenho 100% de fiabilidade se receber 10
 * - Posso no entanto aumentar a fiabilidade enviando as cada mensagens n vezes
 * - e nesse caso tenho de verificar se chega a mensagem
 *
 */
/**
 *
 * @author posilva
 */
public class ReliabilityInstrument extends Instrument {

    protected Hashtable sentMessages = new Hashtable();
    protected Hashtable receivedMessages = new Hashtable();
    protected int timesToSentMessages;
    protected long delayToSentMessages;
    protected long intervalToSentMessages;

    public static Instrument getInstance() {
        if (instance == null) {
            instance = new ReliabilityInstrument();
        }
        return instance;
    }
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
                    SimulationController.getInstance().getSimulation().getSimulator().addEvent(new InstrumentEvent(message, sender, getTimesToSentMessages(), getDelayToSentMessages() * Simulator.ONE_SECOND, getIntervalToSentMessages() * Simulator.ONE_SECOND));
                }
            }
        }
    }

    /**
     * Save a message by ID
     * @param message
     */
    protected void saveMessage(IInstrumentMessage message, IInstrumentHandler handler) {
        if (!sentMessages.contains(message.getUniqueId())) {
            sentMessages.put(message.getUniqueId(), 0);
            receivedMessages.put(message.getDestinationId(), new Vector());
        }
    }

    protected void computeMessageReception(IInstrumentMessage message, IInstrumentHandler handler) {
        if (sentMessages.contains(message.getUniqueId())) {
            Vector messages = (Vector) receivedMessages.get(handler.getUniqueId());
            if (messages != null) {
                if (messages.contains(message.getUniqueId())) {
                    Integer c = (Integer) sentMessages.get(message.getUniqueId());
                    c = c != null ? c + 1 : 1;
                    sentMessages.put(message.getUniqueId(), c);
                }
            } else {
                System.err.println("This cannot ever happen: computeMessageReception::receivedMessages.get(handler.getUniqueId())==null");
            }
        }
    }

    @Override
    public void reset() {
        sentMessages.clear();
        receivedMessages.clear();
        senders.clear();
        receivers.clear();
    }

    public double getPerformance() {
        int numberOfMessagesSent = sentMessages.size();
        int numberOfMessagesReceived = 0;
        for (Object v : sentMessages.values()) {
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
        return null;
    }
}
