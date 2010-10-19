/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.basestation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.protocols.common.ByteArrayDataInputStream;
import org.wisenet.protocols.common.ByteArrayDataOutputStream;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class RoutingTableEntry {

    short source;
    short destination;
    short immediate;

    /**
     *
     */
    public RoutingTableEntry() {
    }

    /**
     *
     * @param source
     * @param destination
     * @param immediate
     */
    public RoutingTableEntry(short source, short destination, short immediate) {
        this.source = source;
        this.destination = destination;
        this.immediate = immediate;
    }

    /**
     *
     * @return
     */
    public short getDestination() {
        return destination;
    }

    /**
     *
     * @return
     */
    public short getImmediate() {
        return immediate;
    }

    /**
     *
     * @return
     */
    public short getSource() {
        return source;
    }

    /**
     *
     * @param badis
     */
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

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RoutingTableEntry){
            RoutingTableEntry o = (RoutingTableEntry) obj;
            return o.source==source && o.destination==destination && o.immediate==immediate;
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.source;
        hash = 97 * hash + this.destination;
        hash = 97 * hash + this.immediate;
        return hash;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return destination+ " , " + source + " , " + immediate;
    }


}
