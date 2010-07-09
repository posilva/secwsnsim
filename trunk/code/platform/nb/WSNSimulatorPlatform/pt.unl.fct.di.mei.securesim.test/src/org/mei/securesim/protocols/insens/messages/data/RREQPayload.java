/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.protocols.insens.INSENSException;

/**
 *
 * @author Pedro Marques da Silva
 */
public class RREQPayload extends INSENSMessagePayload {

    public short sourceId;
    public long ows;
    public byte[] mac;

    public RREQPayload(byte[] payload) throws INSENSException {
        super(payload);
        try {
            sourceId = badis.readShort();
            ows = badis.readLong();
            mac = new byte[CryptoFunctions.MAC_SIZE];
            badis.read(mac);
        } catch (IOException ex) {
            Logger.getLogger(RREQPayload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
