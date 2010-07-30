/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wisenet.simulator.utils;

import org.wisenet.simulator.core.engine.Message;
import org.wisenet.simulator.core.nodes.Node;

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
            Message m = (Message) ((Node)node).getMessage();
            System.out.println("Source: "+ source + "  "+"Type: "+m.getClass().getSimpleName()  +" Nro.: "+ m.getMessageNumber());
        }

}
