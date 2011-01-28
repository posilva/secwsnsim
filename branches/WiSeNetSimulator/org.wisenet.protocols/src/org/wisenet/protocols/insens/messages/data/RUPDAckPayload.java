/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.protocols.insens.INSENSException;
import org.wisenet.simulator.utilities.CryptoFunctions;

/**
 *
 * @author Pedro Marques da Silva
 */
public class RUPDAckPayload extends INSENSMessagePayload {

    /**
     *
     */
    public short source;
    /**
     *
     */
    public short destination;
    /**
     *
     */
    public short immediate;
    /**
     *
     */
    public long ows;
    /**
     *
     */
    public byte[] mac;

    /**
     *
     * @param payload
     * @throws INSENSException
     */
    public RUPDAckPayload(byte[] payload) throws INSENSException {
        super(payload);

        try {
            source = badis.readShort();
            destination = badis.readShort();
            immediate = badis.readShort();
            ows = badis.readLong();
            mac = new byte[CryptoFunctions.MAC_SIZE];
            badis.read(mac);
        } catch (IOException ex) {
            Logger.getLogger(RUPDPayload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
