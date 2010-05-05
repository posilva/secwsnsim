/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.utils;

import java.util.Arrays;

/**
 *
 * @author POSilva
 */
public class MACS implements Cloneable {

        public int id;
        public byte[] MACR;

        public MACS(int id, byte[] MACR) {
            this.id = id;
            this.MACR = MACR;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            MACS m = (MACS) super.clone();
            m.MACR = Arrays.copyOf(MACR, MACR.length);
            return m;

        }
    }