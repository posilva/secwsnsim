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
        init("x", -1);
        init("y", -1);
        init("width", -1);
        init("height", -1);
        init("nodes", -1);
        setExtendedSupportedParameters();
    }
}
