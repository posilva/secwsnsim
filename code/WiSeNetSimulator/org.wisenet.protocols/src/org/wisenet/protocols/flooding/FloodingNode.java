/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.protocols.flooding;

import org.wisenet.protocols.common.BasicNode;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.radio.RadioModel;

/**
 *
 * @author CIAdmin
 */
public class FloodingNode extends BasicNode {

    public FloodingNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
    }

    public Object getCoverageId() {
        return getId();
    }

    public Object getLatencyUniqueId() {
        return getId();
    }
}
