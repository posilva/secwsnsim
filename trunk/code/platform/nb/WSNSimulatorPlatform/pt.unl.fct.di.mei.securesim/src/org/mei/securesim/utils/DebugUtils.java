/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.utils;

import org.mei.securesim.core.engine.BaseMessage;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author POSilva
 */
public class DebugUtils {
        static boolean  DEBUG=false;
        /**
         * This method allow to have some debug information related with 
         * protocols messages
         * @param source
         * @param msg
         */
        public static void debugMessage(String source, Object node) {
            if (!DEBUG) return;
            BaseMessage m = (BaseMessage) ((Node)node).getMessage();
            System.out.println("Source: "+ source + "  "+"Type: "+m.getClass().getSimpleName()  +" Nro.: "+ m.getMessageNumber());
        }

}
