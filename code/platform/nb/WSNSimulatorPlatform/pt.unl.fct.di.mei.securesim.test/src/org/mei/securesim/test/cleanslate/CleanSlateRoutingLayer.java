package org.mei.securesim.test.cleanslate;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.DefaultMessage;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.cleanslate.messages.CleanSlateMsg;
import org.mei.securesim.test.cleanslate.utils.MergeTableEntry;
import org.mei.securesim.test.cleanslate.utils.NeighborInfo;
import org.mei.securesim.test.cleanslate.utils.ProtocolPhases;
import org.mei.securesim.test.common.ByteArrayDataInputStream;
import org.mei.securesim.test.common.events.DelayedMessageEvent;

/**
 *
 * @author CIAdmin
 */
public class CleanSlateRoutingLayer extends RoutingLayer {

    public static final byte DEBUG_LEVEL_NONE = 0;
    public static final byte DEBUG_LEVEL_NORMAL = 1;
    public static final byte DEBUG_LEVEL_FINE = 2;
    public static final byte DEBUG_LEVEL_FINNEST = 3;
    public static final byte DEBUG_LEVEL_ALL = 9;
    private byte debug_level = DEBUG_LEVEL_FINNEST;        // controls the debug level output
    protected long myGroupId;         // group ID were this node belongs
    protected short myGroupSize;          // group size were this node belongs
    protected Hashtable listNeighboringGroups = new Hashtable();
    protected Hashtable listNeighbors = new Hashtable();
    protected ProtocolPhases currentPhase = ProtocolPhases.NONE;
    protected ProtocolPhases previousPhase = null;
    protected short mergeCounter = 0;  // counts the number of group merges
    private long waitingMergeProposalFrom;
    private MergeProposalData currentMergeProposalData = null;  // current merge info
    private Vector mergeTable = new Vector(); // table of merging info
    private Vector routingTable = new Vector();   // routing table
    private String networkAddress = "";
    private Set groupInfoAnnouncements = new HashSet();
    private boolean inMergingProcess = false;
    private boolean receivedBroadcastMergeProposalRefuseMessage;
    //TODO: Com 3 nós eles conseguem se juntar, verificar porque não ficam os grupos actualizados
    //TODO: Trabalhar no broadcast para o grupo



    /**************************************************************************
     * ROUTING LAYER SPECIFIC OPERATIONS
     * 
     **************************************************************************/
    /**
     * Invoqued when the simulation starts
     */
    @Override
    public void autostart() {
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getNodeID() + " - Autostart procedure lauched");
        }
        myGroupId = getNode().getId();
        myGroupSize = 1;
        runSecureNeighboringDiscovery();
    }

    /**
     * Receives a message from de bottom layer (MAC)
     * @param message
     */
    @Override
    public void receiveMessage(Object message) {

        BaseMessageData data = new BaseMessageData(((CleanSlateMsg) message).getPayload());
        if (data.type != CleanSlateConstants.MSG_HELLO) {
            if (!isMyNeighbor(data.sourceId)) {
                return;
            }
        }
        switch (data.type) {
            case CleanSlateConstants.MSG_HELLO:
                receiveHelloMessage(message);
                break;
            case CleanSlateConstants.MSG_MERGE_PROPOSAL_REQUEST:
                receiveMergeProposalMessage(message);
                break;
            case CleanSlateConstants.MSG_KNOWNED_GROUP_INFO:
                receiveGroupInfoMessage(message);
                break;
            case CleanSlateConstants.MSG_POST_MERGE:
                receiveUpdateGroupInfoMessage(message);
                break;
            case CleanSlateConstants.MSG_BROADCAST_MERGE_PROPOSAL_REFUSE:
                receiveBroadcastMergeProposalRefuseMessage(message);
                break;
            case CleanSlateConstants.MSG_GROUP_NEIGHBORING_INFO:
                receiveGroupNeighboringInfoMessage(message);
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

    private void addToNeighborsList(HELLOMsgData helloData) {
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getNodeID() + " Added Neighbor " + helloData.sourceId);
        }
        listNeighbors.put(helloData.sourceId, new Long(-1));
    }

    /************************** END OF ROUTING SPECIFIC OPERATIONS ************/
    /**************************************************************************
     * UTIL FUNCTIONS
     **************************************************************************/
    /**
     * Util function to facilitate getting node id
     * @return
     */
    private String getNodeID() {
        return "<" + getNode().getSimulator().getSimulationTime() + ">  " + getNode().getId();
    }

    /**
     *
     * @return
     */
    protected CleanSlateNode getCSNode() {
        return (CleanSlateNode) getNode();
    }

    /**
     * 
     * @param message
     */
    private void sendMessageToAir(DefaultMessage message) {
        long time = (long) (getNode().getSimulator().getSimulationTime()
                + Simulator.randomGenerator.random().nextDouble() * CleanSlateConstants.DELAYED_MESSAGE_BOUND);
        DelayedMessageEvent delayMessageEvent = new DelayedMessageEvent(time, message, getNode());
        getNode().getSimulator().addEvent(delayMessageEvent);
    }

    /**
     * 
     * @param message
     */
    private void receiveHelloMessage(Object message) {
        if (currentPhase == ProtocolPhases.NEIGHBOR_DISCOVERY) {
            HELLOMsgData helloData = new HELLOMsgData(((CleanSlateMsg) message).getPayload());
            if (getNode().getMacLayer().getSignalStrength() > CleanSlateConstants.NEIGHBOR_SIGNAL_THRESHOLD) {
                if (debug_level > DEBUG_LEVEL_FINNEST) {
                    System.out.println(getNodeID() + " - Received Hello message from " + helloData.sourceId + " With RSSI: " + getNode().getMacLayer().getSignalStrength());
                }
                // verifify signature
                if (CleanSlateFunctions.verifySignature((CleanSlateMsg) message)) {
                    addToNeighborsList(helloData);
                }

            }

        }
    }

    /**
     * 
     * @param message
     */
    private void receiveMergeProposalMessage(Object message) {
        if (inMergingProcess) {
            return;
        }

        MergeProposalData mergeProposalData = new MergeProposalData(((DefaultMessage) message).getPayload());

        if (mergeProposalData.targetGroup == myGroupId) { // se for para mim ?
            if (mergeProposalData.sourceGroupID == waitingMergeProposalFrom) { // se estou à espera?
                sendMergeProposalTo(mergeProposalData.sourceGroupID);
                startMergingProcess(mergeProposalData);
                if (debug_level > DEBUG_LEVEL_NORMAL) {
                    System.out.println(getNodeID() + " - Received MergeProposal message from " + (long) mergeProposalData.sourceGroupID);
                }
            } else {
                if (mergeProposalData.sourceGroupID == chooseSmallestNeighborGroup()) { // é candidato a merge?
                    sendMergeProposalTo(mergeProposalData.sourceGroupID);
                    startMergingProcess(mergeProposalData);
                    if (debug_level > DEBUG_LEVEL_NORMAL) {
                        System.out.println(getNodeID() + " - Received MergeProposal message from " + (long) mergeProposalData.sourceGroupID);
                    }
                }
            }
        }
    }

    /**
     * 
     * @param message
     */
    private void receiveGroupNeighboringInfoMessage(Object message) {
        if (currentPhase != ProtocolPhases.FLOODING_NEIGHBOR_GROUPS_INFO) {
            return; //FIX: verificar se esta condição está correcta
        }
        GroupInfoBroadcastData groupInfoData = new GroupInfoBroadcastData(((DefaultMessage) message).getPayload());
        if (groupInfoData.sourceId == getNode().getId()) {
            return; // descarta se a mensagem vier de mim
        }
        if (groupInfoData.sourceGroupID == myGroupId) { // its a group message
            if (isEdgeNode()) {
                if (debug_level > DEBUG_LEVEL_FINNEST) {
                    System.out.println(getNodeID() + " - Receive Group: " + groupInfoData.sourceGroupID + " Neighboring Info Message from " + (long) groupInfoData.sourceId);
                }
                groupInfoAnnouncements.add(new NeighborInfo(groupInfoData.sourceGroupID, groupInfoData.sourceGroupSize));
                if (groupInfoAnnouncements.size() == listNeighboringGroups.size()) {
                    if (debug_level > DEBUG_LEVEL_FINE) {
                        System.out.println(getNodeID() + " - Number of Announcements reached the group size");
                    }
                    endNeighborInfoCollection();
                }
            }
        }
    }

    /**
     *
     * @param mergeProposalRefuseData
     */
    private void propagateMergeProposalRefuseToGroup(MergeProposalData mergeProposalRefuseData) {
        if (debug_level > DEBUG_LEVEL_NORMAL) {
            System.out.println(getNodeID() + " - Propagate Merge Proposal Refuse To Group Message ");
        }
        startMergeProposalRefuseBroadcast(mergeProposalRefuseData.sourceGroupID);
    }

    private void startMergeProposalRefuseBroadcast(long sourceGroupID) {
        byte[] payload = CleanSlateMessageFactory.createBroadcastMergeRefuseMessageToGroup(this, sourceGroupID, CleanSlateConstants.MSG_BROADCAST_MERGE_PROPOSAL_REFUSE);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
        if (isEdgeNode()) {
            scheduleSelectionNewGroupToMergeTimeBound();
        }
    }

    /**
     * 
     */
    private void scheduleSelectionNewGroupToMergeTimeBound() {
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.RESTART_GROUP_MERGE_BOUND_TIME;
        getNode().getSimulator().addEvent(
                new Event(time) {

                    @Override
                    public void execute() {
                        endNeighborInfoCollection();
                    }
                });
    }

    /**
     *
     */
    private void startMergingProcess(MergeProposalData mergeProposalData) {
        if (inMergingProcess) {
            return;
        }
        inMergingProcess = true;
        if (debug_level > DEBUG_LEVEL_NORMAL) {
            System.out.println(getNodeID() + " - Starting merging with " + (long) mergeProposalData.sourceGroupID);
        }
        updatePhase(ProtocolPhases.MERGE_GROUP);
        long newGroupID = computeNewGroupId(myGroupId, myGroupSize, mergeProposalData.sourceGroupID, mergeProposalData.sourceGroupSize);
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.println(getNodeID() + " - New Group ID " + newGroupID);
        }
        mergeCounter++;
        updateMergeTable(mergeProposalData);
        updateNetworkAddress(mergeProposalData);
        updateRoutingTable(mergeProposalData);
        updateMergeNeighborsList(mergeProposalData);
        myGroupId = newGroupID;
        broadcastNewMergeInfoToNeighbors(mergeProposalData);
    }

    /**
     *
     */
    private void broadcastNewMergeInfoToNeighbors(MergeProposalData mergeProposalData) {
        // este broadcast pode ser desencadeado a partir de uma mensagem delayed
        if (isEdgeNode()) {
            if (debug_level > DEBUG_LEVEL_NORMAL) {
                System.out.println(getNodeID() + "- broadcast New Merge Info To Neighbors");
            }
            byte[] payload = CleanSlateMessageFactory.createMergeProposalMessagePayload(this, mergeProposalData.targetGroup, CleanSlateConstants.MSG_POST_MERGE);
            CleanSlateMsg message = new CleanSlateMsg(payload);
            sendMessageToAir(message);
        }
        scheduleSelectionNewGroupToMergeTimeBound();
    }

    /**
     *
     * @param mergeProposalData
     */
    private void updateMergeNeighborsList(MergeProposalData mergeProposalData) {
        for (int i = 0; i < mergeProposalData.sourceNeighborsListSize; i++) {
            NeighborInfo ni = new NeighborInfo(mergeProposalData.sourceNeighborsListGroupID[i],
                    mergeProposalData.sourceNeighborsListGroupSize[i]);
            listNeighboringGroups.put(ni.getGroupID(), ni);

        }
        listNeighboringGroups.remove(mergeProposalData.sourceGroupID);
        listNeighboringGroups.remove(myGroupId);
        myGroupSize += mergeProposalData.sourceGroupSize;

        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.print(getNodeID() + " - [ ");
            for (Object object : listNeighboringGroups.values()) { // acrescentei  o values
                NeighborInfo n = (NeighborInfo) object;
                System.out.print(n + " ");
            }
            System.out.println(" ] ");
        }

    }

    private void updatePhase(ProtocolPhases phase) {
        previousPhase = currentPhase;
        currentPhase = phase;
    }

    /**
     * 
     * @param mergeProposalData
     */
    private void updateRoutingTable(MergeProposalData mergeProposalData) {
        // adds the node id from were he learned about group
        routingTable.add(mergeProposalData.sourceId);
    }

    /**
     * 
     * @param mergeProposalData
     */
    private void updateNetworkAddress(MergeProposalData mergeProposalData) {
        networkAddress += (myGroupId < mergeProposalData.sourceGroupID ? "0" : "1");
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.println(getNodeID() + " - Address: " + networkAddress);
        }
    }

    /**
     * 
     * @param mergeProposalData
     */
    private void updateMergeTable(MergeProposalData mergeProposalData) {
        mergeTable.add(new MergeTableEntry(mergeProposalData.sourceGroupID, mergeProposalData.sourceGroupSize));
    }

    /**
     *
     */
    private long computeNewGroupId(long mygid, short mysize, long gid, short size) {
        long newGroupID = 0;
        if (mygid > gid) {
            newGroupID = CleanSlateFunctions.groupIdHash(myGroupId, myGroupSize, gid, size);
        } else {
            newGroupID = CleanSlateFunctions.groupIdHash(gid, size, myGroupId, myGroupSize);
        }


        return newGroupID;
    }

    /**
     *
     * @param message
     */
    private void receiveGroupInfoMessage(Object message) {
        if (currentPhase != ProtocolPhases.GROUP_INFO_BROADCAST) {
            return;
        }
        byte[] payload = ((DefaultMessage) message).getPayload();
        GroupInfoBroadcastData groupInfoBroadcastData = new GroupInfoBroadcastData(payload);
        if (listNeighbors.keySet().contains(groupInfoBroadcastData.sourceId)) {
            listNeighbors.put(groupInfoBroadcastData.sourceId, groupInfoBroadcastData.sourceGroupID);
            if (debug_level > DEBUG_LEVEL_FINNEST) {
                System.out.println(getNodeID() + " - Received Group Info from " + groupInfoBroadcastData.sourceId);
            }
            listNeighboringGroups.put(groupInfoBroadcastData.sourceGroupID, new NeighborInfo(groupInfoBroadcastData.sourceGroupID, groupInfoBroadcastData.sourceGroupSize));
        } else {
            if (debug_level > DEBUG_LEVEL_FINNEST) {
                System.out.println(getNodeID() + " - MESSAGE SENT FROM NON NEIGHBOR NODE: " + groupInfoBroadcastData.sourceId);
            }
        }
    }

    /**
     * 
     * @param message
     */
    private void receiveUpdateGroupInfoMessage(Object message) {
        PostMergeData postMergeData = new PostMergeData(((DefaultMessage) message).getPayload());
        //Se estiver a fazer merge tenho q ter em atenção q tenho de descartar
        // as mensagens que vêm do nó a quem me estou a juntar
        if (inMergingProcess && postMergeData.sourceGroupID == waitingMergeProposalFrom) {
            return;
        }
        short nodeId = postMergeData.sourceId;
        Long oldGid = postMergeData.targetGroup;

        NeighborInfo ni = new NeighborInfo(postMergeData.sourceGroupID, postMergeData.sourceGroupSize);
        listNeighboringGroups.remove(oldGid);
        listNeighbors.put(nodeId, postMergeData.sourceGroupID);
        if (postMergeData.sourceGroupID != myGroupId) {
            listNeighboringGroups.put(postMergeData.sourceGroupID, ni); // update new group
            if (waitingMergeProposalFrom == oldGid && !inMergingProcess) {
                startMergeProposalRefuseBroadcast(oldGid);
            }
        }
        if (debug_level > DEBUG_LEVEL_FINNEST) {//TODO: Alterar o nivel de debug
            System.out.print(getNodeID() + " - Update group info: from " + postMergeData.sourceId);
            System.out.print(" - Old Group: " + oldGid);
            System.out.println(" - New Group " + postMergeData.sourceGroupID);
            for (Object object : listNeighboringGroups.values()) {
                System.out.print(" " + object + " ");
            }
            System.out.println();
        }
    }

    /**
     * 
     * @param message
     */
    private void receiveBroadcastMergeProposalRefuseMessage(Object message) {
        BroadcastMergeProposalRefuseData broadcastMergeProposalRefuseData = new BroadcastMergeProposalRefuseData(((DefaultMessage) message).getPayload());
        if (myGroupId == broadcastMergeProposalRefuseData.sourceGroupID) {
            if (!receivedBroadcastMergeProposalRefuseMessage) {
                if (debug_level > DEBUG_LEVEL_NONE) {
                    System.out.println(getNodeID() + " - Received a BroadcastMergeProposalRefuse from " + broadcastMergeProposalRefuseData.sourceId);
                }
                startMergeProposalRefuseBroadcast(broadcastMergeProposalRefuseData.sourceGroupID);
                receivedBroadcastMergeProposalRefuseMessage = true;
            }
        }
    }

    /**
     * 
     */
    private void startNeighborInfoCollection() {
        updatePhase(ProtocolPhases.NEIGHBOR_INFO_COLLECTION);
        if (isEdgeNode()) {
            floodGroupWithNeighborsInfo(); // TODO: algumas duvidas em relação à existência desta função
        }
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.COLLECT_NEIGHBOR_INFO_BOUND_TIME;
        Event e = new Event(time) {

            @Override
            public void execute() {
                endNeighborInfoCollection();
            }
        };
        getNode().getSimulator().addEvent(e);
    }

    /**
     *
     */
    private void endNeighborInfoCollection() {
        inMergingProcess = false;
        updatePhase(ProtocolPhases.MERGE_PROPOSAL);
        this.currentMergeProposalData = null;
        waitingMergeProposalFrom = -1;

        long gid = chooseSmallestNeighborGroup();
        if (gid > -1) {
            sendMergeProposalTo(gid);
        }
    }

    /**
     *
     */
    private void startRecursiveGroupingPhase() {
        updatePhase(ProtocolPhases.RECURSIVE_GROUPING);
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getNodeID() + " - Starting RecursiveGroupingAlgorithm");
            CleanSlateFunctions.printNeighborsGroupInfo(listNeighboringGroups.values(), getNode().getId()); // acrescentei o values
        }
        runRecursiveGroupingAlgorithm();
    }

    /**************************************************************************
     *  OPERATION METHODS
     **************************************************************************/
    private void runSecureNeighboringDiscovery() {
        updatePhase(ProtocolPhases.NEIGHBOR_DISCOVERY);
        periodicHelloMessagesBroadcast();
        scheduleSecureNetworkDiscoveryTimeBound();
    }

    /**
     *
     */
    private void periodicHelloMessagesBroadcast() {
        if (currentPhase == ProtocolPhases.NEIGHBOR_DISCOVERY) {
            sendMessageToAir(new CleanSlateMsg(CleanSlateMessageFactory.createHELLOMessagePayload(this)));
            initBroadcastHelloTimer();
        }
    }

    /**
     * Create a event to send  a hello message
     */
    private void initBroadcastHelloTimer() {
        long time = (int) (getNode().getSimulator().getSimulationTime() + Simulator.randomGenerator.random().nextDouble() * CleanSlateConstants.NEIGHBOR_DISCOVERY_REPEAT_TIME);
        Event e = new Event(time) {

            @Override
            public void execute() {
                periodicHelloMessagesBroadcast();
            }
        };
        getNode().getSimulator().addEvent(e);
    }

    /**
     * 
     */
    private void runRecursiveGroupingAlgorithm() {
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getNodeID() + " - Running RecursiveGroupingAlgorithm");
        }
        startNeighborInfoCollection();

    }

    /**
     * Schedules a event to bound the discovery time period
     */
    private void scheduleSecureNetworkDiscoveryTimeBound() {
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.NEIGHBOR_DISCOVERY_BOUND_TIME;
        Event e = new Event(time) {

            @Override
            public void execute() {
                startGroupInfoBroadcast();
            }
        };
        getNode().getSimulator().addEvent(e);
    }

    /**
     *
     */
    private void startGroupInfoBroadcast() {
        updatePhase(ProtocolPhases.GROUP_INFO_BROADCAST);
        periodicGroupInfoMessageBroadcast();
        scheduleGroupInfoBroadcastTimeBound();
    }

    /**
     * 
     */
    private void scheduleGroupInfoBroadcastTimeBound() {
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.COLLECT_NEIGHBOR_INFO_BOUND_TIME;
        Event e = new Event(time) {

            @Override
            public void execute() {
                startRecursiveGroupingPhase();
            }
        };
        getNode().getSimulator().addEvent(e);
    }

    /**
     *
     */
    private void periodicGroupInfoMessageBroadcast() {
        if (currentPhase == ProtocolPhases.GROUP_INFO_BROADCAST) {
            if (debug_level > DEBUG_LEVEL_FINNEST) {
                System.out.println(getNodeID() + " - Sent Group info message");
            }
            sendMessageToAir(new CleanSlateMsg(CleanSlateMessageFactory.createGroupInfoBroadcastMessagePayload(this, myGroupId, myGroupSize, CleanSlateConstants.MSG_KNOWNED_GROUP_INFO)));
            initBroadcastGroupInfoTimer();
        }
    }

    /**
     * Create a event to send  a hello message
     */
    private void initBroadcastGroupInfoTimer() {
        long time = (long) (getNode().getSimulator().getSimulationTime() + Simulator.randomGenerator.random().nextDouble() * CleanSlateConstants.GROUP_INFO_BROADCAST_REPEAT_TIME);
        Event e = new Event(time) {

            @Override
            public void execute() {
                periodicGroupInfoMessageBroadcast();
            }
        };
        getNode().getSimulator().addEvent(e);
    }

    /**
     * Choose the smallest knowned neighbor
     * @return
     */
    private long chooseSmallestNeighborGroup() {
        PriorityQueue queue = new PriorityQueue(listNeighboringGroups.values());
        Object o = queue.peek();
        return (o == null) ? -1 : (Long) ((NeighborInfo) o).getGroupID();
    }

    /**
     * Send a merge proposal to a group
     * @param gid
     */
    private void sendMergeProposalTo(long gid) {
        waitingMergeProposalFrom = gid;
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getNodeID() + " - Send MergeProposal message TO " + (long) gid + " Round: " + mergeCounter);
        }
        byte[] payload = CleanSlateMessageFactory.createMergeProposalMessagePayload(this, gid, CleanSlateConstants.MSG_MERGE_PROPOSAL_REQUEST);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
        receivedBroadcastMergeProposalRefuseMessage = false; //FIX test if this its a problem
        scheduleMergeProposalWaitTimeBound();

    }

    /**
     * All Edge Nodes have at least one neighbor that belongs to other group
     * diferent from mine
     * @return
     */
    private boolean isEdgeNode() {
        // one node is edge if has a neighbor node that not belongs to the same group
        for (Object object : listNeighbors.keySet()) {
            short s = (Short) object;
            Long g = (Long) listNeighbors.get(s);
            if (g != -1) {
                if (g != myGroupId) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 
     */
    private void floodGroupWithNeighborsInfo() {
        updatePhase(ProtocolPhases.FLOODING_NEIGHBOR_GROUPS_INFO);
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getNodeID() + " - Flood Group: " + myGroupId + " With Neighbors Info ");
        }
        if (isEdgeNode()) {
            for (Object object : listNeighboringGroups.values()) { // acrescentei o values
                NeighborInfo ni = (NeighborInfo) object;
                sendFloodingNeighborsInfo(ni.getGroupID(), ni.getSize());
            }
        }
    }

    /**
     * 
     */
    private void sendFloodingNeighborsInfo(long gid, short size) {
        byte[] payload = CleanSlateMessageFactory.createGroupInfoBroadcastMessagePayload(this, gid, size, CleanSlateConstants.MSG_GROUP_NEIGHBORING_INFO);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
    }

    private void schedulesGroupMerge() {
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.START_MERGE_BOUND_TIME;
        Event e = new Event(time) {

            @Override
            public void execute() {
                startMergingProcess(currentMergeProposalData);
            }
        };
        getNode().getSimulator().addEvent(e);

    }

    private boolean isMyNeighbor(short id) {
        return (listNeighbors.get(id) != null);
    }

    private void verifyMergePhaseStatus() {
        if (currentPhase == ProtocolPhases.MERGE_PROPOSAL) {
            // se estiver a aguardar por uma merge proposal
            // considero que já passou algum tempo e ainda n me juntei a ninguem
            // recomeço o processo
            // se já iniciou o processo ignoro este passo
//
//            updatePhase(ProtocolPhases.RECURSIVE_GROUPING);
//            if (debug_level > DEBUG_LEVEL_NONE) {
//                System.out.println(getNodeID() + " - mergeFailedByTimeout");
//            }
//            endNeighborInfoCollection();
        }
    }

    private void scheduleMergeProposalWaitTimeBound() {
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.MERGE_WAITING_BOUND_TIME;
        Event e = new Event(time) {

            @Override
            public void execute() {
                verifyMergePhaseStatus();
            }
        };
        getNode().getSimulator().addEvent(e);
    }

    /**
     *
     */
    class GroupInfoBroadcastData {

        byte type;
        long sourceGroupID;
        short sourceGroupSize;
        short sourceId;

        public GroupInfoBroadcastData(byte[] payload) {
            try {
                ByteArrayDataInputStream badis = new ByteArrayDataInputStream(payload);
                type = badis.readByte();
                sourceId = badis.readShort();
                sourceGroupID = badis.readLong();
                sourceGroupSize = badis.readShort();
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     *
     */
    class PostMergeData extends BaseMessageData {

        long targetGroup;
        long sourceGroupID;
        short sourceGroupSize;
        short sourceNeighborsListSize = 0;
        long[] sourceNeighborsListGroupID;
        short[] sourceNeighborsListGroupSize;

        public PostMergeData(byte[] payload) {
            super(payload);
            try {
                sourceGroupID = badis.readLong();
                sourceGroupSize = badis.readShort();
                sourceNeighborsListSize = badis.readShort();
                if (sourceGroupSize > 0) {
                    sourceNeighborsListGroupID = new long[sourceNeighborsListSize];
                    sourceNeighborsListGroupSize = new short[sourceNeighborsListSize];
                    for (int i = 0; i < sourceNeighborsListSize; i++) {
                        sourceNeighborsListGroupID[i] = badis.readLong();
                        sourceNeighborsListGroupSize[i] = badis.readShort();
                    }
                }
                targetGroup = badis.readLong();
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    class MergeProposalData extends BaseMessageData {

        long targetGroup;
        long sourceGroupID;
        short sourceGroupSize;
        short sourceNeighborsListSize = 0;
        long[] sourceNeighborsListGroupID;
        short[] sourceNeighborsListGroupSize;

        public MergeProposalData(byte[] payload) {
            super(payload);
            try {
                sourceGroupID = badis.readLong();
                sourceGroupSize = badis.readShort();
                sourceNeighborsListSize = badis.readShort();
                if (sourceGroupSize > 0) {
                    sourceNeighborsListGroupID = new long[sourceNeighborsListSize];
                    sourceNeighborsListGroupSize = new short[sourceNeighborsListSize];
                    for (int i = 0; i < sourceNeighborsListSize; i++) {
                        sourceNeighborsListGroupID[i] = badis.readLong();
                        sourceNeighborsListGroupSize[i] = badis.readShort();
                    }
                }
                targetGroup = badis.readLong();
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**************************************************************************
     *
     */
    class HELLOMsgData extends BaseMessageData {

        long groupID;
        byte[] signature;

        public HELLOMsgData(byte[] payload) {
            super(payload);
            try {
                groupID = badis.readLong();
                signature = new byte[CryptoFunctions.MAC_SIZE];
                badis.read(signature);
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class BroadcastMergeProposalRefuseData extends BaseMessageData {

        long sourceGroupID;
        long refuseGroupID;

        public BroadcastMergeProposalRefuseData(byte[] payload) {
            super(payload);
            try {
                sourceGroupID = badis.readLong();
                refuseGroupID = badis.readShort();
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    class BaseMessageData {

        ByteArrayDataInputStream badis;
        byte type;
        short sourceId;

        public BaseMessageData(byte[] payload) {
            try {
                badis = new ByteArrayDataInputStream(payload);
                type = badis.readByte();
                sourceId = badis.readShort();
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
