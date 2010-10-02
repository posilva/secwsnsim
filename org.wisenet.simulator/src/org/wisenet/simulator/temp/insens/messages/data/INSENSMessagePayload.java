/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.temp.insens.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.simulator.temp.common.ByteArrayDataInputStream;
import org.wisenet.simulator.temp.insens.INSENSException;

/**
 * This is the base message information that every cleans slate message needs
 * @author Pedro Marques da Silva
 */
public class INSENSMessagePayload {

    public ByteArrayDataInputStream badis;
    public byte type;

    public INSENSMessagePayload(byte[] payload) throws INSENSException {
        try {
            if (payload == null) {
                throw new INSENSException("Invalid payload: cannot be null");
            }
            badis = new ByteArrayDataInputStream(payload);
            type = badis.readByte();
        } catch (IOException ex) {
            Logger.getLogger(INSENSMessagePayload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
