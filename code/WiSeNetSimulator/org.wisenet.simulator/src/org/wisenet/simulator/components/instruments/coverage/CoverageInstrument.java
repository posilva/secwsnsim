package org.wisenet.simulator.components.instruments.coverage;

import java.util.Hashtable;

import org.wisenet.simulator.components.instruments.IInstrumentHandler;
import org.wisenet.simulator.components.instruments.IInstrumentMessage;
import org.wisenet.simulator.components.instruments.IProbingResult;
import org.wisenet.simulator.components.instruments.Instrument;
import org.wisenet.simulator.components.instruments.InstrumentEvent;
import org.wisenet.simulator.components.instruments.SimulationController;
import org.wisenet.simulator.components.instruments.coverage.listeners.SignalUpdateEvent;
import org.wisenet.simulator.components.instruments.utils.SignalHandler;
import org.wisenet.simulator.core.engine.Simulator;
import org.wisenet.simulator.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class CoverageInstrument extends Instrument {
// TODO: Ver para mais de 100 nós
    private static CoverageInstrument instance;

    /**
     * There 2 types of converage models
     * 1. Radio model: based on radio conectivity
     * 2. Routing model: based on routing layer specific neighbooring info
     * evaluate ao many of this source nodes are covered
     */
    public enum CoverageModelEnum {

        RADIO, ROUTING;
    }
    protected Hashtable sendingObject = new Hashtable();
    private long delayToSentMessages;
    private long intervalToSentMessages;
    private int timesToSentMessages;
    private int howManyMessagesToSentPerSender;
    private SignalHandler radioModelNeighbors = new SignalHandler();
    private SignalHandler routingModelNeighbors = new SignalHandler();
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

    public static CoverageInstrument getInstance() {
        if (instance == null) {
            instance = new CoverageInstrument();
        }
        return instance;
    }

    public void signalNeighborDetectionReset(CoverageModelEnum model) {
        switch (model) {
            case RADIO:
                radioModelNeighbors.clear();
                break;
            case ROUTING:
                routingModelNeighbors.clear();
                break;
        }
    }

    /**
     * Controls the state of the controller
     */
    public void updateNetworkSize() {
        radioModelNeighbors.setTotalOfNodes(SimulationController.getInstance().getSimulation().getSimulator().getNodes().size());
        routingModelNeighbors.setTotalOfNodes(SimulationController.getInstance().getSimulation().getSimulator().getNodes().size());

    }

    /**
     * For Radio and Logical model each time a neighbor is discovered we must
     * signal this event to increment values of coverage
     * @param model
     * @param n
     */
    public synchronized void signalNeighborDetection(CoverageModelEnum model, Node n) {
        if (model == CoverageModelEnum.RADIO) {
            radioModelNeighbors.signal(n);
            SignalUpdateEvent event = new SignalUpdateEvent(n);
            event.setModel(model);
            event.setSignal(true);
            fireSignalUpdateEvent(event);
        } else if (model == CoverageModelEnum.ROUTING) {
            SignalUpdateEvent event = new SignalUpdateEvent(n);
            event.setModel(model);
            event.setSignal(true);
            fireSignalUpdateEvent(event);
            routingModelNeighbors.signal(n);
        }
    }

    /**
     * Read the value estimated for the coverage in the indicated model
     * (note that the calculations made in this function is based in threshold)
     * @param model
     * @return
     */
    public synchronized double getCoverageValueByModel(CoverageModelEnum model) {
        if (model == CoverageModelEnum.RADIO) {
            return radioModelNeighbors.calculateGlobalThreshold();
        } else if (model == CoverageModelEnum.ROUTING) {
            return routingModelNeighbors.calculateGlobalThreshold();
        } else {
            throw new IllegalArgumentException("Only radio and routing model can be calculated using this method");
        }
    }

    public int getRoutingModelThreshold() {
        return routingModelNeighbors.getThreshold();
    }

    public void setRoutingModelThreshold(int routingModelThreshold) {
        routingModelNeighbors.setThreshold(routingModelThreshold);
    }

    public int getRadioModelThreshold() {
        return radioModelNeighbors.getThreshold();
    }

    public void setRadioModelThreshold(int radioModelThreshold) {
        radioModelNeighbors.setThreshold(radioModelThreshold);
    }

    void fireSignalUpdateEvent(SignalUpdateEvent event) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == CoverageListener.class) {
                ((CoverageListener) listeners[i + 1]).onSignalUpdate(event);
            }
        }
    }

    /**
     *
     * @param listener
     */
    public void addCoverageListener(CoverageListener listener) {
        listenerList.add(CoverageListener.class, listener);
    }

    /**
     *
     * @param listener
     */
    public void removeCoverageListener(CoverageListener listener) {
        listenerList.remove(CoverageListener.class, listener);
    }

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
            if (!sendingObject.containsKey(handler.getUniqueId())) {
                log("Saved Message: " + message.getUniqueId() + " from " + message.getSourceId() + " to " + message.getDestinationId());
                sendingObject.put(message.getSourceId(), 0);
            }
        }
        refreshPanel();
    }

    @Override
    protected void computeMessageReception(IInstrumentMessage message, IInstrumentHandler handler) {
        if (handler.getUniqueId().equals(message.getDestinationId())) { // se quem está a receber é o destino
            if (sendingObject.containsKey(message.getSourceId())) { // se foi registado o envio
                log("Received Message: " + message.getUniqueId() + " from " + message.getSourceId() + " to " + message.getDestinationId());
                Integer c = (Integer) sendingObject.get(message.getSourceId());
                // adicionar ao contador
                c = c != null ? c + 1 : 1;
                sendingObject.put(message.getSourceId(), c);
            }
        }
        refreshPanel();
    }

    @Override
    public void reset() {
        sendingObject.clear();
        senders.clear();
        receivers.clear();
        refreshPanel();
    }

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
            return sendingObject.size();
        }

        public int getNumberOfRegistredReceivedNodes() {
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
