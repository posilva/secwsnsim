/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.cleanslate.messages;

/**
 *
 * @author CIAdmin
 */
public class APPMsg extends CleanSlateMsg{
    public static int ACTION_START_PROTOCOL=0;

    int action ;

    public APPMsg(byte[] payload) {
        super(payload);
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

}
