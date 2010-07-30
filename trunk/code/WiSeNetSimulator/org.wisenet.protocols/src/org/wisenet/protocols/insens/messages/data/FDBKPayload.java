/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.protocols.insens.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.protocols.insens.INSENSException;
import org.wisenet.simulator.components.crypto.CryptoFunctions;

/**
 *
 * @author Pedro Marques da Silva
 */
public class FDBKPayload extends INSENSMessagePayload {

    public short sourceId;
    public byte[] parent_mac;
    public byte[] neighborInfo;
    public int neighborInfoSize;
    public byte[] mac;

    public FDBKPayload(byte[] payload) throws INSENSException {
        super(payload);
        try {
            sourceId = badis.readShort();
            neighborInfoSize = badis.readInt();
            neighborInfo = new byte[neighborInfoSize];
            badis.read(neighborInfo);
            parent_mac = new byte[CryptoFunctions.MAC_SIZE];
            badis.read(parent_mac);
            mac = new byte[CryptoFunctions.MAC_SIZE];
            badis.read(mac);
        } catch (IOException ex) {
            Logger.getLogger(FDBKPayload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
