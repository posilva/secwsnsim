/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.flooding;

import org.wisenet.protocols.common.BasicNode;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.radio.RadioModel;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class FloodingNode extends BasicNode {

    /**
     *
     * @param sim
     * @param radioModel
     */
    public FloodingNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
        getGraphicNode().setRadius(3);
    }

    /**
     *
     * @return
     */
    public Object getCoverageId() {
        return getId();
    }

    /**
     *
     * @return
     */
    public Object getLatencyUniqueId() {
        return getId();
    }

    @Override
    public Object getUniqueID() {
        return getId();
    }
}
