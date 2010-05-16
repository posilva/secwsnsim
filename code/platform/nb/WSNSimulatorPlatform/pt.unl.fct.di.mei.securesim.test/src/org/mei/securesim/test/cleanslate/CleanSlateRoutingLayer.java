/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate;

import org.mei.securesim.test.cleanslate.messages.APPMsg;
import java.util.HashSet;
import java.util.Set;
import org.mei.securesim.test.cleanslate.messages.HELLOMsg;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.cleanslate.messages.CleanSlateMsg;

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
        getNeighborhood().add(msg.getNodeId());
    }

    private boolean verifySignature(HELLOMsg msg) {
        return true;
    }

    private void timeBoundSecureNetworkDiscovery() {
        
    }

    private void startSecureNetworkDiscovery() {
        broadcastHELLO();
    }

    private boolean isNetworkDiscoveryPhase() {
        return state == ProtocolPhases.NEIGHBOR_DISCOVERY;
    }

    private void broadcastHELLO() {
        HELLOMsg m = new HELLOMsg(null);
        m.setNodeId(""+this.getNode().getId());

        getNode().getMacLayer().sendMessage(m, this);
    }
}
