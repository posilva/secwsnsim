/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.engine;

/**
 *
 * @author posilva
 */
public class DefaultMessage {

    private byte[] payload;


    public DefaultMessage(byte[] payload) {
        this.payload = payload;

    }

    public byte[] getPayload() {
        return payload;
    }
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public int size() {
        if(this.payload==null) return 0;
        return this.payload.length;
    }

}
