/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ByteArrayDataOutputStream extends DataOutputStream {

    public ByteArrayDataOutputStream(OutputStream out) {
        super(out);
        if (!(out instanceof ByteArrayOutputStream)) {
            throw new IllegalStateException("output stream must be a ByteArrayOutputStream instance");
        }
    }

    public ByteArrayDataOutputStream() {
        super(new ByteArrayOutputStream());
    }

    public byte[] toByteArray() {
        if (!(out instanceof ByteArrayOutputStream)) {
            throw new IllegalStateException("output stream must be a ByteArrayOutputStream instance");
        }
        //        out.flush();
        return ((ByteArrayOutputStream) out).toByteArray();

    }
}
