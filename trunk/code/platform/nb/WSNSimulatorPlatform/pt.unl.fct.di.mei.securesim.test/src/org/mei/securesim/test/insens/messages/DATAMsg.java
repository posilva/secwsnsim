/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.messages;

/**
 *
 * @author posilva
 */
public class DATAMsg extends INSENSMsg{

    public DATAMsg(byte[] payload) {
        super(payload);
    }

    @Override
    public byte[] toByteArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected int getDataSize() {
        return 0;
    }

}
