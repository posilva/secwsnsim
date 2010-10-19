/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.basestation;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.protocols.common.ByteArrayDataInputStream;
import org.wisenet.protocols.common.ByteArrayDataOutputStream;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ForwardingTable {

    short nodeId;
    Set<RoutingTableEntry> entries = new HashSet<RoutingTableEntry>();

    /**
     *
     * @param nodeId
     */
    public ForwardingTable(short nodeId) {
        this.nodeId = nodeId;
    }

    /**
     *
     */
    public ForwardingTable() {
    }

    /**
     *
     * @return
     */
    public short getNodeId() {
        return nodeId;
    }

    /**
     *
     * @param nodeId
     */
    public void setNodeId(short nodeId) {
        this.nodeId = nodeId;
    }

    /**
     *
     * @return
     */
    public Set<RoutingTableEntry> getEntries() {
        return entries;
    }

    /**
     *
     * @param badis
     */
    public void read(ByteArrayDataInputStream badis) {
        try {
            int nrEntries = badis.readInt();
            for (int i = 0; i < nrEntries; i++) {
                RoutingTableEntry entry = new RoutingTableEntry();
                entry.read(badis);
                entries.add(entry);
            }
        } catch (Exception ex) {
            Logger.getLogger(ForwardingTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param bados
     */
    public void write(ByteArrayDataOutputStream bados) {
        try {
            bados.writeInt(entries.size());
            for (RoutingTableEntry routingTableEntry : entries) {
                routingTableEntry.write(bados);
            }
        } catch (Exception ex) {
            Logger.getLogger(ForwardingTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param source
     * @param destination
     * @return
     */
    public short getImmediate(short source, short destination) {
        for (RoutingTableEntry routingTableEntry : entries) {
            if (routingTableEntry.getSource() == source && routingTableEntry.getDestination() == destination) {
                return routingTableEntry.getImmediate();
            }
        }
        return -1;
    }

    /**
     *
     * @param entry
     */
    public void add(RoutingTableEntry entry) {
        entries.add(entry);
    }

    /**
     *
     * @param source
     * @param destination
     * @param immediate
     */
    public void add(short source, short destination, short immediate) {
        entries.add(new RoutingTableEntry(source, destination, immediate));
    }

    /**
     *
     * @param entry
     */
    public void remove(RoutingTableEntry entry) {
        entries.remove(entry);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String out = "";
        out += "ForwardingTable: " + nodeId + "\n";
        out += "-----------------------------\n";
        for (RoutingTableEntry routingTableEntry : entries) {
            out += routingTableEntry + "\n";
        }
        out += "-----------------------------\n";
        return out;
    }

    /**
     *
     * @param forwardingTable
     */
    public void addAll(ForwardingTable forwardingTable) {
        entries.addAll(forwardingTable.getEntries());
    }
    /**
     * Evaluate if a route exists in this forwing table
     * @param destination
     * @param source
     * @param immediate
     * @return
     */
    public boolean haveRoute(short destination, short source, short immediate) {
        for (RoutingTableEntry routingTableEntry : entries) {
            if (routingTableEntry.getSource() == source && routingTableEntry.getDestination() == destination && routingTableEntry.getImmediate() == immediate) {
                return true;
            }
        }
        return false;
    }
}
