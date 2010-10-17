/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.topology;

import java.util.List;
import java.util.Vector;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class StaticTopologyManager extends TopologyManager {

    /**
     *
     * @return
     */
    @Override
    protected List<Node> createTopologyImpl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param nodes
     * @param parameters
     * @return
     */
    @Override
    public Vector<Node> apply(Vector<Node> nodes, TopologyParameters parameters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
