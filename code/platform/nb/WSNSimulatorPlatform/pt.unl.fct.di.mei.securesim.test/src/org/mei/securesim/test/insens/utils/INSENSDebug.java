/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.utils;

import org.mei.securesim.test.insens.messages.FDBKMsg;
import org.mei.securesim.test.insens.messages.RREQMsg;

/**
 *
 * @author POSilva
 */
public class INSENSDebug {
    public static void debugRREQMessages(String source, Object msg){
        RREQMsg m = (RREQMsg) msg;
        System.out.println("DBG:\t" +"Source: " + source + ", Type: " + m.getClass().getSimpleName() + " Nr: " +m.getMessageNumber() + "Detail: "+m.toString());

    }
    public static void debugFDBKMessages(String source, Object msg){
        FDBKMsg m = (FDBKMsg) msg;
        System.out.println("DBG:\t" +"Source: " + source + ", Type: " + m.getClass().getSimpleName() + " Nr: " +m.getMessageNumber() + "Detail: "+m.toString());

    }

}
