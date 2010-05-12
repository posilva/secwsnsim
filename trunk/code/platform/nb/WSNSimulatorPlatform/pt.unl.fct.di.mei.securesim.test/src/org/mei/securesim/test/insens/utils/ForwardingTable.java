/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.utils;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CIAdmin
 */
public class ForwardingTable  extends Vector<ForwardingTableEntry> {
    @Override
    public synchronized Object clone() {
        ForwardingTable t =  (ForwardingTable) super.clone();


        for (int i = 0; i < elementData.length; i++) {
            try {
                t.add((ForwardingTableEntry) ((ForwardingTableEntry)elementData[i]).clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ForwardingTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return t;
    }
}

