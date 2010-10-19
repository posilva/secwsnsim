package org.wisenet.protocols.common;

import java.awt.Color;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Mica2SensorNode;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.gui.IDisplayable;

/**
 *
 * @author posilva
 */
public class BasicNode extends Mica2SensorNode implements IDisplayable {

    /**
     *
     * @param sim
     * @param radioModel
     */
    public BasicNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
        setBaseColor(Color.yellow);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public Object getUniqueID() {
        return getId();
    }
}


