package org.wisenet.protocols.insens.utils;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class NetworkKeyStoreException extends Exception{

    /**
     *
     * @param cause
     */
    public NetworkKeyStoreException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public NetworkKeyStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param message
     */
    public NetworkKeyStoreException(String message) {
        super(message);
    }

    /**
     *
     */
    public NetworkKeyStoreException() {
    }

}
