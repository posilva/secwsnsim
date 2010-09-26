/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments.coverage;

import java.util.EventListener;
import org.wisenet.simulator.components.instruments.coverage.listeners.SignalUpdateEvent;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface CoverageListener extends EventListener {

    public void onSignalUpdate(SignalUpdateEvent event);
}
