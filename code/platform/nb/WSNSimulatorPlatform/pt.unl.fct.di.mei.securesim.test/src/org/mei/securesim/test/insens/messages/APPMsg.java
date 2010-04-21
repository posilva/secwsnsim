/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.messages;

import org.mei.securesim.test.insens.utils.INSENSConstants;
import org.mei.securesim.test.insens.messages.INSENSMsg;

/**
 *
 * @author posilva
 */
public class APPMsg extends INSENSMsg{
    protected int action ;
    public APPMsg(byte[] payload) {
        super(payload);
        setType(INSENSConstants.MSG_APP);
    }

    public int getAction() {
        return action;
    }

}
