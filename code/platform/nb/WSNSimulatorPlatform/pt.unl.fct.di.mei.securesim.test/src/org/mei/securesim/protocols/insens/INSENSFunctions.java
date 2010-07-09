/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens;

import org.mei.securesim.protocols.insens.messages.INSENSMessage;
import org.mei.securesim.protocols.insens.messages.data.INSENSMessagePayload;
import org.mei.securesim.protocols.insens.utils.OneWaySequenceNumbersChain;

/**
 *
 * @author CIAdmin
 */
public class INSENSFunctions {
   
    protected static OneWaySequenceNumbersChain sequenceNumbersChain = new OneWaySequenceNumbersChain(INSENSConstants.CHAIN_SIZE);

    /**
     * Gets the initial sequence number from one way hash chain
     * @return
     */
    public static long getInitialSequenceNumber() {
        return sequenceNumbersChain.get(0);
    }

    /**
     * Gets the next number from de sequence of one way hash chain numbers
     * @return
     */
    public static long getNextOWS() {
        return sequenceNumbersChain.getNextSequenceNumber();
    }
    /**
     * Helper function for extract type  from message
     * @param m
     * @return
     */
    public static byte getMessageType(INSENSMessage m) throws INSENSException {
        INSENSMessagePayload payload = new INSENSMessagePayload(m.getPayload());
        return payload.type;
    }
}
