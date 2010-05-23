package org.mei.securesim.test.cleanslate;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.test.cleanslate.messages.APPMsg;
import java.util.HashSet;
import java.util.Set;
import org.mei.securesim.test.cleanslate.messages.HELLOMsg;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.cleanslate.messages.CleanSlateMsg;
import org.mei.securesim.test.common.ByteArrayDataInputStream;
import org.mei.securesim.test.common.ByteArrayDataOutputStream;

/**
 *
 * @author CIAdmin
 */
public class CleanSlateRoutingLayer extends RoutingLayer {

    private String address;
    private String group;
    private Set neighborhood;
    private ProtocolPhases state;

    enum ProtocolPhases {

        NEIGHBOR_DISCOVERY
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
            startSecureNetworkDiscovery();
        }

        return true;
    }

    private void handleMessage(CleanSlateMsg msg) {
        if (msg instanceof HELLOMsg) {
            handleHELLOMessageReceive((HELLOMsg) msg);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Set getNeighborhood() {
        if (neighborhood == null) {
            neighborhood = new HashSet();
        }
        return neighborhood;
    }

    public void setNeighborhood(Set neighborhood) {
        this.neighborhood = neighborhood;
    }

    private void handleHELLOMessageReceive(HELLOMsg msg) {
        if (verifySignature(msg)) {
            addToNeigborhoodList(msg);
        }
    }

    private void addToNeigborhoodList(HELLOMsg msg) {
        HELLOMsgData hello = new HELLOMsgData(msg.getPayload());
        getNeighborhood().add(hello.id);
    }

    private boolean verifySignature(HELLOMsg msg) {
        return true;
    }

    private void startSecureNetworkDiscovery() {
        broadcastHELLO();
        scheduleSecureNetworkDiscoveryTimeBound();
    }

    private boolean isNetworkDiscoveryPhase() {
        return state == ProtocolPhases.NEIGHBOR_DISCOVERY;
    }

    private void broadcastHELLO() {
        HELLOMsg m = new HELLOMsg(createPayloadHelloMessage());


        getNode().getMacLayer().sendMessage(m, this);
    }

    /**
     * Schedules a event to bound the discovery time period
     */
    private void scheduleSecureNetworkDiscoveryTimeBound() {
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
            bados.write(signData(bados.toByteArray()));
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Creates a MAC from data
     * @param data input data to sign
     * @return a MAC
     */
    private byte[] signData(byte[] data) {

        return null;
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

    /**
     * Extract a signature data from a message payload
     * the signature must be at the end
     * @param payload
     * @return
     */
    private byte[] extractSignatureFromPayload(byte[] payload) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
