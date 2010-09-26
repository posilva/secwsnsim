package org.wisenet.protocols.insens.messages;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class INSENSDATAMessage extends INSENSMessage {
    short destination;
    short source;
    private static long globalUniqueId=0;

    long ID= globalUniqueId++;
    
    public short getDestination() {
        return destination;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setDestination(short destination) {
        this.destination = destination;
    }

    public short getSource() {
        return source;
    }

    public void setSource(short source) {
        this.source = source;
    }
    
    public INSENSDATAMessage(byte[] payload) {
        super(payload);
    }

    public INSENSDATAMessage() {
        super();
    }
}
