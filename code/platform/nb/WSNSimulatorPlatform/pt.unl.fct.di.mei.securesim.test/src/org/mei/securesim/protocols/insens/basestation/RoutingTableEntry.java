/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.basestation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.protocols.common.ByteArrayDataInputStream;
import org.mei.securesim.protocols.common.ByteArrayDataOutputStream;

/**
 *
 * @author CIAdmin
 */
public class RoutingTableEntry {

    short source;
    short destination;
    short immediate;

    public RoutingTableEntry() {
    }

    public RoutingTableEntry(short source, short destination, short immediate) {
        this.source = source;
        this.destination = destination;
        this.immediate = immediate;
    }

    public short getDestination() {
        return destination;
    }

    public short getImmediate() {
        return immediate;
    }

    public short getSource() {
        return source;
    }

    public void read(ByteArrayDataInputStream badis) {
        try {
            
            source = badis.readShort();
            destination = badis.readShort();
            immediate = badis.readShort();
        } catch (IOException ex) {
            Logger.getLogger(RoutingTableEntry.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void write(ByteArrayDataOutputStream bados) {
        try {
            
            bados.writeShort(source);
            bados.writeShort(destination);
            bados.writeShort(immediate);
        } catch (IOException ex) {
            Logger.getLogger(RoutingTableEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RoutingTableEntry){
            RoutingTableEntry o = (RoutingTableEntry) obj;
            return o.source==source && o.destination==destination && o.immediate==immediate;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.source;
        hash = 97 * hash + this.destination;
        hash = 97 * hash + this.immediate;
        return hash;
    }

    @Override
    public String toString() {
        return destination+ " , " + source + " , " + immediate;
    }


}
