/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate.messages.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.test.common.ByteArrayDataInputStream;

/**
 *
 * @author Pedro Marques da Silva
 */
public class BaseMessageData {
    public byte[] payloadCopy;
    public ByteArrayDataInputStream badis;
    public byte type;
    public short sourceId;
    public long sourceGroupId;

    public BaseMessageData(byte[] payload) {
        try {
            payloadCopy = Arrays.copyOf(payload, payload.length);
            badis = new ByteArrayDataInputStream(payload);
            type = badis.readByte();
            sourceId = badis.readShort();
            sourceGroupId = badis.readLong();
        } catch (IOException ex) {
            Logger.getLogger(BaseMessageData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
