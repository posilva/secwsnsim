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
    public long getID() {
        return ID;
    }

    /**
     *
     * @param ID
     */
    public void setID(long ID) {
        this.ID = ID;
    }

    /**
     *
     * @param destination
     */
    public void setDestination(short destination) {
        this.destination = destination;
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
     * @param source
     */
    public void setSource(short source) {
        this.source = source;
    }
    
    /**
     *
     * @param payload
     */
    public INSENSDATAMessage(byte[] payload) {
        super(payload);
    }

    /**
     *
     */
    public INSENSDATAMessage() {
        super();
    }
}
