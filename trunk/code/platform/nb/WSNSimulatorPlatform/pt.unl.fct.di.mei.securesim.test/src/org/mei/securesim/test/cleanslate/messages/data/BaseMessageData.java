/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.protocols.common.ByteArrayDataInputStream;

/**
 * This is the base message information that every cleans slate message needs
 * @author Pedro Marques da Silva
 */
public class BaseMessageData {

    public ByteArrayDataInputStream badis;
    public byte type;
    public short sourceId;
    public long sourceGroupId;
    public short forwardId;
    public long forwardGroupId;
    public int sequenceNumber = 0;

    public BaseMessageData(byte[] payload) {
        try {
            badis = new ByteArrayDataInputStream(payload);
            type = badis.readByte();
            sourceId = badis.readShort();
            sourceGroupId = badis.readLong();
            forwardId = badis.readShort();
            forwardGroupId = badis.readLong();
            sequenceNumber = badis.readInt();
        } catch (IOException ex) {
            Logger.getLogger(BaseMessageData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
