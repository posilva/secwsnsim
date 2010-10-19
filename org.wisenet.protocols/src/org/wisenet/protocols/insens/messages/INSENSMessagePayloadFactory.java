/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.messages;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.protocols.common.ByteArrayDataOutputStream;
import org.wisenet.protocols.insens.INSENSConstants;
import org.wisenet.protocols.insens.INSENSFunctions;
import org.wisenet.protocols.insens.basestation.ForwardingTable;
import org.wisenet.protocols.insens.utils.NeighborInfo;
import org.wisenet.simulator.core.node.Node;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class INSENSMessagePayloadFactory {

    /**
     * Creates a Route Update Message Payload
     * @param src Source node id
     * @param dst Destination node id
     * @param imt Immediate sender node id
     * @param ows current round OWS sequence number
     * @param table forwarding table
     * @param key Private key 
     * @param node
     * @return
     */
    public static byte[] createRUPDPayload(short src, short dst, short imt, long ows, ForwardingTable table, byte[] key, Node node) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(INSENSConstants.MSG_ROUTE_UPDATE);
            bados.writeShort(src);
            bados.writeShort(dst);
            bados.writeShort(imt);  // Este tem de mudar de lugar tem q ir cifrado mas se for alterado tem um problema de ataque
            bados.writeLong(ows);
            table.write(bados);
            byte[] mac = INSENSFunctions.createMAC(bados.toByteArray(), key, node);
            bados.write(mac);
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(INSENSMessagePayloadFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Creates a Route Request Message payload 
     * @param id source node id
     * @param ows message ows
     * @param parent_mac can be null if not parent mac needded
     * @param key private key to create mac
     * @param node
     * @return
     */
    public static byte[] createREQPayload(short id, long ows, byte[] key, byte[] parent_mac, Node node) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(INSENSConstants.MSG_ROUTE_REQUEST);
            bados.writeShort(id);
            bados.writeLong(ows);
            byte[] data = null;
            if (parent_mac == null) { // no parent mac, so is a first time message
                data = bados.toByteArray();
            } else { // append parent mac to mac, nested mac
                data = new byte[bados.toByteArray().length + parent_mac.length];
                System.arraycopy(bados.toByteArray(), 0, data, 0, bados.toByteArray().length);
                System.arraycopy(parent_mac, 0, data, bados.toByteArray().length, parent_mac.length);
            }
            byte[] mac = INSENSFunctions.createMAC(data, key, node);
            bados.write(mac);
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(INSENSMessagePayloadFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Creates a Feedback Message Payload
     * @param id Feedback node id
     * @param key Private key of the node
     * @param neighborInfo Neighbor info data
     * @param parent_mac
     * @param node
     * @return
     */
    public static byte[] createFBKPayload(short id, byte[] key, NeighborInfo neighborInfo, byte[] parent_mac, Node node) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(INSENSConstants.MSG_FEEDBACK);
            bados.writeShort(id);
            //TODO: Encrypt neighbor info
            byte[] neighborInfoData = neighborInfo.toByteArray();
            byte[] cipherNeighborInfoData = INSENSFunctions.encryptData(node, neighborInfoData, key);
            bados.writeInt(cipherNeighborInfoData.length); // size of neighbor info data
            bados.write(cipherNeighborInfoData);
            byte[] data = bados.toByteArray();
            byte[] mac = INSENSFunctions.createMAC(data, key, node);
            bados.write(parent_mac);
            bados.write(mac);
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(INSENSMessagePayloadFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param src
     * @param dst
     * @param imt
     * @param payload
     * @param key
     * @param node
     * @return
     */
    public static byte[] createDATAPayload(short src, short dst, short imt, byte[] payload, byte[] key, Node node) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(INSENSConstants.MSG_DATA);
            bados.writeShort(src);
            bados.writeShort(dst);
            bados.writeShort(imt);
            byte[] cipherPayload = INSENSFunctions.encryptData(node, payload, key);
            bados.writeInt(cipherPayload.length); // size of neighbor info data
            bados.write(cipherPayload);
            bados.write(payload);
            byte[] mac = INSENSFunctions.createMAC(bados.toByteArray(), key, node);

            bados.write(mac);
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(INSENSMessagePayloadFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param src
     * @param dst
     * @param imt
     * @param ows
     * @param table
     * @param mac
     * @param node
     * @return
     */
    public static byte[] updateRUPDPayload(short src, short dst, short imt, long ows, ForwardingTable table, byte[] mac, Node node) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(INSENSConstants.MSG_ROUTE_UPDATE);
            bados.writeShort(src);
            bados.writeShort(dst);
            bados.writeShort(imt);  // Este tem de mudar de lugar tem q ir cifrado mas se for alterado tem um problema de ataque
            bados.writeLong(ows);
            table.write(bados);
            bados.write(mac);
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(INSENSMessagePayloadFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     *
     * @param src
     * @param dst
     * @param imt
     * @param payload
     * @param mac
     * @param node
     * @return
     */
    public static byte[] updateDATAPayload(short src, short dst, short imt, byte[] payload, byte[] mac, Node node) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(INSENSConstants.MSG_DATA);
            bados.writeShort(src);
            bados.writeShort(dst);
            bados.writeShort(imt);
            bados.writeInt(payload.length);
            bados.write(payload);
            bados.write(mac);
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(INSENSMessagePayloadFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
