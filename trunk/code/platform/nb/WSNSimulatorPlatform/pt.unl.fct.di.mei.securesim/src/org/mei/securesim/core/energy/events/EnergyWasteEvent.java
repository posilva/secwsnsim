/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.core.energy.events;

import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class EnergyWasteEvent extends Event{
    protected  Node source;
    private static long clockTickTime = Simulator.ONE_SECOND / 100;
    public EnergyWasteEvent(long time) {
        super(time);
    }

    public EnergyWasteEvent() {
        super();
    }

    
    @Override
    public void execute() {
        source.getBateryEnergy().consume(0.00001);
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
