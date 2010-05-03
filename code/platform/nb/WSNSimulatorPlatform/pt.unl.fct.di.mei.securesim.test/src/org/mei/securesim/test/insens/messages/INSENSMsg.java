/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens.messages;

import org.mei.securesim.core.engine.DefaultMessage;

/**
 *
 * @author posilva
 */
public abstract class INSENSMsg extends DefaultMessage implements Cloneable{

    protected int type ;
    protected long OWS;
    protected byte[] MAC;

    public INSENSMsg(byte[] payload) {
        super(payload);
    }

    public long getOWS() {
        return OWS;
    }

    public void setOWS(long OWS) {
        this.OWS = OWS;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getMAC() {
        return MAC;
    }

    public void setMAC(byte[] MAC) {
        this.MAC = MAC;
    }
    /**
     * This function is for retrieve the byte array with data
     * that must be used for MAC 
     * @return
     */
    abstract public byte[] toByteArray();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int size() {
        return getDataSize();
    }

    abstract protected int getDataSize();


}
