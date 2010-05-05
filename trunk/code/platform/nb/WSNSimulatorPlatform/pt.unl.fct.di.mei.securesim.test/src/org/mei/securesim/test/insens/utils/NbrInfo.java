/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.utils;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.test.insens.messages.FDBKMsg;

/**
 *
 * @author POSilva
 */
    public class NbrInfo implements Cloneable {

        public int size;
        public Vector macs;

        public byte[] toByteArray() {
            try {
                ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();

                bados.writeInt(size);
                for (Object m : macs) {
                    bados.writeInt(((MACS) m).id);
                    bados.write(((MACS) m).MACR);
                }

                return bados.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(FDBKMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            NbrInfo n = (NbrInfo) super.clone();
            n.macs = new Vector();
            for (int i = 0; i < macs.size(); ++i) {
                n.macs.addElement(((MACS) macs.elementAt(i)).clone());
            }
            return n;
        }

        @Override
        public String toString() {

            String out = "(";
            for (Object object : macs) {
                MACS m = (MACS) object;
                out += "" + m.id + ",";
            }
            out += ")";
            return out;
        }
    }