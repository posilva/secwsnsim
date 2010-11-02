/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities;

import org.wisenet.simulator.core.node.Node;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface NodeSelectionCondition {

    /**
     *
     * @param node
     * @return
     */
    public boolean select(Node node);
}
