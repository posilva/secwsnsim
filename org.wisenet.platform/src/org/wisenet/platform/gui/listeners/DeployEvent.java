/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.platform.gui.listeners;

import java.util.EventObject;
import org.wisenet.platform.gui.SimulationPanel;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class DeployEvent extends EventObject {

    public DeployEvent(Object source) {
        super(source);
    }

    public SimulationPanel getSimulationPanel() {
        return (SimulationPanel) source;
    }
}
