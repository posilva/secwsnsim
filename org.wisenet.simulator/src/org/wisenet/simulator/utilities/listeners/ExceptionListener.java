/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */

package org.wisenet.simulator.utilities.listeners;

import java.util.EventListener;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface ExceptionListener extends EventListener{
    public void onError(ExceptionEvent event);
}
