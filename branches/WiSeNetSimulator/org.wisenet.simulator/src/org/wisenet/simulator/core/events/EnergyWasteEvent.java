package org.wisenet.simulator.core.events;

import org.wisenet.simulator.core.Event;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Node;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class EnergyWasteEvent extends Event {

    protected Node source;
    private static long clockTickTime = Simulator.ONE_SECOND / 10;

    public EnergyWasteEvent(long time) {
        super(time);
    }

    public EnergyWasteEvent() {
        super();
    }

    @Override
    public void execute() {
        source.getBateryEnergy().consumeIdle();
        time += clockTickTime;
        source.simulator.addEvent(this);
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }
}
