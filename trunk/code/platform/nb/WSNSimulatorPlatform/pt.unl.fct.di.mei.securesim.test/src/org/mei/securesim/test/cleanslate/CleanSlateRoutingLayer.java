package org.mei.securesim.test.cleanslate;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
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
import org.mei.securesim.test.common.ByteArrayDataOutputStream;
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
    /**
     * Constants declaration
     */
    private byte debug_level = DEBUG_LEVEL_FINNEST;        // controls the debug level output
    protected long myGroupId;         // group ID were this node belongs
    protected short myGroupSize;          // group size were this node belongs
    protected Hashtable listNeighboringGroups = new Hashtable();
    protected Set listNeighboringGroupsRefuse = new HashSet();
    protected Hashtable listNeighbors = new Hashtable();
    protected ProtocolPhases currentPhase = ProtocolPhases.NONE;
    protected ProtocolPhases previousPhase = null;
    protected short mergeCounter = 0;  // counts the number of group merges
    private long lastMergeProposalSent;
    private boolean mergeProposalWasSent;
    private long lastMergeProposalRefuseSent;
    private boolean mergeProposalRefuseWasSent;
    private MergeProposalData currentMergeProposalData = null;  // current merge info
    private Vector mergeTable = new Vector(); // table of merging info
    private Vector routingTable = new Vector();   // routing table
    private String networkAddress = "";
    private PriorityQueue listNeighboringGroupsWorkingCopy;
    private boolean receivedBroadcastMergeProposalRefuseMessage;
    private Set groupInfoAnnouncements = new HashSet();

    // TODO: Retirei a repetição do merge proposal assumindo que ambos recebem
    // TODO: Garantir que depois do discovery as mensagens que recebe são sempre de nós vizinhos
    // TODO: Verificar porque um grupo depois de se juntar os elementos desse grupo agem em separado
    // TODO: Continuação - Juntando-se os ambos os nós aos mesmos nós, e os nós do mesmo grupo voltam a juntar-se
    // TODO: Cont. - Começar por verificar a selecção do minimo grupo
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
        byte type = CleanSlateFunctions.getMessageTypeFromPayload(((CleanSlateMsg) message).getPayload());
        switch (type) {
            case CleanSlateConstants.MSG_HELLO:
                receiveHelloMessage(message);
                break;
            case CleanSlateConstants.MSG_MERGE_PROPOSAL_REQUEST:
                receiveMergeProposalMessage(message);
                break;
            case CleanSlateConstants.MSG_KNOWNED_GROUP_INFO:
                receiveGroupInfoMessage(message);
                break;
            case CleanSlateConstants.MSG_MERGE_PROPOSAL_REFUSE:
                receiveMergeProposalRefuseMessage(message);
                break;
            case CleanSlateConstants.MSG_UPDATE_GROUPINFO:
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
        listNeighbors.put(helloData.sourceID, new Long(-1));
    }

    /************************** END OF ROUTING SPECIFIC OPERATIONS ************/
    /**************************************************************************
     * UTIL FUNCTIONS
     **************************************************************************/
    /**
     * Util function to facilitate getting node id
     * @return
     */
    private short getNodeID() {
        return getNode().getId();
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
            if (debug_level > DEBUG_LEVEL_FINNEST) {
                System.out.println(getNodeID() + " - Received Hello message from " + helloData.sourceID);
            }
            // verifify signature
            if (CleanSlateFunctions.verifySignature((CleanSlateMsg) message)) {
                addToNeighborsList(helloData);
            }
        }
    }

    /**
     * 
     * @param message
     */
    private void receiveMergeProposalMessage(Object message) {
        if (currentPhase != ProtocolPhases.MERGE_PROPOSAL) {
            return;
        }

        MergeProposalData mergeProposalData = new MergeProposalData(((DefaultMessage) message).getPayload());

        if (mergeProposalData.targetGroup != myGroupId) {
            return;
        }

        if (debug_level > DEBUG_LEVEL_NORMAL) {
            System.out.println(getNodeID() + " - Received MergeProposal message from " + (long) mergeProposalData.sourceGroupID);
        }
        evaluateMergeProposal(mergeProposalData);
    }

    /**
     * 
     * @param message
     */
    private void receiveMergeProposalRefuseMessage(Object message) {
        if (currentPhase != ProtocolPhases.MERGE_PROPOSAL) {
            return;
        }

        MergeProposalData mergeProposalRefuseData = new MergeProposalData(((DefaultMessage) message).getPayload());
        // se a resposta é de alguem a quem enviei a proposta
        if (lastMergeProposalSent == mergeProposalRefuseData.sourceGroupID) {
            if (debug_level > DEBUG_LEVEL_NORMAL) {
                System.out.println(getNodeID() + " - Receive Merge Proposal Refuse Message from " + (long) mergeProposalRefuseData.sourceGroupID);
            }
            if (isEdgeNode()) {
                propagateMergeProposalRefuseToGroup(mergeProposalRefuseData);
            }
        } // senão descarto a mensagem
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
                if (debug_level > DEBUG_LEVEL_NORMAL) {
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

    /**
     * 
     * @param mergeProposalRefuseData
     */
    private void startMergeProposalRefuseBroadcast(long sourceGroupID) {
        byte[] payload = createBroadcastMergeRefuseMessageToGroup(sourceGroupID, CleanSlateConstants.MSG_BROADCAST_MERGE_PROPOSAL_REFUSE);
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
     * @param mergeProposalData
     */
    private void evaluateMergeProposal(MergeProposalData mergeProposalData) {
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.println(getNodeID() + " - Evaluating Merge Proposal");
        }
        if (currentPhase == ProtocolPhases.MERGE_PROPOSAL) {
            if (this.lastMergeProposalSent == mergeProposalData.sourceGroupID) {

                // this mensagens enforces the ack for group merging
                sendMergeProposalTo(mergeProposalData.sourceGroupID);
                this.currentMergeProposalData = mergeProposalData;
                schedulesGroupMerge();
                this.lastMergeProposalSent = -1;
                this.mergeProposalWasSent = false;
                if (debug_level > DEBUG_LEVEL_FINE) {
                    System.out.println("\t... enforced de Ack for proposal and start group merge");
                }
            } else {
                if (debug_level > DEBUG_LEVEL_FINE) {
                    System.out.println("\t... Send refuse");
                }
                if (this.currentMergeProposalData == null) {
                    // TODO: Verificar se só os nós edge é q recusam e q tratam mensagens de proposal
                    
                    sendMergeProposalRefuseTo(mergeProposalData.sourceGroupID);
                }
            }
        } else {
            System.out.println("\t... Not in MergeProposal Phase");
        }
    }

    /**
     *
     * @param gid
     */
    private void sendMergeProposalRefuseTo(long gid) {
        lastMergeProposalRefuseSent = gid;
        if (debug_level > DEBUG_LEVEL_NONE) {
            System.out.println(getNodeID() + " - Send Merge Proposal Refuse To " + (long) gid);
        }
        byte[] payload = createMergeProposalMessagePayload(gid, CleanSlateConstants.MSG_MERGE_PROPOSAL_REFUSE);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
        mergeProposalRefuseWasSent = true;
    }

    /**
     *
     */
    private void startGroupMerge(MergeProposalData mergeProposalData) {
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
        broadcastNewMergeInfoToNeighbors();
        // TODO: implement endgroupMerge Method
        //      endGroupMerge();
    }

    /**
     * 
     */
    private void endGroupMerge() {
    }

    /**
     *
     */
    private void broadcastNewMergeInfoToNeighbors() {
        // este broadcast pode ser desencadeado a partir de uma mensagem delayed
        if (isEdgeNode()) {
            if (debug_level > DEBUG_LEVEL_NORMAL) {
                System.out.println(getNodeID() + "- broadcast New Merge Info To Neighbors");
            }
            byte[] payload = createMergeProposalMessagePayload(myGroupId, CleanSlateConstants.MSG_UPDATE_GROUPINFO);
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
            if (debug_level > DEBUG_LEVEL_NONE) {
                System.out.println(getNodeID() + " - MESSAGE SENT FROM NON NEIGHBOR NODE: " + groupInfoBroadcastData.sourceId);
            }
        }
    }

    /**
     * 
     * @param message
     */
    private void receiveUpdateGroupInfoMessage(Object message) {
        MergeProposalData updateGroupInfo = new MergeProposalData(((DefaultMessage) message).getPayload());
        short nodeId = updateGroupInfo.sourceId;
        Long oldGid = (Long) listNeighbors.get(nodeId);
        if (oldGid != null) {
            listNeighboringGroups.remove(oldGid); // remove old group
            listNeighboringGroupsWorkingCopy.remove(oldGid);
            NeighborInfo ni = new NeighborInfo(updateGroupInfo.sourceGroupID, updateGroupInfo.sourceGroupSize);
            listNeighboringGroups.put(updateGroupInfo.sourceGroupID, ni); // update new group
            listNeighboringGroupsWorkingCopy.add(ni);
            listNeighbors.put(nodeId, updateGroupInfo.sourceGroupID);
            if (debug_level > DEBUG_LEVEL_FINE) {
                System.out.println(getNodeID() + " - Update group info: from " + updateGroupInfo.sourceId);
                System.out.println(getNodeID() + " - Old Group: " + oldGid);
                System.out.println(getNodeID() + " - New Group " + updateGroupInfo.sourceGroupID);
            }



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
                startMergeProposalRefuseBroadcast(broadcastMergeProposalRefuseData.refuseGroupID);
                receivedBroadcastMergeProposalRefuseMessage = true;
            }
        }
    }

    /**
     * 
     */
    private void startNeighborInfoCollection() {
        this.previousPhase = this.currentPhase;
        this.currentPhase = ProtocolPhases.NEIGHBOR_INFO_COLLECTION;
        if (isEdgeNode()) {
            floodGroupWithNeighborsInfo();
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
        this.previousPhase = this.currentPhase;
        this.currentPhase = ProtocolPhases.MERGE_PROPOSAL;
        this.currentMergeProposalData = null;
        long gid = chooseSmallestNeighborGroup();
        if (gid > -1) {
            sendMergeProposalTo(gid);
        }
    }

    /**************************************************************************
     *  PAYLOAD CREATION FUNCTIONS
     **************************************************************************/
    private byte[] createHELLOMessagePayload() {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_HELLO);
            bados.writeShort(getNode().getId());
            bados.write(CleanSlateFunctions.signData(CleanSlateFunctions.shortToByteArray(getNode().getId()), getCSNode().getNAKey()));
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     */
    private void startRecursiveGroupingPhase() {

        updatePhase(ProtocolPhases.RECURSIVE_GROUPING);
        prepareMergeGroupsWorkingCopy();
        if (debug_level > DEBUG_LEVEL_NORMAL) {
            System.out.println(getNodeID() + " - Starting RecursiveGroupingAlgorithm");
            CleanSlateFunctions.printNeighborsGroupInfo(listNeighboringGroups.values(), getNode().getId()); // acrescentei o values
        }

        runRecursiveGroupingAlgorithm();

    }

    private void prepareMergeGroupsWorkingCopy() {
        if (debug_level > DEBUG_LEVEL_FINE) {
            System.out.println(getNodeID() + " - Preparing Working Copy");
        }
        this.listNeighboringGroupsWorkingCopy = new PriorityQueue(listNeighboringGroups.values()); // acrescentei o values
    }

    /**************************************************************************
     *  OPERATION METHODS
     **************************************************************************/
    private void runSecureNeighboringDiscovery() {
        updatePhase(ProtocolPhases.NEIGHBOR_DISCOVERY);
        if (debug_level > DEBUG_LEVEL_FINNEST) {
            System.out.println(getNodeID() + " - Running Secure Neighboring Discovery");
        }
        periodicHelloMessagesBroadcast();
        // send HELLO messages every NSecs secs
        scheduleSecureNetworkDiscoveryTimeBound();
    }

    /**
     *
     */
    private void periodicHelloMessagesBroadcast() {
        if (currentPhase == ProtocolPhases.NEIGHBOR_DISCOVERY) {
            if (debug_level > DEBUG_LEVEL_FINNEST) {
                System.out.println(getNodeID() + " - Sent HELLO message");
            }
            sendMessageToAir(new CleanSlateMsg(createHELLOMessagePayload()));
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
        if (debug_level > DEBUG_LEVEL_NORMAL) {
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
            sendMessageToAir(new CleanSlateMsg(createGroupInfoBroadcastMessagePayload(myGroupId, myGroupSize, CleanSlateConstants.MSG_KNOWNED_GROUP_INFO)));
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
        // escolher o menor grupo dos que ainda n foram recusados
        Object o = listNeighboringGroupsWorkingCopy.poll();
        if (o == null) {
            return -1;
        }
        return (Long) ((NeighborInfo) o).getGroupID();
    }

    /**
     * Send a merge proposal to a group
     * @param gid
     */
    private void sendMergeProposalTo(long gid) {

        lastMergeProposalSent = gid;
        if (debug_level > DEBUG_LEVEL_NONE) {
            System.out.println(getNodeID() + " - Send MergeProposal message TO " + (long) gid + " Round: " + mergeCounter);
        }
        byte[] payload = createMergeProposalMessagePayload(gid, CleanSlateConstants.MSG_MERGE_PROPOSAL_REQUEST);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
        receivedBroadcastMergeProposalRefuseMessage = false; //FIX test if this its a problem
        mergeProposalWasSent = true;

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

    private void floodGroupWithNeighborsInfo() {
        updatePhase(ProtocolPhases.FLOODING_NEIGHBOR_GROUPS_INFO);
        if (debug_level > DEBUG_LEVEL_NONE) {
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
        byte[] payload = createGroupInfoBroadcastMessagePayload(gid, size, CleanSlateConstants.MSG_GROUP_NEIGHBORING_INFO);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
    }

    /**
     * 
     * @param gid
     * @param type
     * @return
     */
    private byte[] createMergeProposalMessagePayload(long gid, byte type) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(type);
            bados.writeShort(getNode().getId()); // source node ID
            bados.writeLong(myGroupId); // source group ID
            bados.writeShort(myGroupSize); // source group Size
            bados.writeShort(listNeighboringGroups.size()); // size of neighbors list

            for (Iterator it = listNeighboringGroups.values().iterator(); it.hasNext();) { // acrescentei o values
                NeighborInfo ni = (NeighborInfo) it.next();
                bados.writeLong(ni.getGroupID());
                bados.writeShort(ni.getSize());
            } // write list of neighbors

            bados.writeLong(gid); // update group ID
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void schedulesGroupMerge() {
        long time = getNode().getSimulator().getSimulationTime() + CleanSlateConstants.START_MERGE_BOUND_TIME;
        Event e = new Event(time) {

            @Override
            public void execute() {
                startGroupMerge(currentMergeProposalData);
            }
        };
        getNode().getSimulator().addEvent(e);

    }

    private byte[] createGroupInfoBroadcastMessagePayload(long gid, short size, byte type) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(type);
            bados.writeShort(getNode().getId()); // source node ID
            bados.writeLong(gid); // source group ID
            bados.writeShort(size); // source group Size
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private byte[] createBroadcastMergeRefuseMessageToGroup(long sourceGroupID, byte type) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(type);
            bados.writeShort(getNode().getId()); // source node ID
            bados.writeLong(myGroupId); // source group ID
            bados.writeLong(sourceGroupID); // group that refuses the merge
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

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
    class MergeProposalData {

        byte type;
        long targetGroup;
        long sourceGroupID;
        short sourceGroupSize;
        short sourceId;
        short sourceNeighborsListSize = 0;
        long[] sourceNeighborsListGroupID;
        short[] sourceNeighborsListGroupSize;

        public MergeProposalData(byte[] payload) {
            try {
                ByteArrayDataInputStream badis = new ByteArrayDataInputStream(payload);
                type = badis.readByte();
                sourceId = badis.readShort();
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
    class HELLOMsgData {

        byte type;
        short sourceID;
        byte[] signature;

        public HELLOMsgData(byte[] payload) {
            try {
                ByteArrayDataInputStream badis = new ByteArrayDataInputStream(payload);
                type = badis.readByte();
//                groupId = new byte[CryptoFunctions.MAC_SIZE];
                sourceID = badis.readShort();
                signature = new byte[CryptoFunctions.MAC_SIZE];
                badis.read(signature);
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class BroadcastMergeProposalRefuseData {

        byte type;
        long sourceGroupID;
        long refuseGroupID;
        short sourceId;

        public BroadcastMergeProposalRefuseData(byte[] payload) {
            try {
                ByteArrayDataInputStream badis = new ByteArrayDataInputStream(payload);
                type = badis.readByte();
                sourceId = badis.readShort();
                sourceGroupID = badis.readLong();
                refuseGroupID = badis.readShort();
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
