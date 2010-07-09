/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.flooding;

import org.mei.securesim.components.instruments.coverage.ITotalCoverageHandler;
import org.mei.securesim.components.instruments.latency.ILatencyHandler;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.protocols.common.BasicNode;

/**
 *
 * @author CIAdmin
 */
public class FloodingNode extends BasicNode implements ITotalCoverageHandler, ILatencyHandler {

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
