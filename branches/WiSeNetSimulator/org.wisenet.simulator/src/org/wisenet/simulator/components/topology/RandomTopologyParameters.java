/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.topology;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class RandomTopologyParameters extends TopologyParameters {

    @Override
    protected void setExtendedSupportedParameters() {
        init("seed", System.currentTimeMillis());
    }
}
