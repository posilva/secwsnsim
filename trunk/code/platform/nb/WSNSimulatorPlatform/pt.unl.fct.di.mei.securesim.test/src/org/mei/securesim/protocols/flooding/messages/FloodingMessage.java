/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.flooding.messages;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.core.engine.Message;
import org.mei.securesim.protocols.common.ByteArrayDataInputStream;
import org.mei.securesim.protocols.common.ByteArrayDataOutputStream;

/**
 *
 * @author CIAdmin
 */
public class FloodingMessage extends Message implements Cloneable {

    private byte type;
    private short source;
    private short destin;
    private String data;

    public FloodingMessage() {
        super(null);
    }

    /**
     * 
     * @param payload
     */
    public FloodingMessage(byte[] payload) {
        super(payload);
    }

    /**
     * 
     */
    void unmarshalling() {
        try {
            ByteArrayDataInputStream badis = new ByteArrayDataInputStream(getPayload());
            type = badis.readByte();
            source = badis.readShort();
            destin = badis.readShort();
            data = badis.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(FloodingMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMessageData() {
        return data;
    }

    public void setMessageData(String data) {
        this.data = data;
    }

    public short getDestin() {
        return destin;
    }

    public void setDestin(short destin) {
        this.destin = destin;
    }

    public short getSource() {
        return source;
    }

    public void setSource(short source) {
        this.source = source;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    void marshalling() {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(type);
            bados.writeShort(source);
            bados.writeShort(destin);
            bados.writeUTF(data);
            setPayload(bados.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(FloodingMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
