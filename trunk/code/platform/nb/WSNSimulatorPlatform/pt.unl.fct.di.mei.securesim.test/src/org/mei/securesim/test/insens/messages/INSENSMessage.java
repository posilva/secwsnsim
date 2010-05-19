/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.messages;

/**
 *
 * @author CIAdmin
 */
public class INSENSMessage extends INSENSMsg{

    public INSENSMessage(byte[] payload) {
        super(payload);
    }

    @Override
    public byte[] toByteArray() {
        return getPayload();
    }

    @Override
    protected int getDataSize() {
        if (getPayload()==null) return 0;
        return getPayload().length;
    }

}
