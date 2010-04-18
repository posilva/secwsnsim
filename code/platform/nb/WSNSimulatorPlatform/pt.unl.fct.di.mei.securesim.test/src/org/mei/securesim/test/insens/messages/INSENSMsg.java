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
public abstract class INSENSMsg extends DefaultMessage {

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

}
