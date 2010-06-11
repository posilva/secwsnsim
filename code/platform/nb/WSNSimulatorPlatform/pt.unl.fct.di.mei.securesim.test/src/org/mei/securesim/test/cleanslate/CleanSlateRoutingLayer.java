package org.mei.securesim.test.cleanslate;

import java.util.*;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.*;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.cleanslate.messages.CleanSlateMsg;
import org.mei.securesim.test.cleanslate.messages.data.*;
import org.mei.securesim.test.cleanslate.utils.*;
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
    private byte debug_level = DEBUG_LEVEL_ALL;        // controls the debug level output
    protected long myGroupId;         // group ID were this node belongs
    protected short myGroupSize;          // group size were this node belongs
    protected Hashtable listNeighboringGroups = new Hashtable();
    protected Hashtable listNeighbors = new Hashtable();
    protected short mergeCounter = 0;  // counts the number of group merges
    private long waitingMergeProposalFrom;
    private Vector mergeTable = new Vector(); // table of merging info
    private Vector routingTable = new Vector();   // routing table
    private String networkAddress = "";
    private Set groupInfoAnnouncements = new HashSet();
    private boolean inMergingProcess = false;
    private int groupMergeRefuseMessafeSeqNumber = 0;
    private boolean secureNeighborDiscovery = false;
    private boolean recursiveGroupingInitiated = false;

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
                System.out.println(getInfo() + " - received a message from a non neighbor: " + data.sourceId);
                return;
            }
        }
        // saves neihgbors data to evaluate my edge node state
        updateNodeNeighbors(data.sourceId, data.sourceGroupId);
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
                receivePostMergeInfoMessage(message);
                break;
            case CleanSlateConstants.MSG_BROADCAST_MERGE_PROPOSAL_REFUSE:
                receiveBroadcastMergeProposalRefuseMessage(message);
                break;
            case CleanSlateConstants.MSG_GROUP_NEIGHBORING_INFO:
                receiveGroupNeighboringInfoMessage(message);
                break;
            case CleanSlateConstants.MSG_GROUP_MERGE:
                receiveGroupMergeMessage(message);
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
            System.out.println(getInfo() + " Added Neighbor " + helloData.sourceId);
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
     * 
     * @param message
     */
    private void sendMessageToAir(DefaultMessage message) {
        boolean reliable = false;
        long time = (long) (getNode().getSimulator().getSimulationTime()
                + Simulator.randomGenerator.nextDoubleBetween((int) CleanSlateConstants.MIN_DELAYED_MESSAGE_BOUND, (int) CleanSlateConstants.MAX_DELAYED_MESSAGE_BOUND));
        DelayedMessageEvent delayMessageEvent = new DelayedMessageEvent(time, message, getNode());
        getNode().getSimulator().addEvent(delayMessageEvent);
        if (reliable) {
            DelayedMessageEvent delayMessageEvent1 = new DelayedMessageEvent(time + 200, message, getNode());
            getNode().getSimulator().addEvent(delayMessageEvent1);
            DelayedMessageEvent delayMessageEvent2 = new DelayedMessageEvent(time + 200, message, getNode());
            getNode().getSimulator().addEvent(delayMessageEvent2);
        }
    }

    private void updateNodeNeighbors(short sourceNodeID, long groupId) {
        listNeighbors.put(sourceNodeID, groupId);
    }
    
    /**
     * 
     * @param message
     */
    private void receiveGroupMergeMessage(Object message) {
        if (inMergingProcess) {
            return;
        }
        GroupMergeData groupMergeData = new GroupMergeData(((CleanSlateMsg) message).getPayload());
        if (groupMergeData.sourceGroupId == myGroupId) { // intra group message
            MergeProposalData mergeProposalData = new MergeProposalData(groupMergeData.mergeProposalData);
            if (debug_level > DEBUG_LEVEL_FINNEST) {
                System.out.println(getInfo() + " - Start intra group merge with " + mergeProposalData.sourceGroupId);
            }
            startMergingProcess(mergeProposalData);
        }
    }

    /**
     * 
     * @param message
     */
    private void receiveHelloMessage(Object message) {
        if (secureNeighborDiscovery) {
            HELLOMsgData helloData = new HELLOMsgData(((CleanSlateMsg) message).getPayload());
            if (getNode().getMacLayer().getSignalStrength() > CleanSlateConstants.NEIGHBOR_SIGNAL_THRESHOLD) {
                if (debug_level > DEBUG_LEVEL_FINNEST) {
                    System.out.println(getInfo() + " - Received Hello message from " + helloData.sourceId + " With RSSI: " + getNode().getMacLayer().getSignalStrength());
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
            if (mergeProposalData.sourceGroupId == waitingMergeProposalFrom) { // se estou à espera?
                sendMergeProposalTo(mergeProposalData.sourceGroupId);
                if (debug_level > DEBUG_LEVEL_NORMAL) {
                    System.out.println(getInfo() + " - Received MergeProposal message from " + (long) mergeProposalData.sourceGroupId + " Notify group");
                }
                notifyGroup2Merge(mergeProposalData);
                startMergingProcess(mergeProposalData);
            } else {
                if (mergeProposalData.sourceGroupId == chooseSmallestNeighborGroup()) { // é candidato a merge?
                    sendMergeProposalTo(mergeProposalData.sourceGroupId);
                    if (debug_level > DEBUG_LEVEL_NORMAL) {
                        System.out.println(getInfo() + " - Received MergeProposal message from " + (long) mergeProposalData.sourceGroupId + " Notify group");
                    }
                    notifyGroup2Merge(mergeProposalData);
                    startMergingProcess(mergeProposalData);
                }
            }
        }
    }

    /**
     * 
     * @param message
     */
    private void receiveGroupNeighboringInfoMessage(Object message) {
        //TODO: Verificar a informação dos grupos que estou a receber 
        GroupInfoBroadcastData groupInfoData = new GroupInfoBroadcastData(((DefaultMessage) message).getPayload());
        if (groupInfoData.sourceId == getNode().getId()) {
            return; // descarta se a mensagem vier de mim
        }
        if (groupInfoData.sourceGroupId == myGroupId) { // its a group message
            if (isEdgeNode()) {
                if (debug_level > DEBUG_LEVEL_FINNEST) {
                    System.out.println(getInfo() + " - Receive Group: " + groupInfoData.sourceGroupId + " Neighboring Info Message from " + (long) groupInfoData.sourceId);
                }
                groupInfoAnnouncements.add(new NeighborInfo(groupInfoData.sourceGroupId, groupInfoData.sourceGroupSize));
                if (groupInfoAnnouncements.size() == listNeighboringGroups.size()) {
                    if (debug_level > DEBUG_LEVEL_FINE) {
                        System.out.println(getInfo() + " - Number of Announcements reached the group size");
                    }
                    initiateMergeProposals();
                }
            }
        }
    }

    private void startMergeProposalRefuseBroadcast(long sourceGroupID) {
        if (debug_level > DEBUG_LEVEL_NORMAL) {
            System.out.println(getInfo() + " - Propagate Merge Proposal Refuse To Group Message From " + sourceGroupID + " my merge is: "+waitingMergeProposalFrom);
        }
        byte[] payload = CleanSlateMessageFactory.createBroadcastMergeRefuseMessageToGroup(this, sourceGroupID, CleanSlateConstants.MSG_BROADCAST_MERGE_PROPOSAL_REFUSE, groupMergeRefuseMessafeSeqNumber++);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);

        waitingMergeProposalFrom = -1;
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
                        startNeighborInfoCollection();
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
            System.out.println(getInfo() + " - Starting merging with " + (long) mergeProposalData.sourceGroupId);
        }
        long newGroupID = computeNewGroupId(myGroupId, myGroupSize, mergeProposalData.sourceGroupId, mergeProposalData.sourceGroupSize);
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.println(getInfo() + " - New Group ID " + newGroupID);
        }
        mergeCounter++;
        updateMergeTable(mergeProposalData);
        updateNetworkAddress(mergeProposalData);
        updateRoutingTable(mergeProposalData);
        updateMergeNeighborsList(mergeProposalData);
        myGroupId = newGroupID;
        if (isEdgeNode()) {
            broadcastPostMergeInfo(mergeProposalData);
        }
    }

    /**
     *
     */
    private void broadcastPostMergeInfo(MergeProposalData mergeProposalData) {
        // este broadcast pode ser desencadeado a partir de uma mensagem delayed
        if (isEdgeNode()) {
            if (debug_level > DEBUG_LEVEL_NORMAL) {
                System.out.println(getInfo() + "- Broadcast Post Merge Info To Neighbors");
            }
            byte[] payload = CleanSlateMessageFactory.createPostMergeMessagePayload(this, mergeProposalData.sourceGroupId);
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
        listNeighboringGroups.remove(mergeProposalData.sourceGroupId);
        listNeighboringGroups.remove(myGroupId);
        myGroupSize += mergeProposalData.sourceGroupSize;

        if (debug_level > DEBUG_LEVEL_FINE) {
            CleanSlateFunctions.printNeighborsGroupInfo(listNeighboringGroups.values(), getInfo());
        }
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
        networkAddress += (myGroupId < mergeProposalData.sourceGroupId ? "0" : "1");
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.println(getInfo() + " - Address: " + networkAddress);
        }
    }

    /**
     * 
     * @param mergeProposalData
     */
    private void updateMergeTable(MergeProposalData mergeProposalData) {
        mergeTable.add(new MergeTableEntry(mergeProposalData.sourceGroupId, mergeProposalData.sourceGroupSize));
    }

    /**
     *
     */
    private long computeNewGroupId(long mygid, short mysize, long gid, short size) {
        long newGroupID = 0;
        if (mygid > gid) {
            newGroupID = CleanSlateFunctions.groupIdHash(mygid, mysize, gid, size);
        } else {
            newGroupID = CleanSlateFunctions.groupIdHash(gid, size, mygid, mysize);
        }
        return newGroupID;
    }

    /**
     * Receives the messages with de groups information
     * @param message
     */
    private void receiveGroupInfoMessage(Object message) {
        byte[] payload = ((DefaultMessage) message).getPayload();
        GroupInfoBroadcastData groupInfoBroadcastData = new GroupInfoBroadcastData(payload);
        if (listNeighbors.keySet().contains(groupInfoBroadcastData.sourceId)) {
            listNeighbors.put(groupInfoBroadcastData.sourceId, groupInfoBroadcastData.sourceGroupId);
            if (debug_level > DEBUG_LEVEL_FINNEST) {
                System.out.println(getInfo() + " - Received Group Info from " + groupInfoBroadcastData.sourceId);
            }
            listNeighboringGroups.put(groupInfoBroadcastData.sourceGroupId, new NeighborInfo(groupInfoBroadcastData.sourceGroupId, groupInfoBroadcastData.sourceGroupSize));
        } else {
            if (debug_level > DEBUG_LEVEL_FINNEST) {
                System.out.println(getInfo() + " - MESSAGE SENT FROM NON NEIGHBOR NODE: " + groupInfoBroadcastData.sourceId);
            }
        }
    }

    /**
     * 
     * @param message
     */
    private void receivePostMergeInfoMessage(Object message) {
        //TODO: Rever este metodo alterei a mensagem de postMerge
        PostMergeData postMergeData = new PostMergeData(((DefaultMessage) message).getPayload());
        //Se estiver a fazer merge tenho q ter em atenção q tenho de descartar
        // as mensagens que vêm do nó a quem me estou a juntar
        if (inMergingProcess || postMergeData.sourceGroupId == waitingMergeProposalFrom) {
            return;
        }
        short nodeId = postMergeData.sourceId; // quem se esta a juntar
        Long oldGid = postMergeData.sourceGroupId; // com quem se esta a juntar

        NeighborInfo ni = new NeighborInfo(postMergeData.sourceGroupId, postMergeData.sourceGroupSize);
        listNeighboringGroups.remove(oldGid);
        listNeighbors.put(nodeId, postMergeData.sourceGroupId);

        if (debug_level > DEBUG_LEVEL_FINNEST) {//TODO: Alterar o nivel de debug
            System.out.print(getInfo() + " - Received Post Merge info from " + postMergeData.sourceId);
            System.out.print(" - Old Group: " + oldGid);
            System.out.println(" - New Group " + postMergeData.sourceGroupId);
            for (Object object : listNeighboringGroups.values()) {
                System.out.print(" " + object + " ");
            }
            System.out.println();
        }

        if (postMergeData.sourceGroupId != myGroupId) { // it comes from my group
            listNeighboringGroups.put(postMergeData.sourceGroupId, ni); // update new group
            if (waitingMergeProposalFrom == oldGid && !inMergingProcess) {
                startMergeProposalRefuseBroadcast(oldGid);
            }
        }

    }

    /**
     * 
     * @param message
     */
    private void receiveBroadcastMergeProposalRefuseMessage(Object message) {
        if (inMergingProcess) {
            return;
        }
        BroadcastMergeProposalRefuseData broadcastMergeProposalRefuseData = new BroadcastMergeProposalRefuseData(((DefaultMessage) message).getPayload());
        if (myGroupId == broadcastMergeProposalRefuseData.sourceGroupId) {
            if (broadcastMergeProposalRefuseData.sequenceNumber > groupMergeRefuseMessafeSeqNumber) {
                groupMergeRefuseMessafeSeqNumber = broadcastMergeProposalRefuseData.sequenceNumber;
                if (debug_level > DEBUG_LEVEL_NONE) {
                    System.out.println(getInfo() + " - Received a Broadcast Merge Proposal Refuse from " + broadcastMergeProposalRefuseData.sourceId + " forward to neighbors");
                }
                startMergeProposalRefuseBroadcast(broadcastMergeProposalRefuseData.sourceGroupId);

            }
        }
    }

    /**
     *
     */
    private void startNeighborInfoCollection() {
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.println(getInfo() + " - Start new Merge");
        }
        if (isEdgeNode()) {
            floodGroupWithNeighborsInfo(); // TODO: algumas duvidas em relação à existência desta função
        }
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.COLLECT_NEIGHBOR_INFO_BOUND_TIME;
        Event e = new Event(time) {

            @Override
            public void execute() {
                initiateMergeProposals();
            }
        };
        getNode().getSimulator().addEvent(e);
    }

    /**
     *
     */
    private void initiateMergeProposals() {
        inMergingProcess = false;
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
        recursiveGroupingInitiated = true;
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getInfo() + " - Starting RecursiveGroupingAlgorithm");
            CleanSlateFunctions.printNeighborsGroupInfo(listNeighboringGroups.values(), getInfo());
        }
        runRecursiveGroupingAlgorithm();
    }

    /**************************************************************************
     *  OPERATION METHODS
     **************************************************************************/
    private void runSecureNeighboringDiscovery() {
        secureNeighborDiscovery = true;
        sendMessageToAir(new CleanSlateMsg(CleanSlateMessageFactory.createHELLOMessagePayload(this)));
        sendMessageToAir(new CleanSlateMsg(CleanSlateMessageFactory.createHELLOMessagePayload(this)));
        sendMessageToAir(new CleanSlateMsg(CleanSlateMessageFactory.createHELLOMessagePayload(this)));
        scheduleSecureNetworkDiscoveryTimeBound();


    }

    /**
     *
     */
    private void runRecursiveGroupingAlgorithm() {
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getInfo() + " - Running RecursiveGroupingAlgorithm");
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
        if (recursiveGroupingInitiated) {
            return;
        }
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getInfo() + " - Sent Group info message");
        }
        sendMessageToAir(new CleanSlateMsg(CleanSlateMessageFactory.createGroupInfoBroadcastMessagePayload(this, myGroupId, myGroupSize, CleanSlateConstants.MSG_KNOWNED_GROUP_INFO)));
        initBroadcastGroupInfoTimer();
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
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.println(getInfo() + " - Send MergeProposal message TO " + (long) gid + " Round: " + mergeCounter);
        }
        byte[] payload = CleanSlateMessageFactory.createMergeProposalMessagePayload(this, gid, CleanSlateConstants.MSG_MERGE_PROPOSAL_REQUEST);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
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
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.print(getInfo() + " - Flooding My Group: " + myGroupId + " With Neighbors Info ");
            CleanSlateFunctions.printNeighborsGroupInfo(listNeighboringGroups.values(), "");
        }
        if (isEdgeNode()) {
            for (Object object : listNeighboringGroups.values()) { // acrescentei o values
                NeighborInfo ni = (NeighborInfo) object;
                sendFloodingNeighborsInfo(
                        ni.getGroupID(), ni.getSize());
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

    private boolean isMyNeighbor(short id) {
        return (listNeighbors.get(id) != null);
    }

    private void broadcastToGroup() {
    }

    private void notifyGroup2Merge(MergeProposalData mergeProposalData) {
        byte[] payload = CleanSlateMessageFactory.createGroupMergeMessagePayload(this, mergeProposalData);
        sendMessageToAir(new CleanSlateMsg(payload));
    }
}
