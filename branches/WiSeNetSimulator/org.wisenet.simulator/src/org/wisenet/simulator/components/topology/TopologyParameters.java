/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.topology;

import org.wisenet.simulator.common.ObjectParameters;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class TopologyParameters extends ObjectParameters {
    
    protected abstract void setExtendedSupportedParameters();

    @Override
    protected void setSupportedParameters() {
        parameters.put("x", -1);
        parameters.put("y", -1);
        parameters.put("width", -1);
        parameters.put("height", -1);
        parameters.put("maxelevation", -1);
        parameters.put("nodes", -1);
    }
}
