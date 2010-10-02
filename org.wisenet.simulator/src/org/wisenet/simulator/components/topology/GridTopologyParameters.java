/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.topology;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class GridTopologyParameters extends TopologyParameters {

    @Override
    protected void setExtendedSupportedParameters() {
       init("distance",0);
    }
}
