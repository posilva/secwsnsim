/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.listeners;

import java.util.EventObject;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ExceptionEvent extends EventObject {

    public ExceptionEvent(Object source) {
        super(source);
    }

    public Throwable getThrowable() {
        return (Throwable) source;
    }
}
