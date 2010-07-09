/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.protocols.insens;

/**
 *
 * @author CIAdmin
 */
public class INSENSException extends Exception{

    public INSENSException(Throwable cause) {
        super(cause);
    }

    public INSENSException(String message, Throwable cause) {
        super(message, cause);
    }

    public INSENSException(String message) {
        super(message);
    }

    public INSENSException() {
    }


}
