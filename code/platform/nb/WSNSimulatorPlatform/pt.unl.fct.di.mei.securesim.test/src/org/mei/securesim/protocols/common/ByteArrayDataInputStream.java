/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.protocols.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;

/**
 *
 * @author CIAdmin
 */
public class ByteArrayDataInputStream extends DataInputStream{

    public ByteArrayDataInputStream(InputStream in) {
        super(in);
        if(!(in instanceof ByteArrayInputStream)){
            throw  new IllegalStateException("output stream must be a ByteArrayInputStream instance");
        }
    }
    
    public ByteArrayDataInputStream(byte [] data) {
        super (new ByteArrayInputStream(data));
    }
    
}
