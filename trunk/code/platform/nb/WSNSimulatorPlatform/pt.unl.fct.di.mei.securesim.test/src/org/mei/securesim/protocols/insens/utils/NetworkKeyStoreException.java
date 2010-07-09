package org.mei.securesim.protocols.insens.utils;

/**
 *
 * @author CIAdmin
 */
public class NetworkKeyStoreException extends Exception{

    public NetworkKeyStoreException(Throwable cause) {
        super(cause);
    }

    public NetworkKeyStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkKeyStoreException(String message) {
        super(message);
    }

    public NetworkKeyStoreException() {
    }

}
