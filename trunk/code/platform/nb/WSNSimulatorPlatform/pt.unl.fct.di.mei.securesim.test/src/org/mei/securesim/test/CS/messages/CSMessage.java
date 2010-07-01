/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.CS.messages;

import org.mei.securesim.core.engine.BaseMessage;

/**
 *
 * @author Pedro Marques da Silva
 */
public class CSMessage extends BaseMessage {

    /**
     * 
     */
    public CSMessage() {
        super();
    }

    /**
     * 
     * @param payload
     */
    public CSMessage(byte[] payload) {
        super(payload);
    }
}
