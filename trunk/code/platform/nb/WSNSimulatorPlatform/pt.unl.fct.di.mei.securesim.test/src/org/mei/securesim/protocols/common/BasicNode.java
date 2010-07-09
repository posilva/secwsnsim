package org.mei.securesim.protocols.common;


import java.awt.Color;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.gui.IDisplayable;
import org.mei.securesim.core.nodes.basic.Mica2SensorNode;

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
