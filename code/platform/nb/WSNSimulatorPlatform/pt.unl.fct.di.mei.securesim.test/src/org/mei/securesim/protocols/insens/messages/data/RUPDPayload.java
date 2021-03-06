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
import org.mei.securesim.protocols.insens.basestation.ForwardingTable;

/**
 *
 * @author Pedro Marques da Silva
 */
public class RUPDPayload extends INSENSMessagePayload {
    public short source;
    public short destination;
    public short immediate;
    public long ows;
    public ForwardingTable forwardingTable;

    public byte[] mac;
    
    public RUPDPayload(byte[] payload) throws INSENSException {
        super(payload);
        
        try {
            source = badis.readShort();
            destination = badis.readShort();
            immediate = badis.readShort();
            ows=badis.readLong();
            forwardingTable=new ForwardingTable();
            forwardingTable.read(badis);
            mac = new byte[CryptoFunctions.MAC_SIZE];
            badis.read(mac);
        } catch (IOException ex) {
            Logger.getLogger(RUPDPayload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
