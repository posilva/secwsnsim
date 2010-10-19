/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */

package org.wisenet.protocols.insens;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class INSENSException extends Exception{

    /**
     *
     * @param cause
     */
    public INSENSException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public INSENSException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param message
     */
    public INSENSException(String message) {
        super(message);
    }

    /**
     *
     */
    public INSENSException() {
    }


}
