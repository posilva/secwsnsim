/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.messages;

import org.mei.securesim.test.insens.utils.INSENSConstants;

/**
 *
 * @author posilva
 */
public class APPMsg extends INSENSMsg{
    protected int action ;

    public void setAction(int action) {
        this.action = action;
    }
    public APPMsg(byte[] payload) {
        super(payload);
        setType(INSENSConstants.MSG_APP);
    }

    public int getAction() {
        return action;
    }

    @Override
    public byte[] toByteArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
