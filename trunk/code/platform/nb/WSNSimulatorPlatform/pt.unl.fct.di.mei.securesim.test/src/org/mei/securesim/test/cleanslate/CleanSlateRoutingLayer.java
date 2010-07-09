package org.mei.securesim.test.cleanslate;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Vector;

import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.BaseMessage;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.events.Timer;
import org.mei.securesim.test.cleanslate.messages.CleanSlateMsg;
import org.mei.securesim.test.cleanslate.messages.data.BaseMessageData;
import org.mei.securesim.test.cleanslate.messages.data.HELLOMsgData;
import org.mei.securesim.test.cleanslate.messages.data.MergeProposalAgreementData;
import org.mei.securesim.test.cleanslate.messages.data.MergeProposalData;
import org.mei.securesim.test.cleanslate.messages.data.MergeProposalRefuseData;
import org.mei.securesim.test.cleanslate.messages.data.NeighborGroupAnnounceData;
import org.mei.securesim.test.cleanslate.messages.data.PostMergeData;
import org.mei.securesim.test.cleanslate.utils.MergeTableEntry;
import org.mei.securesim.test.cleanslate.utils.NeighborInfo;
import org.mei.securesim.protocols.common.events.DelayedMessageEvent;

/**
 *
 * @author CIAdmin
 */
public class CleanSlateRoutingLayer extends RoutingLayer {

    private byte debug_level = CleanSlateConstants.DEBUG_LEVEL_ALL;        // controls the debug level output
    protected long myGroupId;         // group ID were this node belongs
    protected short myGroupSize;          // group size were this node belongs
    private String networkAddress = "";
    protected Hashtable listNeighboringGroups = new Hashtable();
    protected Hashtable listNeighbors = new Hashtable();
    protected short mergeCounter = 0;  // counts the number of group merges
    private Vector mergeTable = new Vector(); // table of merging info
    private Vector routingTable = new Vector();   // routing table
    private Long waitingMergeProposalFrom = null;
    private boolean inMergingProcess = false, inSecureNeighborDiscovery = false;
    private Set neighborsGroupInformationAnnounces = new HashSet();
    private boolean waitingMergeProposal = false;
    private int mergeAgreementSEQNUM = 0;
    private int mergeRefuseSEQNUM = 0;
    private int mergeNeighborAnnouncesSEQNUM = 0;
    private Hashtable forwardGroupMergeAgreementMessages = new Hashtable();
    private Hashtable forwardGroupMergeRefuseMessages = new Hashtable();
    private Hashtable forwardGroupAnnouncesMessages = new Hashtable();
    public static final byte MAX_MERGE_TRIES = 3;
    private byte mergeTriesCounter = 0;
    short nodeState = CleanSlateConstants.BOOT_STATE;
    Timer networkDiscoveryTimer;
    Timer mergeCheckTimer;

    /**************************************************************************
     * ROUTING LAYER SPECIFIC OPERATIONS
     **************************************************************************/
    /**
     * Invoqued when the simulation starts
     */
    @Override
    public void setup() {
        myGroupId = getNode().getId();
        myGroupSize = 1;
        configureTimers();
        if (nodeState == CleanSlateConstants.BOOT_STATE) {
            nodeState = CleanSlateConstants.NEIGHBOR_DISCOVERY_STATE;
            runSecureNeighboringDiscovery();
        }

    }

    /**
     * Receives a message from de bottom layer (MAC)
     * @param message
     */
    @Override
    public void receiveMessage(Object m) {

        BaseMessageData data = new BaseMessageData(((CleanSlateMsg) m).getPayload());
        // verify if I received a message from a legal neighbor
        if (data.type != CleanSlateConstants.MSG_HELLO) {
            if (!isMyNeighbor(data.forwardId)) {
                return;
            }
        }

        // saves neihgbors data to evaluate my edge node state
        updateNodeNeighbors(data.forwardId, data.forwardGroupId);
        CleanSlateMsg message = (CleanSlateMsg) m;
        switch (data.type) {
            case CleanSlateConstants.MSG_HELLO:
                receiveHelloMessage(message);
                break;
            case CleanSlateConstants.MSG_MERGE_PROPOSAL:
                receiveMergeProposalMessage(message);
                break;
            case CleanSlateConstants.MSG_MERGE_PROPOSAL_AGREEMENT:
                receiveMergeProposalAgreementMessage(message);
                break;
            case CleanSlateConstants.MSG_MERGE_PROPOSAL_REFUSE:
                receiveMergeProposalRefuseMessage(message);
                break;
            case CleanSlateConstants.MSG_POST_MERGE:
                receivePostMergeMessage(message);
                break;
            case CleanSlateConstants.MSG_NEIGHBOR_GROUP_ANNOUNCE:
                receiveNeighborsGroupAnnounceMessage(message);
                break;
        }
    }

    /**
     * This method is invoqued when message arrives go destination
     */
    @Override
    public void sendMessageDone() {
    }

    /**
     * This method receives messages from application layer
     * @param message
     * @param app
     * @return
     */
    @Override
    public boolean sendMessage(Object message, Application app) {
        return true;
    }

    /************************** END OF ROUTING SPECIFIC OPERATIONS ************/
    /**************************************************************************
     * UTIL FUNCTIONS
     **************************************************************************/
    /**
     * Util function to facilitate getting node id
     * @return
     */
    private String getInfo() {
        return "<" + getNode().getSimulator().getSimulationTime() + ">  " + getNode().getId() + " [ " + myGroupId + "," + myGroupSize + " ]";
    }

    /**
     *
     * @return
     */
    protected CleanSlateNode getCSNode() {
        return (CleanSlateNode) getNode();
    }

    /**
     * Uses Hello message information to update neighbors list
     * @param helloData
     */
    private void addToNeighborsList(HELLOMsgData helloData) {
        listNeighbors.put(helloData.sourceId, helloData.sourceGroupId);
    }

    /************************** END OF UTILS FUNCTIONS ************/
    /**
     * All Edge Nodes have at least one neighbor that belongs to other group
     * diferent from mine
     * @return
     */
    private boolean iAmEdgeNode() {
        // one node is edge if has a neighbor node that not belongs to the same group
        for (Object object : listNeighbors.keySet()) {
            short s = (Short) object;
            Long g = (Long) listNeighbors.get(s);
            if (g != null) {
                if (g != myGroupId) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if a node belongs to my neighbors list
     * @param id
     * @return
     */
    private boolean isMyNeighbor(short id) {
        return (listNeighbors.get(id) != null);
    }

    /**
     * Sends a message without reliability to the air
     * @param message
     */
    private void sendMessageToAir(BaseMessage message) {
        sendMessageToAir(message, false);
    }

    /**
     * Sent the message to the air, if send fail then try again
     * @see DelayedMessageEvent
     * @param message
     */
    private void sendMessageToAir(BaseMessage message, boolean reliable) {

        long time = (long) (getNode().getSimulator().getSimulationTime()
                + Simulator.randomGenerator.nextDoubleBetween((int) CleanSlateConstants.MIN_DELAYED_MESSAGE_BOUND, (int) CleanSlateConstants.MAX_DELAYED_MESSAGE_BOUND));
        DelayedMessageEvent delayMessageEvent = new DelayedMessageEvent(time, message, getNode());
        delayMessageEvent.setReliable(reliable);
        getNode().getSimulator().addEvent(delayMessageEvent);
    }

    /**
     * Update neighbors information based on messages received.
     * This must be keeptd updated because the edge status probe
     * @param sourceNodeID
     * @param groupId
     */
    private void updateNodeNeighbors(short sourceNodeID, long groupId) {
        listNeighbors.put(sourceNodeID, groupId);
    }

    /**
     * 
     * @param message
     */
    private void receiveHelloMessage(CleanSlateMsg message) {
        if (inSecureNeighborDiscovery) {
            HELLOMsgData helloData = new HELLOMsgData(((CleanSlateMsg) message).getPayload());
            if (getNode().getMacLayer().getSignalStrength() > CleanSlateConstants.NEIGHBOR_SIGNAL_THRESHOLD) {
                // verifify signature
                //    System.out.println("getNode().getMacLayer().getSignalStrength(): " + getNode().getMacLayer().getSignalStrength());
                if (CleanSlateFunctions.verifySignature((CleanSlateMsg) message)) {
                    addToNeighborsList(helloData);
                }
            }
        }
    }

    /**
     * Handles the message Merge Proposal Refuse
     * This messages is sent by edge nodes when detects that a merge candidate
     * node proposes a merge with other node.
     * This message is used Intra Group comunication
     * @param message
     */
    private void receiveMergeProposalRefuseMessage(CleanSlateMsg message) {
        MergeProposalRefuseData messageData = new MergeProposalRefuseData(message.getPayload());
        if (inMergingProcess) {
            return;
        }
        if (messageData.sourceGroupId == myGroupId) { // its a group comunication message
            forwardGroupMergeRefuseMessage(messageData);
            if (iAmEdgeNode()) {
                waitingMergeProposal = false;
                startMergeProposal(); // start a new merge proposal phase
            }
        }
    }

    /**
     * Handles the post merge messages
     * This messages are sent by edge nodes to inform about new group info
     * This is a message for neighboring groups not for intra group coordination
     * @param message
     */
    private void receivePostMergeMessage(CleanSlateMsg message) {
        PostMergeData messageData = new PostMergeData(message.getPayload());

        if (messageData.sourceGroupId != myGroupId && messageData.oldGroupId != myGroupId) {
            // esta mensagem não é intra group então deverá vir de um edge node de outro grupo
            // update my list of group neighbors
            listNeighboringGroups.remove(messageData.oldGroupId);
            listNeighboringGroups.remove(messageData.targetGroup);

            NeighborInfo ni = new NeighborInfo(messageData.sourceGroupId, messageData.sourceGroupSize);
            listNeighboringGroups.put(messageData.sourceGroupId, ni);
            System.out.print(getInfo() + " - Received a Post Merge UPDATE " + messageData.oldGroupId + " with " + ni + " - ");

            CleanSlateFunctions.printNeighborsGroupInfo(listNeighboringGroups.values(), "");
            if (messageData.oldGroupId == waitingMergeProposalFrom && waitingMergeProposal) {
                if (messageData.targetGroup == myGroupId && !inMergingProcess) {
                    // it will come a new message proposal in near future
                    System.out.println(getInfo() + " - I AM WAITING FOR A MERGE PROPOSAL FROM " + messageData.oldGroupId);
                    waitingMergeProposal = true;
                } else {
                    mergeTriesCounter = 0;
                    // my merge candidate already merge with other group
                    // lets notify group and restart merge process
                    System.out.println(getInfo() + " - old group " + messageData.oldGroupId + " was my candidate to merge notfy group");
                    waitingMergeProposal = false; // if my propose was refuse I am no longer wait for a proposal
                    // notify my group
                    startNewMergeProposalRefuseGroupFlooding(messageData.oldGroupId);
                    // lets start again
                    startMergeProposal();
                }
            }
        }
    }

    /**
     * Handles the neighbors group announce message
     * This is a periodic message that enables a edge node to initiate the merge
     * proposal. This is a intra group coordination message
     * @param message
     */
    private void receiveNeighborsGroupAnnounceMessage(CleanSlateMsg message) {
        if (waitingMergeProposal) {
            return;
        }
        NeighborGroupAnnounceData messageData = new NeighborGroupAnnounceData(message.getPayload());
        if (messageData.sourceGroupId == myGroupId) {
            // its a group message and is from my group
            if (messageData.sourceId != getNode().getId()) {
                // if it was not initiated for me
                updateNeighborsGroupsInformation(messageData);

                forwardNeighborsGroupsAnnouncesMessage(messageData);
                for (int i = 0; i < messageData.sourceNeighborsListSize; i++) {
                    NeighborInfo ni = new NeighborInfo(messageData.sourceNeighborsListGroupID[i], messageData.sourceNeighborsListGroupSize[i]);
                    neighborsGroupInformationAnnounces.add(ni);
                }
                startMergeProposal();
            }// else discard it
        }
    }

    /**
     * Handles the merge proposal messages
     * This must only be handled for a edge node, if not a edge node discard
     * Just the edge nodes receive merge proposals
     * @param message
     */
    private void receiveMergeProposalMessage(CleanSlateMsg message) {
        // just edge nodes can receive merge proposals, internal nodes cannot
        if (inMergingProcess || !iAmEdgeNode()) {
            return;
        }
        // read message data
        MergeProposalData messageData = new MergeProposalData(message.getPayload()); // TODO: Pode passar para depois
        if (messageData.targetGroup == myGroupId) {
            // if is destinated to my group
            System.out.print(getInfo() + " - Received a Merge Proposal from " + messageData.sourceGroupId);
            if (waitingMergeProposal) {
                System.out.print(" I am waiting for it: ");
                // if i am waiting for a proposal response
                if (messageData.sourceGroupId == waitingMergeProposalFrom) {
                    // repeat the merge proposal to enforce connectivity
                    // TODO: may be deleted in future for now is commented
                    System.out.println(" Is from my last Proposal : " + waitingMergeProposalFrom);
                    //PMS        sendMergeProposalTo(waitingMergeProposalFrom);
                } else {
                    return;
                }
            } else {
                System.out.print(" Verify if is a candidate to me: ");

                // verify if can be my candidate (I don't send the proposal yet)
                Long candidateGroupId = chooseSmallestNeighborGroup(); // i want the first
                if (candidateGroupId != null) {
                    if (messageData.sourceGroupId == candidateGroupId) {
                        sendMergeProposalTo(candidateGroupId);
                        System.out.print(" Yes. Sent a proposal");
                        mergeTriesCounter = 0;
                    } else {
                        System.out.println();
                        return; // discards
                    }
                }
            }
            System.out.println(" Notify group for the merge agreement. ");
            startNewMergeProposalAgreementGroupFlooding(messageData);

            initiateMergeOperations(messageData);
        } else {

            if (messageData.sourceGroupId == waitingMergeProposalFrom) {
                // my proposal candidate group decided to join with other group
                // so I have to notify my group with a merge proposal refuse and
                // I could restart the merge again
//                System.out.println(getInfo() + " - Received a merge proposal from my candidate " + messageData.sourceGroupId + " With " + messageData.targetGroup + " is a refuse merge");
//                startNewMergeProposalRefuseGroupFlooding(messageData.sourceGroupId);
//                waitingMergeProposal = false;
//                startMergeProposal(); // let's try again
            }
        }
    }

    /**
     * Handle Merge Proposal Agreement Messages
     * This messages are group messages to notify all group about the merge 
     * agreement done by one of the edge nodes
     * @param message
     */
    private void receiveMergeProposalAgreementMessage(CleanSlateMsg message) {
        // I am merging so I' dont do anything
        if (inMergingProcess) {
            return;
        }
        MergeProposalAgreementData messageData = new MergeProposalAgreementData(message.getPayload());
        System.out.println(getInfo() + "Received a merge proposal agreement frrom " + messageData.sourceId);
        if (messageData.sourceGroupId == myGroupId) {
            // its from my group
            System.out.println(getInfo() + " - Received a Merge Proposal Agreement from " + messageData.sourceId);
            forwardGroupMergeAgreementMessage(messageData);

            initiateMergeOperations(new MergeProposalData(messageData.mergeProposalPayload));
        } // else discard message
    }

    /**
     * Initiate the merging process in a asynchronous manner
     * @param messageData
     */
    private void initiateMergeOperations(MergeProposalData messageData) {
        waitingMergeProposal = false;
        if (inMergingProcess) {
            return; // i'm allready started a merge
        }
        inMergingProcess = true;
        System.out.println(getInfo() + " - INITIATED Merge Operations for merge proposal with  " + messageData.sourceGroupId);
        long newGroupID = CleanSlateFunctions.computeNewGroupId(myGroupId, myGroupSize, messageData.sourceGroupId, messageData.sourceGroupSize);
        networkAddress = CleanSlateFunctions.networkAddressBit(myGroupId, messageData.sourceGroupId) + networkAddress;
        mergeTable.add(new MergeTableEntry(messageData.sourceGroupId, messageData.sourceGroupSize));
        routingTable.add(messageData.sourceId);
        updateMergeNeighborsList(messageData);
        myGroupSize += messageData.sourceGroupSize;
        myGroupId = newGroupID;
        mergeCounter++;
        listNeighboringGroups.remove(myGroupId);
        postMergeOperations(messageData);
        System.out.println(getInfo() + " - MERGE DONE with " + messageData.sourceGroupId);
        waitingMergeProposal = false;
        startMergeProposal();
        mergeTriesCounter = 0;
    }

    /**
     * Execute Post Merge Operations
     * This must be done only by edge nodes 
     * Intended to inform all neighboring groups about the new group info
     */
    private void postMergeOperations(MergeProposalData messageData) {
        if (iAmEdgeNode()) {
            System.out.println(getInfo() + " - Send Post Merge Message");
            byte[] payload = CleanSlateMessageFactory.createPostMergeMessagePayload(this, messageData.sourceGroupId, messageData.targetGroup);
            broadcast(new CleanSlateMsg(payload));
        }
        startNewNeighborsGroupsAnnounceGroupFlooding();
        inMergingProcess = false;
    }

    /**
     * Merge the neighbor group information from the merge proposal
     * @param mergeProposalData
     */
    private void updateMergeNeighborsList(MergeProposalData mergeProposalData) {
        for (int i = 0; i < mergeProposalData.sourceNeighborsListSize; i++) {
            NeighborInfo ni = new NeighborInfo(mergeProposalData.sourceNeighborsListGroupID[i],
                    mergeProposalData.sourceNeighborsListGroupSize[i]);
            listNeighboringGroups.put(ni.getGroupID(), ni);
        }
        listNeighboringGroups.remove(mergeProposalData.sourceGroupId);
        listNeighboringGroups.remove(myGroupId);
    }

    /**************************************************************************
     *  OPERATION METHODS
     **************************************************************************/
    private void runSecureNeighboringDiscovery() {
        inSecureNeighborDiscovery = true;
        // substitui a as mensagens periodicas por 3 chamadas a hello


        networkDiscoveryTimer.start(CleanSlateConstants.MAX_DISCOVERY_MESSAGES_SENT, CleanSlateConstants.DISCOVERY_MESSAGES_INTERVAL);
        scheduleSecureNetworkDiscoveryTimeBound();
    }

    /**
     * Schedules a event to bound the discovery time period
     */
    private void scheduleSecureNetworkDiscoveryTimeBound() {
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.NEIGHBOR_DISCOVERY_BOUND_TIME;
        Event e = new Event(time) {

            @Override
            public void execute() {
                updateNeighborsGroupWithNeighborsHello();
                startMergeProposal();
            }
        };
        getNode().getSimulator().addEvent(e);
    }

    /**
     * Starting merge proposal procedure
     * Just for Edge Nodes
     */
    private void startMergeProposal() {
        if (!iAmEdgeNode()) {
            return;
        }
        
        int neighborsGroupInformationAnnouncesSize = neighborsGroupInformationAnnounces.size();
        boolean conditionToStartMergeProposal = (myGroupSize == 1) || (neighborsGroupInformationAnnouncesSize == listNeighboringGroups.size());
        if (conditionToStartMergeProposal && !waitingMergeProposal) {
            Long candidateGroupID = chooseSmallestNeighborGroup(); // gets the next one
            if (candidateGroupID != null) {
                sendMergeProposalTo(candidateGroupID);
                nodeState = CleanSlateConstants.MERGE_PROPOSAL_STATE;
            } else {
                System.out.println(getInfo() + "- NO CANDIDATE");
                printRoutingTable();
            }
        }
    }

    private void updateNeighborsGroupWithNeighborsHello() {
        for (Object key : listNeighbors.keySet()) {
            short nodeId = (Short) key;
            Long groupId = (Long) listNeighbors.get(nodeId);
            listNeighboringGroups.put(groupId, new NeighborInfo(groupId, (short) 1));
        }
    }

    /**
     * Choose the smallest knowned neighbor
     * @return
     */
    private Long chooseSmallestNeighborGroup() {
        PriorityQueue queue = new PriorityQueue();
        for (Object o : listNeighboringGroups.values()) {
            NeighborInfo ni = (NeighborInfo) o;
            if (waitingMergeProposalFrom == null || waitingMergeProposalFrom != ni.getGroupID()) {
                queue.add(ni);
            }
        }
        return (queue.peek() == null) ? null : ((NeighborInfo) queue.peek()).getGroupID();
    }

    /**
     * Send a merge proposal to a group
     * @param gid
     */
    private void sendMergeProposalTo(long gid) {
        waitingMergeProposalFrom = gid;
        waitingMergeProposal = true;
        if (debug_level > CleanSlateConstants.DEBUG_LEVEL_FINE) {
            System.out.print(getInfo() + " - Send MergeProposal message TO " + (long) gid + " Round: " + mergeCounter + " ");
            CleanSlateFunctions.printNeighborsGroupInfo(listNeighboringGroups.values(), "");
        }
        byte[] payload = CleanSlateMessageFactory.createMergeProposalMessagePayload(this, gid, CleanSlateConstants.MSG_MERGE_PROPOSAL);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
    }

    /**
     *  Broadcast to group 
     * @param mergeProposalRefuseMessage
     */
    private void broadcastToGroup(CleanSlateMsg message) {
        sendMessageToAir(message, true);
    }

    /**
     * Broadcast to the air
     * @param message
     */
    private void broadcast(CleanSlateMsg message) {
        sendMessageToAir(message);
    }

    /**
     * Updates my neighbors group information
     * @param messageData
     */
    private void updateNeighborsGroupsInformation(NeighborGroupAnnounceData messageData) {
        System.out.println(getInfo() + " - updating neighbors info using the announce from " + messageData.forwardGroupId);
        for (int i = 0; i < messageData.sourceNeighborsListSize; i++) {
            NeighborInfo ni = new NeighborInfo(messageData.sourceNeighborsListGroupID[i], messageData.sourceNeighborsListGroupSize[i]);
            listNeighboringGroups.put(ni.getGroupID(), ni);
        }
    }

    private void printRoutingTable() {
        System.out.println(getInfo() + " - ROUTING TABLE -");
        System.out.println("| \t");
    }

    private void forwardGroupMergeAgreementMessage(MergeProposalAgreementData messageData) {
        Integer seqNum = (Integer) forwardGroupMergeAgreementMessages.get(messageData.sourceId);
        if (seqNum == null) {
            forwardGroupMergeAgreementMessages.put(messageData.sourceId, messageData.sequenceNumber);
        } else {
            if (messageData.sequenceNumber > seqNum) {
                forwardGroupMergeAgreementMessages.put(messageData.sourceId, messageData.sequenceNumber);
            } else {
                return; // not a fresh message discard it
            }
        }
        System.out.println(getInfo() + "  - forwardGroupMergeAgreementMessage SN:" + messageData.sequenceNumber + " Sent by " + messageData.sourceGroupId);
        byte[] payload = CleanSlateMessageFactory.createMergeProposalAgreementMessagePayload(this,
                new MergeProposalData(messageData.mergeProposalPayload),
                messageData.sequenceNumber,
                messageData.sourceId,
                messageData.sourceGroupId);
        broadcastToGroup(new CleanSlateMsg(payload));
    }

    private void forwardGroupMergeRefuseMessage(MergeProposalRefuseData messageData) {
        Integer seqNum = (Integer) forwardGroupMergeRefuseMessages.get(messageData.sourceId);
        if (seqNum == null) {
            forwardGroupMergeRefuseMessages.put(messageData.sourceId, messageData.sequenceNumber);
        } else {
            if (messageData.sequenceNumber > seqNum) {
                forwardGroupMergeRefuseMessages.put(messageData.sourceId, messageData.sequenceNumber);
            } else {
                return; // not a fresh message discard it
            }
        }
        System.out.println(getInfo() + "  - forwardGroupMergeRefuseMessage SN:" + messageData.sequenceNumber + " Sent by " + messageData.sourceGroupId);

        byte[] payload = CleanSlateMessageFactory.createMergeProposalRefuseMessagePayload(this, messageData.refusedGroupId,
                messageData.sequenceNumber,
                messageData.sourceId,
                messageData.sourceGroupId);
        broadcastToGroup(new CleanSlateMsg(payload));
    }

    private void forwardNeighborsGroupsAnnouncesMessage(NeighborGroupAnnounceData messageData) {
        Integer seqNum = (Integer) forwardGroupAnnouncesMessages.get(messageData.sourceId);
        if (seqNum == null) {
            forwardGroupAnnouncesMessages.put(messageData.sourceId, messageData.sequenceNumber);
        } else {
            if (messageData.sequenceNumber > seqNum) {
                forwardGroupAnnouncesMessages.put(messageData.sourceId, messageData.sequenceNumber);
            } else {
                return; // not a fresh message discard it
            }
        }
        System.out.println(getInfo() + "  - forwardNeighborsGroupsAnnouncesMessage SN:" + messageData.sequenceNumber + " Sent by " + messageData.sourceGroupId);
        byte[] payload = CleanSlateMessageFactory.createNeighborGroupAnnounceMessagePayload(this,
                messageData.sequenceNumber,
                messageData.sourceId,
                messageData.sourceGroupId);
        broadcastToGroup(new CleanSlateMsg(payload));
    }

    /**
     * Starting group flooding of the merge proposal refuse
     * @param messageData
     */
    private void startNewMergeProposalRefuseGroupFlooding(long refusedGroupId) {
        System.out.println(getInfo() + " -  startNewMergeProposalRefuseGroupFlooding  by " + refusedGroupId);
        byte[] payload = CleanSlateMessageFactory.createMergeProposalRefuseMessagePayload(this, refusedGroupId, mergeRefuseSEQNUM++, getNode().getId(), myGroupId);
        broadcastToGroup(new CleanSlateMsg(payload));
    }

    /**
     * Initiate a merge agreement message flooding to group
     * @param messageData
     */
    private void startNewMergeProposalAgreementGroupFlooding(MergeProposalData messageData) {
        System.out.println(getInfo() + " -  Send a Merge Proposal Agreement to Group SEQ: " + mergeAgreementSEQNUM);
        byte[] payload = CleanSlateMessageFactory.createMergeProposalAgreementMessagePayload(this, messageData, mergeAgreementSEQNUM++, getNode().getId(), myGroupId);
        broadcastToGroup(new CleanSlateMsg(payload));
    }

    /**
     * Start a neighbors group announce flooding to group
     * @param messageData
     */
    private void startNewNeighborsGroupsAnnounceGroupFlooding() {
        System.out.println(getInfo() + " -  Start New Neighbors Groups Announce Group Flooding SEQ: " + mergeNeighborAnnouncesSEQNUM);

        byte[] payload = CleanSlateMessageFactory.createNeighborGroupAnnounceMessagePayload(this,
                mergeNeighborAnnouncesSEQNUM++,
                getNode().getId(),
                myGroupId);
        broadcastToGroup(new CleanSlateMsg(payload));
    }

    /**
     * Node timers can be set here
     */
    private void configureTimers() {
        networkDiscoveryTimer = new Timer() {

            @Override
            public void executeAction() {
                sendMessageToAir(new CleanSlateMsg(CleanSlateMessageFactory.createHELLOMessagePayload(CleanSlateRoutingLayer.this)));
            }
        };

        mergeCheckTimer = new Timer() {

            @Override
            public void executeAction() {
                if (nodeState == CleanSlateConstants.MERGE_WAITING_STATE) {
                    mergeTriesCounter++;
                    if (mergeTriesCounter > MAX_MERGE_TRIES) {
                        mergeTriesCounter = 0;
                        switchToMergeProposalState();
                    }
                }
            }
        };
    }

    /**
     * 
     */
    private void switchToMergeProposalState() {
       nodeState=CleanSlateConstants.MERGE_PROPOSAL_STATE;
       startMergeProposal();
    }

    @Override
    public void routeMessage(Object message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
