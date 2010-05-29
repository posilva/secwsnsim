package org.mei.securesim.test.cleanslate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.test.cleanslate.messages.APPMsg;
import java.util.Hashtable;
import java.util.Vector;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.test.cleanslate.messages.HELLOMsg;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.DefaultMessage;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.cleanslate.messages.CleanSlateMsg;
import org.mei.securesim.test.common.ByteArrayDataInputStream;
import org.mei.securesim.test.common.ByteArrayDataOutputStream;
import org.mei.securesim.test.common.events.DelayedMessageEvent;

/**
 *
 * @author CIAdmin
 */
public class CleanSlateRoutingLayer extends RoutingLayer {

    @Override
    public void autostart() {
    }

    public enum ProtocolPhases {

        NONE, NEIGHBOR_DISCOVERY, RECURSIVE_GROUPING
    }
    private String address;
    private short group = -1;
    private Hashtable<Short, Short> neighboringGroupsIdList = new Hashtable<Short, Short>();
    private ProtocolPhases state = ProtocolPhases.NONE;
    private boolean waitingForProposalResponse;
    private boolean interiorNode = true;
    private boolean edgeNode = true;
    private int groupSize = 1;
    private boolean startNetworkDiscovery = false;


    private void broadcastSTART() {
        if (state == ProtocolPhases.NONE) {
            APPMsg aPPMsg = new APPMsg(null);
            aPPMsg.setAction(CleanSlateConstants.ACTION_START);
            sendMessageToAir(aPPMsg);
            setState(ProtocolPhases.NEIGHBOR_DISCOVERY);
        }
    }

    /**
     * 
     * @return
     */
    private short computeGroupWithSmallestSize() {

        short min_size = Short.MAX_VALUE;
        Vector min_id = new Vector();
        for (Object key : neighboringGroupsIdList.keySet()) {
            Short id = (Short) key;
            Short size = neighboringGroupsIdList.get(id);
            if (size < min_size) {
                min_size = size;
                min_id.clear();
            }
            if (size == min_size) {
                min_id.add(key);
            }
        }
        if (min_id.size() > 1) {
            Collections.sort(min_id);
        }

       if (min_id.size()==0)
            System.out.println("oops");

        return (Short) min_id.get(0);
    }

    private void printNeighborHood() {
        System.out.print(getNode().getId() + "->[");


        for (Object object : neighboringGroupsIdList.keySet()) {
            System.out.print(" " + object + " ");
        }
        System.out.println("]");
    }

    @Override
    public void receiveMessage(Object message) {
        CleanSlateMsg msg = (CleanSlateMsg) message;
        handleMessage(msg);
    }

    @Override
    public void sendMessageDone() {
    }

    @Override
    public boolean sendMessage(Object message, Application app) {

        if (message instanceof APPMsg) {
            return handleAPPMessage((APPMsg) message);
        }
        return true;
    }

    private boolean handleAPPMessage(APPMsg message) {
        if (message.getAction() == APPMsg.ACTION_START_PROTOCOL) {
            broadcastSTART();
        }
        return true;
    }

    private void handleMessage(CleanSlateMsg msg) {
        if (msg instanceof APPMsg) {
            broadcastSTART();
            if (((APPMsg) msg).getAction() == APPMsg.ACTION_START_PROTOCOL) {
                if (!startNetworkDiscovery) {
                    startSecureNetworkDiscovery();
                }
            }
        } else {
            int messageType = getMessageTypeFromPayload(msg.getPayload());
            switch (messageType) {
                case CleanSlateConstants.MSG_HELLO:
                    if (isNetworkDiscoveryPhase()) {
                        handleHELLOMessageReceive(msg);
                        broadcastHELLO();
                    }
                    break;
                case CleanSlateConstants.MSG_GROUP_PROPOSAL:
                    if (isRecursiveGroupingPhase()) {
                        if (!isWaitingForProposalResponse()) {
                            handleGROUP_PROPOSALMessage(msg);
                        }
                    }
                    break;
                case CleanSlateConstants.MSG_KNOWNED_GROUP_INFO:
                    if (isRecursiveGroupingPhase()) {
                        handleKNOWNED_GROUP_INFOMessage(msg);
                    }
                    break;
            }
        }
    }

    /**
     * 
     * @param msg
     */
    private void handleKNOWNED_GROUP_INFOMessage(CleanSlateMsg msg) {
    }

    /**
     * 
     * @param msg
     */
    private void handleGROUP_PROPOSALMessage(CleanSlateMsg msg) {
        GProposalData gProposalData = new GProposalData(msg.getPayload());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public short getGroup() {
        if (group == -1) {
            group = getNode().getId();
        }
        return group;
    }

    /**
     *
     * @param group
     */
    public void setGroup(short group) {
        this.group = group;
    }

    /**
     * 
     * @param msg
     */
    private void handleHELLOMessageReceive(CleanSlateMsg msg) {
        if (verifySignature(msg)) {
            addToNeigborhoodList(msg);
        }
    }

    /**
     * 
     * @param msg
     */
    private void addToNeigborhoodList(CleanSlateMsg msg) {
        HELLOMsgData hello = new HELLOMsgData(msg.getPayload());
        neighboringGroupsIdList.put(hello.id, (short) 1);
        printNeighborHood();
    }

    /**
     *
     * @param msg
     * @return
     */
    private boolean verifySignature(CleanSlateMsg msg) {
        return true;
    }

    /**
     *
     */
    private void startSecureNetworkDiscovery() {
        broadcastHELLO();
        scheduleSecureNetworkDiscoveryTimeBound();
        startNetworkDiscovery = true;
    }

    /**
     *
     * @return
     */
    private boolean isNetworkDiscoveryPhase() {
        return state == ProtocolPhases.NEIGHBOR_DISCOVERY;
    }

    /**
     *
     * @return
     */
    private boolean isRecursiveGroupingPhase() {
        return state == ProtocolPhases.RECURSIVE_GROUPING;
    }

    /**
     *
     */
    private void broadcastHELLO() {
        HELLOMsg m = new HELLOMsg(createPayloadHelloMessage());
        getNode().getMacLayer().sendMessage(m, this);
    }

    /**
     * Schedules a event to bound the discovery time period
     */
    private void scheduleSecureNetworkDiscoveryTimeBound() {
        Event e = new TimeoutWaitEvent();
        e.setTime(getNode().getSimulator().getSimulationTime() + CleanSlateConstants.NEIGHBOR_DISCOVERY_BOUND_TIME);
        getNode().getSimulator().addEvent(e);
    }

    /**
     * Create a message payload 
     * @return
     */
    byte[] createPayloadHelloMessage() {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_HELLO);
            bados.writeShort(getNode().getId());
            bados.write(signDataWithNAPubKey(bados.toByteArray()));
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Creates a signature from data using NA Pub Key
     * @param data input data to sign
     * @return a signature
     */
    private byte[] signDataWithNAPubKey(byte[] data) {
        byte[] sign = CryptoFunctions.createMAC(data, getCSNode().getNAKey());
        return sign;
    }

    /**
     * This is a wrapper class to handle with message payload
     */
    class HELLOMsgData {

        byte type;
        short id;
        byte[] signature;

        public HELLOMsgData(byte[] payload) {
            try {
                ByteArrayDataInputStream badis = new ByteArrayDataInputStream(payload);
                type = badis.readByte();
                id = badis.readShort();
                signature = extractSignatureFromPayload(payload);
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    class GProposalData {

        byte type;

        public GProposalData(byte[] payload) {
            try {
                ByteArrayDataInputStream badis = new ByteArrayDataInputStream(payload);
                type = badis.readByte();
            } catch (IOException ex) {
                Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Extract a signature data from a message payload
     * the signature must be at the end
     * @param payload
     * @return
     */
    private byte[] extractSignatureFromPayload(byte[] payload) {
        return Arrays.copyOfRange(payload, payload.length - CryptoFunctions.MAC_SIZE - 1, payload.length - 1);
    }

    protected CleanSlateNode getCSNode() {
        return (CleanSlateNode) getNode();
    }

    private int getMessageTypeFromPayload(byte[] payload) {
        if (payload == null) {
            return CleanSlateConstants.INVALID_TYPE;
        }
        return payload[0];
    }

    public boolean isWaitingForProposalResponse() {
        return waitingForProposalResponse;
    }

    public void setWaitingForProposalResponse(boolean waitingForProposalResponse) {
        this.waitingForProposalResponse = waitingForProposalResponse;
    }

    public boolean isEdgeNode() {
        return edgeNode;
    }

    public void setEdgeNode(boolean edgeNode) {
        this.edgeNode = edgeNode;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public boolean isInteriorNode() {
        return interiorNode;
    }

    public void setInteriorNode(boolean interiorNode) {
        this.interiorNode = interiorNode;


    }

    /**
     * Sends a message to the air 
     * @param message
     */
    public void sendMessageToAir(final DefaultMessage message) {
        double result = Simulator.randomGenerator.random().nextDouble() * Simulator.ONE_SECOND * 1;


        long time = getNode().getSimulator().getSimulationTime() + (int) result;
        Event e = new DelayedMessageEvent(time, message, getNode());
        getNode().getSimulator().addEvent(e);


    }

    public ProtocolPhases getState() {
        return state;
    }

    public void setState(ProtocolPhases state) {
        this.state = state;
    }

    class TimeoutWaitEvent
            extends Event {

        private int action = CleanSlateConstants.EVENT_ACTION_BOUND_DISCOVERY;

        public TimeoutWaitEvent(long time, int action) {
            super(time);
            this.action = action;
        }

        private TimeoutWaitEvent() {
            super();
        }

        @Override
        public void execute() {
            if (action == CleanSlateConstants.EVENT_ACTION_BOUND_DISCOVERY) {
                setState(ProtocolPhases.RECURSIVE_GROUPING);
                startRecursiveGrouping();
            }
        }
    }

    /**
     *
     */
    private void startRecursiveGrouping() {
        System.out.println(getNodeID() + " - startRecursiveGrouping");
        if (groupSize == 1) {
            short gid = computeGroupWithSmallestSize();
            sendMergeProposalTo(gid);
        } else {
            sendNeighborGroupInfoToGroup();
        }
    }

    /**
     * 
     * @param gid
     */
    private void sendMergeProposalTo(short gid) {
        System.out.println(getNodeID() + " sendMergeProposalTo ->" + gid);
        byte[] payload = createMergeProposalPayload(gid);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
    }

    /**
     * 
     * @param gid
     * @return
     */
    private byte[] createMergeProposalPayload(short gid) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_GROUP_PROPOSAL);
            bados.writeShort(getNode().getId()); // source node ID
            bados.writeShort(getGroup()); // source group ID
            bados.writeShort(gid); // update group ID
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * 
     */
    private void sendNeighborGroupInfoToGroup() {
        System.out.println(getNodeID() + " - sendNeighborGroupInfoToGroup");
        for (Object key : neighboringGroupsIdList.keySet()) {
            short gid = (Short) key;
            short size = neighboringGroupsIdList.get(gid);
            sendUpdateGroupInfo(gid, size);
        }

    }

    /**
     *
     * @param gid
     * @param size
     */
    private void sendUpdateGroupInfo(short gid, short size) {
        byte[] payload = createUpdateGroupInfoMessagePayload(gid, size);
        CleanSlateMsg message = new CleanSlateMsg(payload);
        sendMessageToAir(message);
    }

    /**
     *
     * @param gid
     * @param size
     * @return
     */
    private byte[] createUpdateGroupInfoMessagePayload(short gid, short size) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_UPDATE_GROUPINFO);
            bados.writeShort(getNode().getId()); // source node ID
            bados.writeShort(getGroup()); // source group ID
            bados.writeShort(gid); // update group ID
            bados.writeShort(size); // update group size
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Util function to facilitate getting node id 
     * @return
     */
    private short getNodeID() {
        return getNode().getId();
    }
}
