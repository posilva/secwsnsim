package org.wisenet.protocols.insens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.wisenet.protocols.common.ByteArrayDataOutputStream;
import org.wisenet.protocols.common.events.DelayedMessageEvent;
import org.wisenet.protocols.insens.attacks.BlackholeRoutingAttack;
import org.wisenet.protocols.insens.basestation.BaseStationController;
import org.wisenet.protocols.insens.basestation.ForwardingTable;
import org.wisenet.protocols.insens.messages.INSENSMessage;
import org.wisenet.protocols.insens.messages.INSENSMessagePayloadFactory;
import org.wisenet.protocols.insens.messages.data.DATAPayload;
import org.wisenet.protocols.insens.messages.data.FDBKPayload;
import org.wisenet.protocols.insens.messages.data.RREQPayload;
import org.wisenet.protocols.insens.messages.data.RUPDPayload;
import org.wisenet.protocols.insens.utils.NeighborInfo;
import org.wisenet.protocols.insens.utils.NetworkKeyStore;
import org.wisenet.protocols.insens.utils.OneWaySequenceNumbersChain;
import org.wisenet.simulator.utilities.CryptoFunctions;
import org.wisenet.simulator.components.instruments.IInstrumentHandler;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.events.Timer;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksEntry;
//TODO: Ter em conta que Ã© preciso ter um determinado numero de vizinhos para grantir q o protocolo funciona
//TODO: Ã‰ necessÃ¡rio que se avalie a forÃ§a do sinal por forma a aceitar ou nÃ£o os RREQ
//TODO: Verificar porque se tem tantas vezes a transmitir quando se recebe e o q
//se pode fazer para minimizar esse impacto

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class INSENSRoutingLayer extends RoutingLayer implements IInstrumentHandler {

    /**
     *
     */
    public static final String PHASE_FORWARD_DATA = "FORWARD_DATA";
    /**
     *
     */
    public static final String PHASE_ROUTE_FEEDBACK = "ROUTE_FEEDBACK";
    /**
     *
     */
    public static final String PHASE_ROUTE_REQUEST = "ROUTE_REQUEST";
    /**
     *
     */
    public static final String PHASE_ROUTE_UPDATE = "ROUTE_UPDATE";
    /**
     *
     */
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
    private LinkedList messagesQueue = new LinkedList();
    private boolean sendingMessage;
    /**
     * Counters for control protocol
     */
    private short feedbackMessagesReceived = 0;
    private short lastFeedbackMessagesReceivedCheck = 0;
    private byte feedbackMessageRetries;
    private Hashtable tableOfNodesByHops;
    private boolean reliableMode = false;

    public INSENSRoutingLayer() {
        setDebugEnabled(false);
    }

    /**
     * Receive a message from the MAC Layer
     * @param message
     */
    @Override
    public void onReceiveMessage(Object message) {
        setDebugEnabled(false);
        if (message instanceof INSENSMessage) {
            try {
                INSENSMessage m = (INSENSMessage) message;
                byte type = INSENSFunctions.getMessageType(m);
                if (INSENSFunctions.verifyMAC(m.getPayload(), type)) {
                    switch (type) {
                        case INSENSConstants.MSG_ROUTE_REQUEST:
                            setCurrentPhase(PHASE_ROUTE_REQUEST);
                            getController().addMessageReceivedCounter(INSENSConstants.MSG_ROUTE_REQUEST);
                            processRREQMessage(m);
                            break;
                        case INSENSConstants.MSG_FEEDBACK:
                            setCurrentPhase(PHASE_ROUTE_FEEDBACK);
                            getController().addMessageReceivedCounter(INSENSConstants.MSG_FEEDBACK);
                            processFDBKMessage(m);
                            break;
                        case INSENSConstants.MSG_ROUTE_UPDATE:
                            setCurrentPhase(PHASE_ROUTE_UPDATE);
                            getController().addMessageReceivedCounter(INSENSConstants.MSG_ROUTE_UPDATE);
                            processRUPDMessage(m);
                            break;
                        case INSENSConstants.MSG_ROUTE_UPDATE_ACK:
                            getController().addMessageReceivedCounter(INSENSConstants.MSG_ROUTE_UPDATE_ACK);
                            processRUPDACKMessage(m);
                            break;
                        case INSENSConstants.MSG_DATA:
                            setCurrentPhase(PHASE_FORWARD_DATA);
                            getController().addMessageReceivedCounter(INSENSConstants.MSG_DATA);
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

    /**
     * Send a message that came from application layer
     * @param message
     *          Message to be sent
     * @param app
     *          Sender Application 
     * @return   True success
     *           False failed
     */
    @Override
    protected boolean onSendMessage(Object message, Application app) {
        if (isStable()) {
            return sendDATAMessage((Message) message);
        } else {
            return false;
        }

    }

    /**
     * Setup the routing layer 
     */
    @Override
    protected void onStartUp() {
        setCurrentPhase(PHASE_SETUP);
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

    /**
     * Gets the current OWS
     * @return
     */
    public long getOWS() {
        return OWS;
    }

    /**
     * Gets the node private key
     * @return
     */
    public byte[] getPrivateKey() {
        return privateKey;
    }

    /**
     * Action done when message routing needed
     * @param message
     *          Message to be routed
     */
    @Override
    protected void onRouteMessage(Object message) {
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
                            getController().addMessageSentCounter(INSENSConstants.MSG_ROUTE_UPDATE);
                            send((Message) m.clone());
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
                            getController().addMessageSentCounter(INSENSConstants.MSG_DATA);
                            send((Message) m.clone());
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
     * Broadcast a message
     * @param message
     * @param reliable
     */
    private void broadcastMessage(Message message) {
        sendingMessage = true;
        long delay = (long) Simulator.randomGenerator.nextDoubleBetween((int) INSENSConstants.MIN_DELAYED_MESSAGE_BOUND, (int) INSENSConstants.MAX_DELAYED_MESSAGE_BOUND);
        long time = (long) (Simulator.getSimulationTime());
        DelayedMessageEvent delayMessageEvent = new DelayedMessageEvent(time, delay, message, getNode());
        delayMessageEvent.setReliable(reliableMode);
        getNode().getSimulator().addEvent(delayMessageEvent);
    }

    /**
     * Auxiliary function to create a list of nodes joined by number of hops
     * @param allpaths
     * @return
     */
    private Hashtable buildListOfNodestByHops(Vector allpaths) {
        Hashtable t = new Hashtable();
        for (Object list : allpaths) {
            ArrayList lst = (ArrayList) list;
            ArrayList lst2 = (ArrayList) t.get(lst.size());
            if (lst2 == null) {
                lst2 = new ArrayList();
            }
            lst2.add(lst.get(lst.size() - 1));
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

    protected Message encapsulateMessage(Message message) {

        INSENSMessage m = new INSENSMessage();
        m.setUniqueId(message.getUniqueId());
        m.setSourceId(message.getSourceId());
        m.setDestinationId(message.getDestinationId());
        byte[] new_payload = encapsulateDataPayload(message);
        m.setPayload(new_payload);
        getController().addMessageSentCounter(INSENSConstants.MSG_DATA);
        return m;
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
        this.feedbackMessageStartTimer = new Timer(getNode().getSimulator(), 1, INSENSConstants.FEEDBACK_START_TIME_BOUND + getNode().getId() * 100) {

            @Override
            public void executeAction() {
                sendFeedbackMessageInfo();

            }
        };
        this.startForwardTablesCalculesTimer = new Timer(getNode().getSimulator(), INSENSConstants.FEEDBACK_MSG_RECEIVED_TIME_BOUND) {

            @Override
            public void executeAction() {
                startComputeRoutingInfo();
            }
        };
        this.queueMessageDispatchTimer = new Timer(getNode().getSimulator(), INSENSConstants.MESSAGE_DISPATCH_RATE) {

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
        getController().addMessageSentCounter(INSENSConstants.MSG_FEEDBACK);
        send(message);
//        feedbackMessageStartTimer.reschedule();
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
            getController().addMessageSentCounter(INSENSConstants.MSG_ROUTE_REQUEST);
            send(m);
            startForwardTablesCalculesTimer.start();
        } catch (INSENSException ex) {
            log(ex);
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
                if (getNode().getMacLayer().getSignalStrength() > INSENSConstants.SIGNAL_STRENGH_THRESHOLD && getNode().getMacLayer().getNoiseStrength() < INSENSConstants.SIGNAL_NOISE_THRESHOLD) {
                    if (owsIsValid(payload)) {
                        isParent = true;
                        roundOWS = payload.ows; // updates the round ows
                        log("Received RREQ From " + m.getSourceId());
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
        getController().addMessageSentCounter(INSENSConstants.MSG_ROUTE_REQUEST);
        send(message);
    }

    /**
     * Process a Feedback message message
     * @param m
     */
    private void processFDBKMessage(INSENSMessage m) throws INSENSException {
        FDBKPayload payload = new FDBKPayload(m.getPayload());
        if (Arrays.equals(myRoundMAC, payload.parent_mac)) { // is from my child
            if (getNode().isSinkNode()) { // if i'm a sink node keep the information
                baseStationController.addFeedbackMessages(payload);
                INSENSFunctions.decryptData(getNode(), payload.neighborInfo, null);
                feedbackMessagesReceived++;
            } else { // forward the message if is from my child
                byte[] new_payload = modifiedParentMAC(payload);
                getController().addMessageSentCounter(INSENSConstants.MSG_FEEDBACK);
                send(new INSENSMessage(new_payload));
                sendACKFeedbackMessage(payload);
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
                /**
                 * Este teste permite diminuir a densidade da rede em virtude de
                 * dimunir as colisoes na rede
                 */
//              if(!getNode().getMacLayer().isReceiving() &&  !getNode().getMacLayer().isTransmitting()){

                broadcastMessage((Message) messagesQueue.peek());
                
//                System.out.println("NOISE:" + getNode().getMacLayer().getNoiseStrength());
//                System.out.println("STRENGTH:" + getNode().getMacLayer().getSignalStrength());
//              }
            } else {
                queueMessageDispatchTimer.stop();
            }
        }
    }

    /**
     * Get neighbor info
     * @return the neighbor information
     */
    public NeighborInfo getNeighborInfo() {
        return neighborInfo;
    }

    /**
     * Send messages with pre-calculated routing tables
     * @param forwardingTables
     */
    private void sendRouteUpdateMessages(Hashtable<Short, ForwardingTable> forwardingTables) {
        Vector allpaths = baseStationController.getAllPaths();
        Comparator<List> c = new Comparator<List>() {

            public int compare(List o1, List o2) {
                return Integer.valueOf(o1.size()).compareTo(Integer.valueOf(o2.size()));
            }
        };
        Collections.sort(allpaths, c);
        tableOfNodesByHops = new Hashtable();
        tableOfNodesByHops = buildListOfNodestByHops(allpaths);
        sendForwardingTablesByHops(forwardingTables, tableOfNodesByHops);
        setStable(true);
    }

    /**
     * Process a RUPD message
     * With this method we can get some reliable RouteUpdate messages
     * by controlling the message acknowledge
     * @param m
     */
    private void processRUPDACKMessage(INSENSMessage m) {
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

    /**
     * For each hop number based nodes send a Forwarding table
     * @param forwardingTables
     * @param tableOfNodesByHops
     */
    private void sendForwardingTablesByHops(Hashtable<Short, ForwardingTable> forwardingTables, Hashtable tableOfNodesByHops) {

        List orderedByHops = new LinkedList(tableOfNodesByHops.keySet());
        Collections.sort(orderedByHops);
        byte[] payload;
        for (Object key : orderedByHops) {
            List nodes = (List) tableOfNodesByHops.get(key);
            for (Object n : nodes) {

                Short id = (Short) n;
                if (forwardingTables.containsKey(id)) {
                    payload = INSENSMessagePayloadFactory.createRUPDPayload(getNode().getId(), (Short) n, getNode().getId(), OWS, forwardingTables.get(id), privateKey, this.getNode());
                    getController().addMessageSentCounter(INSENSConstants.MSG_ROUTE_UPDATE);
                    send(new INSENSMessage(payload));
                }
            }
        }
    }

    /**
     * When receive a route update message, we must update the routing status
     * of the node, ie. change status to stable (which can route messages)
     * @param payload
     */
    private void updateRoutingStatus(RUPDPayload payload) {
        forwardingTable = new ForwardingTable(getNode().getId());
        forwardingTable.addAll(payload.forwardingTable);
        setStable(true);
        replyToRUPDMessage(payload);
    }

    /**
     * Send a route update message acknowledge to base station
     * @param payload
     */
    private void replyToRUPDMessage(RUPDPayload payload) {
        log("Replying to RUPD Message");
    }

    /**
     * When routing is stable we can process a data message
     * this messages are application specific 
     * @param m
     *          the message
     */
    private void processDATAMessage(INSENSMessage m) {
        if (isStable()) {
            if (!itsForMe(m)) {
                routeMessage(m);
            } else {
                try {
                    DATAPayload payload = new DATAPayload(m.getPayload());
                    INSENSFunctions.decryptData(getNode(), payload.data, null);
                    getNode().getApplication().receiveMessage(payload.data);
                    done(m);
                } catch (INSENSException ex) {
                    log(ex);
                }
            }
        }
    }

    /**
     * Send a DATA message 
     * @param message
     * @return
     */
    private boolean sendDATAMessage(Message message) {
        INSENSMessage m = (INSENSMessage) encapsulateMessage(message);
        send((Message) m);
        return true;
    }

    /**
     * Create a DATA message payload
     * @param message
     * @return
     */
    private byte[] encapsulateDataPayload(Message message) {
        return INSENSMessagePayloadFactory.createDATAPayload((Short) message.getSourceId(), (Short) message.getDestinationId(), getNode().getId(), message.getPayload(), privateKey, this.getNode());
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return forwardingTable.toString();
    }

    public Object getUniqueId() {
        return getNode().getId();
    }

    @Override
    protected String getRoutingTable() {
        return forwardingTable.toString();
    }

    @Override
    protected void setupAttacks() {
    }

    @Override
    public void newRound() {
        if (getNode().isSinkNode()) {
            newRoutingDiscover();
        }
    }

    @Override
    protected void sendMessageToAir(Object message) {
        messagesQueue.addLast((Message) message);
        if (queueMessageDispatchTimer.isStop()) {
            sendingMessage = false;
            queueMessageDispatchTimer.start();
        }
    }

    /**
     * 
     * @param oldValue
     */
    @Override
    protected void onStable(boolean oldValue) {
        if (oldValue == false) {
//            boolean attackStatus = getNode().getId() % 3 == 0;
//            setUnderAttack(attackStatus);
        }
    }

    @Override
    protected void initAttacks() {
        AttacksEntry entry = new AttacksEntry(false, "Blackhole Attack", new BlackholeRoutingAttack(this));
        attacks.addEntry(entry);
        getController().registerAttack(entry);
    }

    @Override
    public void reset() {
        super.reset();
        messagesQueue.clear();
        neighborInfo = new NeighborInfo();
        sendingMessage = false;
        feedbackMessagesReceived = 0;
        lastFeedbackMessagesReceivedCheck = 0;
        feedbackMessageRetries = 0;
        tableOfNodesByHops.clear();
        reliableMode = false;
    }

    private void sendACKFeedbackMessage(FDBKPayload payload) {
        // sending a feed back message ACK to the child
    }
}
