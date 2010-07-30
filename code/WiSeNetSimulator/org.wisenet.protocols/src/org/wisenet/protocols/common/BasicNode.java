package org.wisenet.protocols.common;


import java.awt.Color;
import org.wisenet.simulator.core.engine.Simulator;
import org.wisenet.simulator.core.nodes.basic.Mica2SensorNode;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.gui.IDisplayable;

public class BasicNode extends Mica2SensorNode implements IDisplayable {

    public BasicNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
        setBaseColor(Color.yellow);
    }

    @Override
    public void init() {
        super.init();
    }
}
