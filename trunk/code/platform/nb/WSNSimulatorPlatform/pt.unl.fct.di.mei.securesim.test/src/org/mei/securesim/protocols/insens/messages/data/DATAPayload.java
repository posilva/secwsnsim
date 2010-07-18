/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.protocols.insens.INSENSException;
import org.mei.securesim.protocols.insens.INSENSFunctions;

/**
 *
 * @author Pedro Marques da Silva
 */
public class DATAPayload extends INSENSMessagePayload {

    public short source;
    public short destination;
    public short immediate;
    public int size;
    public byte[] data;
    public byte[] mac;

    public DATAPayload(byte[] payload) throws INSENSException {
        super(payload);

        try {
            source = badis.readShort();
            destination = badis.readShort();
            immediate = badis.readShort();
            size = badis.readInt();
            data = new byte[size];
            badis.read(data);
            mac = new byte[CryptoFunctions.MAC_SIZE];
            badis.read(mac);
        } catch (IOException ex) {
            Logger.getLogger(DATAPayload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
