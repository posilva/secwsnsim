/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.test.insens.messages.FDBKMsg;

/**
 *
 * @author POSilva
 */
public class PathInfo implements Cloneable {

        public int id;
        public int size;
        public Vector pathToMe;
        public byte[] MACRx;

        public byte[] toByteArray() {
            try {
                ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
                bados.writeInt(id);
                bados.writeInt(size);
                for (Object n : pathToMe) {
                    bados.writeInt((Integer) n);
                }
                bados.write(MACRx);
                return bados.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(FDBKMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            PathInfo p = (PathInfo) super.clone();
            p.pathToMe = new Vector();
            for (int i = 0; i < pathToMe.size(); ++i) {
                p.pathToMe.addElement(((Integer) pathToMe.elementAt(i)).intValue());
            }
            p.MACRx = Arrays.copyOf(MACRx, MACRx.length);
            return p;
        }

        @Override
        public String toString() {

            String out = "[" + id + "] (";
            for (Object integer : pathToMe) {
                out += ((Integer) integer).intValue();
                out += ",";
            }
            out += ")";
            return out;
        }
    }


