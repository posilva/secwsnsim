/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */

package org.wisenet.protocols.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ByteArrayDataInputStream extends DataInputStream{

    /**
     *
     * @param in
     */
    public ByteArrayDataInputStream(InputStream in) {
        super(in);
        if(!(in instanceof ByteArrayInputStream)){
            throw  new IllegalStateException("output stream must be a ByteArrayInputStream instance");
        }
    }
    
    /**
     *
     * @param data
     */
    public ByteArrayDataInputStream(byte [] data) {
        super (new ByteArrayInputStream(data));
    }
    
}
