/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.protocols.common.ByteArrayDataInputStream;
import org.mei.securesim.protocols.common.ByteArrayDataOutputStream;

/**
 *  Neighbor info storage class
 * @author CIAdmin
 */
public class NeighborInfo extends Hashtable<Short, byte[]> {

    private short parentId;
    private byte[] parentMac;

    /**
     * Adds a neighbor to the set of neighbors
     * @param id
     * @param mac
     */
    public void addNeighbor(short id, byte[] mac, boolean parent) {

        if (containsKey(id)) {
            return;
        }
        byte[] new_mac = Arrays.copyOf(mac, mac.length);
        if (parent) {
            markAsParent(id, new_mac);
        }
        put(id, new_mac);
    }

    /**
     * Gets the neighbor info into byte array for message payload
     * @return
     */
    public byte[] toByteArray() {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            for (Short s : keySet()) {
                bados.writeShort(s);
                bados.write(get(s));
            }
            return bados.toByteArray();

        } catch (IOException ex) {
            Logger.getLogger(NeighborInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Buils a this object from a byte array
     * @param nbrInfo
     */
    public void fromByteArray(byte[] nbrInfo) {
        try {
            reset();

            ByteArrayDataInputStream badis = new ByteArrayDataInputStream(nbrInfo);
            while (badis.available() > 0) {
                short key = badis.readShort();
                byte[] buffer = new byte[CryptoFunctions.MAC_SIZE];
                badis.read(buffer);
                put(key, buffer);
            }

        } catch (IOException ex) {
            Logger.getLogger(NeighborInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Mark a node as a parent and save is info
     * @param id
     * @param mac
     */
    private void markAsParent(short id, byte[] mac) {
        parentId = id;
        parentMac = Arrays.copyOf(mac, mac.length);
    }

    public short getParentId() {
        return parentId;
    }

    public byte[] getParentMac() {
        return parentMac;
    }

    /**
     * Resets all the neighbors information
     */
    public void reset() {
        clear();
        parentId = -1;
        parentMac = null;
    }
}
