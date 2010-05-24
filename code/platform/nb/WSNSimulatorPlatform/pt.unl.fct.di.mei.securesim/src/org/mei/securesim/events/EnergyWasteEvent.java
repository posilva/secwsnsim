package org.mei.securesim.events;

import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
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
