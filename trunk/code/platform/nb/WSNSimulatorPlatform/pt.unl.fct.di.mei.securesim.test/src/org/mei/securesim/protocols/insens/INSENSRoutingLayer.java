package org.mei.securesim.protocols.insens;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.components.instruments.coverage.CoverageInstrument;
import org.mei.securesim.components.instruments.IInstrumentHandler;
import org.mei.securesim.components.instruments.IInstrumentMessage;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.components.instruments.reliability.ReliabilityInstrument;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.attacks.IBlackHoleAttack;
import org.mei.securesim.core.engine.Message;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.events.Timer;
import org.mei.securesim.protocols.common.ByteArrayDataOutputStream;
import org.mei.securesim.protocols.common.events.DelayedMessageEvent;
import org.mei.securesim.protocols.insens.basestation.BaseStationController;
import org.mei.securesim.protocols.insens.basestation.ForwardingTable;
import org.mei.securesim.protocols.insens.messages.INSENSDATAMessage;
import org.mei.securesim.protocols.insens.messages.INSENSMessage;
import org.mei.securesim.protocols.insens.messages.INSENSMessagePayloadFactory;
import org.mei.securesim.protocols.insens.messages.data.DATAPayload;
import org.mei.securesim.protocols.insens.messages.data.FDBKPayload;
import org.mei.securesim.protocols.insens.messages.data.RREQPayload;
import org.mei.securesim.protocols.insens.messages.data.RUPDPayload;
import org.mei.securesim.protocols.insens.messages.evaluation.EvaluationINSENSDATAMessage;
import org.mei.securesim.protocols.insens.utils.NeighborInfo;
import org.mei.securesim.protocols.insens.utils.NetworkKeyStore;
import org.mei.securesim.protocols.insens.utils.OneWaySequenceNumbersChain;
//TODO: Ter em conta que Ã© preciso ter um determinado numero de vizinhos para grantir q o protocolo funciona
//TODO: Ã‰ necessÃ¡rio que se avalie a forÃ§a do sinal por forma a aceitar ou nÃ£o os RREQ
//TODO: Verificar porque se tem tantas vezes a transmitir quando se recebe e o q
//se pode fazer para minimizar esse impacto

/**
 *
 * @author CIAdmin
 */
public class INSENSRoutingLayer extends RoutingLayer implements IInstrumentHandler, IBlackHoleAttack {

    public static final String PHASE_FORWARD_DATA = "FORWARD_DATA";
    public static final String PHASE_ROUTE_FEEDBACK = "ROUTE_FEEDBACK";
    public static final String PHASE_ROUTE_REQUEST = "ROUTE_REQUEST";
    public static final String PHASE_ROUTE_UPDATE = "ROUTE_UPDATE";
    public static final String PHASE_SETUP = "SETUP";
    /**
     * Node info attributes
     */
    private byte[] privateKey;
    private long OWS;
    private long roundOWS;
    private int roundNumber = 0;
    private NeighborInfo neighborInfo = new NeighborInfo();
    private byte[] myRoundMAC = null;
    private ForwardingTable forwardingTable;
    /**
     * Timers for actions control
     */
    private Timer feedbackMessageStartTimer = null;
    private Timer queueMessageDispatchTimer = null;
    private Timer startForwardTablesCalculesTimer = null;
    /**
     * Control structures
     */
    private BaseStationController baseStationController = null;
    private boolean reliableMode = true;
    private LinkedList messagesQueue = new LinkedList();
    private boolean sendingMessage;
    /**
     * Counters for control protocol
     */
    private short feedbackMessagesReceived = 0;
    private short lastFeedbackMessagesReceivedCheck = 0;
    private byte feedbackMessageRetries;
    private Hashtable tableOfNodesByHops;

    @Override
    public void onReceiveMessage(Object message) {
        if (message instanceof INSENSMessage) {
            try {
                INSENSMessage m = (INSENSMessage) message;
                byte type = INSENSFunctions.getMessageType(m);
                if (INSENSFunctions.verifyMAC(m.getPayload(), type)) {
                    switch (type) {
                        case INSENSConstants.MSG_ROUTE_REQUEST:
                            setCurrentPhase(PHASE_ROUTE_REQUEST);
                            routingController.addMessageReceivedCounter(INSENSConstants.MSG_ROUTE_REQUEST);
                            processRREQMessage(m);
                            break;
                        case INSENSConstants.MSG_FEEDBACK:
                            setCurrentPhase(PHASE_ROUTE_FEEDBACK);
                            routingController.addMessageReceivedCounter(INSENSConstants.MSG_FEEDBACK);
                            processFDBKMessage(m);
                            break;
                        case INSENSConstants.MSG_ROUTE_UPDATE:
                            setCurrentPhase(PHASE_ROUTE_UPDATE);
                            routingController.addMessageReceivedCounter(INSENSConstants.MSG_ROUTE_UPDATE);
                            processRUPDMessage(m);
                            break;
                        case INSENSConstants.MSG_ROUTE_UPDATE_ACK:
                            routingController.addMessageReceivedCounter(INSENSConstants.MSG_ROUTE_UPDATE_ACK);
                            processRUPDACKMessage(m);
                            break;
                        case INSENSConstants.MSG_DATA:
                            setCurrentPhase(PHASE_FORWARD_DATA);
                            routingController.addMessageReceivedCounter(INSENSConstants.MSG_DATA);
                            processDATAMessage(m);
                            break;
                    }
                }
            } catch (INSENSException ex) {
                log(ex);
            }
        }
    }

    @Override
    public void sendMessageDone() {
        sendingMessage = false;
        messagesQueue.poll();
    }

    @Override
    public boolean onSendMessage(Object message, Application app) {
        if (message instanceof INSENSDATAMessage) {
            if (isStable()) {
                return sendDATAMessage((INSENSDATAMessage) message);
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public void setup() {
        setCurrentPhase(PHASE_SETUP);
        ((CoverageInstrument) CoverageInstrument.getInstance()).signalNeighborDetectionReset(CoverageInstrument.CoverageModelEnum.ROUTING);
        setupEvaluationClasses();
        setRoutingController(SimulationController.getInstance().getRoutingLayerController());
        getNode().getMacLayer().setDebugEnabled(false);
        getNode().getRoutingLayer().setDebugEnabled(false);
        if (getNode().isSinkNode()) {
            baseStationController = new BaseStationController(this);
        }
        createNodeKeys();
        initiateSequenceNumber();
        createTimers();
        startProtocol();
    }

    public long getOWS() {
        return OWS;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    @Override
    public void onRouteMessage(Object message) {
        try {
            INSENSMessage m = (INSENSMessage) message;
            byte type = INSENSFunctions.getMessageType(m);
            switch (type) {
                case INSENSConstants.MSG_ROUTE_UPDATE:
                    RUPDPayload payload = new RUPDPayload(m.getPayload());
                    if (forwardingTable.haveRoute(payload.destination, payload.source, payload.immediate)) {
                        byte[] new_payload = INSENSMessagePayloadFactory.updateRUPDPayload(payload.source, payload.destination, getNode().getId(), payload.ows, payload.forwardingTable, payload.mac, this.getNode());
                        if (new_payload != null) {
                            m.setPayload(new_payload);
                            routingController.addMessageSentCounter(INSENSConstants.MSG_ROUTE_UPDATE);
                            sendMessageToAir((Message) m.clone(), reliableMode);
                            log("Forward Message from " + payload.source + " to " + payload.destination);
                        }
                    } else {
                    }
                    break;
                case INSENSConstants.MSG_DATA:
                    DATAPayload payloadData = new DATAPayload(m.getPayload());
                    if (forwardingTable.haveRoute(payloadData.destination, payloadData.source, payloadData.immediate)) {
                        byte[] new_payload = INSENSMessagePayloadFactory.updateDATAPayload(payloadData.source, payloadData.destination, getNode().getId(), payloadData.data, payloadData.mac, this.getNode());
                        if (new_payload != null) {
                            m.setPayload(new_payload);
                            routingController.addMessageSentCounter(INSENSConstants.MSG_DATA);
                            sendMessageToAir((Message) m.clone(), reliableMode);
                            log("Forward Message from " + payloadData.source + " to " + payloadData.destination);
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (CloneNotSupportedException ex) {
            log(ex);
        } catch (INSENSException ex) {
            log(ex);
        }
    }

    /**
     * 
     * @param message
     * @param reliable
     */
    private void broadcastMessage(Message message, boolean reliable) {
        sendingMessage = true;
        long delay = (long) Simulator.randomGenerator.nextDoubleBetween((int) INSENSConstants.MIN_DELAYED_MESSAGE_BOUND, (int) INSENSConstants.MAX_DELAYED_MESSAGE_BOUND);
        long time = (long) (getNode().getSimulator().getSimulationTime());
        DelayedMessageEvent delayMessageEvent = new DelayedMessageEvent(time, delay, message, getNode());
        delayMessageEvent.setReliable(reliable);
        getNode().getSimulator().addEvent(delayMessageEvent);
    }

    private Hashtable buildLisOfNodestByHops(Vector allpaths) {
        Hashtable t = new Hashtable();
        for (Object list : allpaths) {
            LinkedList lst = (LinkedList) list;
            LinkedList lst2 = (LinkedList) t.get(lst.size());
            if (lst2 == null) {
                lst2 = new LinkedList();
            }
            lst2.add(lst.getLast());
            t.put(lst.size(), lst2);
        }
        return t;
    }

    /**
     * Create a node private key
     */
    private void createNodeKeys() {
        privateKey = CryptoFunctions.createSkipjackKey();
        NetworkKeyStore.getInstance().registerKey(getNode().getId(), privateKey);
    }

    /**
     * Load initial sequence number from the one way hash chain
     */
    private void initiateSequenceNumber() {
        OWS = INSENSFunctions.getInitialSequenceNumber();
    }

    /**
     * Create the timers needed for operation
     */
    private void createTimers() {
        this.feedbackMessageStartTimer = new Timer(1, INSENSConstants.FEEDBACK_START_TIME_BOUND + getNode().getId() * 100) {

            @Override
            public void executeAction() {
                sendFeedbackMessageInfo();

            }
        };
        this.startForwardTablesCalculesTimer = new Timer(INSENSConstants.FEEDBACK_MSG_RECEIVED_TIME_BOUND) {

            @Override
            public void executeAction() {
                startComputeRoutingInfo();
            }
        };
        this.queueMessageDispatchTimer = new Timer(INSENSConstants.MESSAGE_DISPATCH_RATE) {

            @Override
            public void executeAction() {
                dispatchNextMessage();
            }
        };
    }

    /**
     * Prepare the feedback message info to send to parent nodes
     */
    private void sendFeedbackMessageInfo() {
        log("Send FeedBack Message ");
        byte[] payload = INSENSMessagePayloadFactory.createFBKPayload(getNode().getId(),
                privateKey, neighborInfo, neighborInfo.getParentMac(), this.getNode());
        INSENSMessage message = new INSENSMessage(payload);
        routingController.addMessageSentCounter(INSENSConstants.MSG_FEEDBACK);
        sendMessageToAir(message, reliableMode);
    }

    /**
     * Verifies if can begin to compute the routing info
     * @return
     */
    boolean canStartComputeRoutingInfo() {
        /**
         * Devo verificar se o numero de mensagens alterou
         * se alterou entao actualizo e reinicio as tentativas
         * se nao alterou depois de 3 checks entÃ£o posso iniciar a recepÃ§Ã£o
         */
        if (feedbackMessagesReceived <= lastFeedbackMessagesReceivedCheck) {
            if (feedbackMessageRetries >= INSENSConstants.FEEDBACK_MSG_RECEIVED_RETRIES) {
                startForwardTablesCalculesTimer.stop();
                return true;
            } else {
                feedbackMessageRetries++;
                return false;
            }
        } else {
            lastFeedbackMessagesReceivedCheck = feedbackMessagesReceived;
            feedbackMessageRetries = 0;
            return false;
        }
    }

    /**
     * Start building routing info
     */
    private void startComputeRoutingInfo() {
        if (canStartComputeRoutingInfo()) {
            log("Started to compute routing info");
            baseStationController.calculateForwardingTables();
            log("Number of Forwarding Tables:  " + baseStationController.getForwardingTables().size());
            sendRouteUpdateMessages(baseStationController.getForwardingTables());
        }
    }

    /**
     * Start the protocol execution, must be initiated by a node a base station
     */
    private void startProtocol() {
        setCurrentPhase(PHASE_ROUTE_REQUEST);
        queueMessageDispatchTimer.start();
        if (getNode().isSinkNode()) {
            newRoutingDiscover();
        }
    }

    /**
     * Begins a new network organization
     */
    private void newRoutingDiscover() {
        roundNumber++;
        roundOWS = INSENSFunctions.getNextOWS();
        log("Round " + roundNumber + " OWS: " + roundOWS);
        startNetworkOrganization();
    }

    /**
     * Create the initial request message for network organization
     */
    private void startNetworkOrganization() {
        try {
            /* create a initial route request message */
            byte[] payload = INSENSMessagePayloadFactory.createREQPayload(getNode().getId(), roundOWS, privateKey, null, this.getNode());
            INSENSMessage m = new INSENSMessage(payload);
            RREQPayload dummy = new RREQPayload(payload);
            myRoundMAC = dummy.mac;
            routingController.addMessageSentCounter(INSENSConstants.MSG_ROUTE_REQUEST);
            sendMessageToAir(m, reliableMode);
            startForwardTablesCalculesTimer.start();
        } catch (INSENSException ex) {
            log(ex);
        }
    }

    /**
     * Sent the message to the air, if send fail then try again
     * @see DelayedMessageEvent
     * @param message
     */
    private void sendMessageToAir(Message message, boolean reliable) {

        messagesQueue.addLast(message);
        if (queueMessageDispatchTimer.isStop()) {
            sendingMessage = false;
            queueMessageDispatchTimer.start();
        }
    }

    /**
     * Processes a Route Request message
     * @param m
     */
    private void processRREQMessage(INSENSMessage m) throws INSENSException {
        boolean isParent = false;
        RREQPayload payload = new RREQPayload(m.getPayload());

        if (!getNode().isSinkNode()) {
            if (isFirstTime(payload)) {
                log("SIGNAL STRENGTH: " + getNode().getMacLayer().getSignalStrength() + "\tSIGNAL NOISE: " + getNode().getMacLayer().getNoiseStrength());
                if (getNode().getMacLayer().getSignalStrength() > INSENSConstants.SIGNAL_STRENGH_THRESHOLD && getNode().getMacLayer().getNoiseStrength() < INSENSConstants.SIGNAL_NOISE_THRESHOLD) {
                    if (owsIsValid(payload)) {
                        isParent = true;
                        roundOWS = payload.ows; // updates the round ows
                        rebroadcastRREQMessage(payload);
                        feedbackMessageStartTimer.start();
                    } else {
                        log("OWS is invalid");
                        return;
                    }
                } else {
                    log("SIGNAL STRENGH = " + getNode().getMacLayer().getSignalStrength());
                }
            }
        }
        if (!neighborInfo.containsKey(payload.sourceId)) {
            ((CoverageInstrument) CoverageInstrument.getInstance()).signalNeighborDetection(CoverageInstrument.CoverageModelEnum.ROUTING, this.getNode());
        }
        neighborInfo.addNeighbor(payload.sourceId, payload.mac, isParent);
    }

    /**
     * Verify if is the first time this node receives a RREQ in current round
     * @param payload
     * @return
     */
    private boolean isFirstTime(RREQPayload payload) {
        if (roundOWS == payload.ows) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verify if ows is valid
     * @param payload
     * @return
     */
    private boolean owsIsValid(RREQPayload payload) {
        return OneWaySequenceNumbersChain.verifySequenceNumber(OWS, payload.ows);
    }

    /**
     * Broadcast the RREQ message updating important fieds
     * @param payload
     */
    private void rebroadcastRREQMessage(RREQPayload rreqPayload) throws INSENSException {
        byte[] payload = INSENSMessagePayloadFactory.createREQPayload(getNode().getId(), rreqPayload.ows, privateKey, rreqPayload.mac, this.getNode());
        INSENSMessage message = new INSENSMessage(payload);
        RREQPayload dummy = new RREQPayload(payload);
        myRoundMAC = Arrays.copyOf(dummy.mac, dummy.mac.length);
        routingController.addMessageSentCounter(INSENSConstants.MSG_ROUTE_REQUEST);
        sendMessageToAir(message, reliableMode);
    }

    /**
     * Process a Feedback message message
     * @param m
     */
    private void processFDBKMessage(INSENSMessage m) throws INSENSException {
        FDBKPayload payload = new FDBKPayload(m.getPayload());
        if (Arrays.equals(myRoundMAC, payload.parent_mac)) {
            if (getNode().isSinkNode()) { // if i'm a sink node keep the information
                baseStationController.addFeedbackMessages(payload);
                INSENSFunctions.decryptData(getNode(), payload.neighborInfo, null);
                feedbackMessagesReceived++;
            } else { // forward the message if is from my child
                byte[] new_payload = modifiedParentMAC(payload);
                routingController.addMessageSentCounter(INSENSConstants.MSG_FEEDBACK);
                sendMessageToAir(new INSENSMessage(new_payload), reliableMode);
                log("Forward FDBK Message From Child " + payload.sourceId);
            }
        }// else drop it
    }

    /**
     * Modify the parentMAC
     * @param old_payload
     * @return
     */
    private byte[] modifiedParentMAC(FDBKPayload old_payload) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(old_payload.type);
            bados.writeShort(old_payload.sourceId);
            bados.writeInt(old_payload.neighborInfoSize);
            bados.write(old_payload.neighborInfo);
            bados.write(neighborInfo.getParentMac());
            bados.write(old_payload.mac);
            return bados.toByteArray();
        } catch (IOException ex) {
            log(ex);
        }
        return null;
    }

    /**
     * Dispatchs a message from the message queue
     */
    private void dispatchNextMessage() {
        if (!sendingMessage) {
            if (!messagesQueue.isEmpty()) {
                broadcastMessage((Message) messagesQueue.peek(), reliableMode);
            } else {
                queueMessageDispatchTimer.stop();
            }
        }
    }

    public NeighborInfo getNeighborInfo() {
        return neighborInfo;
    }

    private void sendRouteUpdateMessages(Hashtable<Short, ForwardingTable> forwardingTables) {
        Vector allpaths = baseStationController.getAllPaths();
        Comparator<LinkedList> c = new Comparator<LinkedList>() {

            public int compare(LinkedList o1, LinkedList o2) {
                return Integer.valueOf(o1.size()).compareTo(Integer.valueOf(o2.size()));
            }
        };
        Collections.sort(allpaths, c);
        tableOfNodesByHops = new Hashtable();
        tableOfNodesByHops = buildLisOfNodestByHops(allpaths);
        sendForwardingTablesByHops(forwardingTables, tableOfNodesByHops);
        setStable(true);
    }

    private void processRUPDACKMessage(INSENSMessage m) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Process a Route Update Message
     * @param m
     */
    private void processRUPDMessage(INSENSMessage m) {
        try {
            if (getNode().isSinkNode()) {
                return;
            }
            RUPDPayload payload = new RUPDPayload(m.getPayload());
            if (payload.destination == getNode().getId()) { // It's for me :)
                if (isStable()) {
                    replyToRUPDMessage(payload);
                } else {
                    updateRoutingStatus(payload);
                }
            } else {
                if (isStable()) {
                    routeMessage(m);
                }
            }
        } catch (INSENSException ex) {
            log(ex);
        }
    }

    private void sendForwardingTablesByHops(Hashtable<Short, ForwardingTable> forwardingTables, Hashtable tableOfNodesByHops) {

        List orderedByHops = new LinkedList(tableOfNodesByHops.keySet());
        Collections.sort(orderedByHops);
        byte[] payload;
        for (Object key : orderedByHops) {
            LinkedList nodes = (LinkedList) tableOfNodesByHops.get(key);
            for (Object n : nodes) {

                Short id = (Short) n;
                if (forwardingTables.containsKey(id)) {
                    payload = INSENSMessagePayloadFactory.createRUPDPayload(getNode().getId(), (Short) n, getNode().getId(), OWS, forwardingTables.get(id), privateKey, this.getNode());
                    routingController.addMessageSentCounter(INSENSConstants.MSG_ROUTE_UPDATE);
                    sendMessageToAir(new INSENSMessage(payload), reliableMode);
                }
            }
        }
    }

    private void updateRoutingStatus(RUPDPayload payload) {
        forwardingTable = new ForwardingTable(getNode().getId());
        forwardingTable.addAll(payload.forwardingTable);
        System.out.println("\n" + forwardingTable.toString());
        setStable(true);
        replyToRUPDMessage(payload);
    }

    private void replyToRUPDMessage(RUPDPayload payload) {
        log("Replying to RUPD Message");
    }

    private void processDATAMessage(INSENSMessage m) {
        if (isStable()) {
            if (!itsForMe(m)) {
                routeMessage(m);
            } else {
                try {
                    DATAPayload payload = new DATAPayload(m.getPayload());
                    INSENSFunctions.decryptData(getNode(), payload.data, null);
                    getNode().getApplication().receiveMessage(payload.data);
                } catch (INSENSException ex) {
                    log(ex);
                }
            }
        }
    }

    private boolean sendDATAMessage(INSENSDATAMessage message) {
        try {
            byte[] new_payload = encapsulateDataPayload(message);
            message.setPayload(new_payload);
            routingController.addMessageSentCounter(INSENSConstants.MSG_DATA);
            sendMessageToAir((Message) message.clone(), reliableMode);
            return true;
        } catch (CloneNotSupportedException ex) {
            log(ex);
        }
        return false;
    }

    private byte[] encapsulateDataPayload(INSENSDATAMessage message) {
        return INSENSMessagePayloadFactory.createDATAPayload(message.getSource(), message.getDestination(), getNode().getId(), message.getPayload(), privateKey, this.getNode());
    }

    /**
     * Verify if the message its for me
     * @param m
     * @return
     */
    private boolean itsForMe(INSENSMessage m) {
        try {
            DATAPayload payload = new DATAPayload(m.getPayload());
            if (payload.destination == getNode().getId()) {
                return true;
            }
        } catch (INSENSException ex) {
            log(ex);
        }
        return false;

    }

    private void setupEvaluationClasses() {
        CoverageInstrument.getInstance().setMessageClass(EvaluationINSENSDATAMessage.class);
        ReliabilityInstrument.getInstance().setMessageClass(EvaluationINSENSDATAMessage.class);
    }

    @Override
    public String toString() {
        return forwardingTable.toString();
    }

    public Object getUniqueId() {
        return getNode().getId();
    }

    public void probing(IInstrumentMessage message) {
        getNode().sendMessage(message);
    }

    @Override
    protected String getRoutingTable() {
        return forwardingTable.toString();
    }

    @Override
    protected void setupAttacks() {
        getAttacksLabels().add("Blackhole");
        getAttacksLabels().add("Wormhole");
        getAttacksLabels().add("Sybil");
        getAttacksStatus().add(false);
        getAttacksStatus().add(false);
        getAttacksStatus().add(false);
    }

    @Override
    public void newRound() {
        if (getNode().isSinkNode()) {
            newRoutingDiscover();
        }
    }
}
