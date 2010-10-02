/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.common;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class PersistantException extends Exception {

    public PersistantException(Throwable arg0) {
        super(arg0);
    }

    public PersistantException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public PersistantException(String arg0) {
        super(arg0);
    }

    public PersistantException() {
        super();
    }
}
