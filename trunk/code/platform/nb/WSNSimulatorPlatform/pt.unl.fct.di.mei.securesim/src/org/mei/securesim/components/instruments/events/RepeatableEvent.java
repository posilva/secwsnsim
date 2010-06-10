/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.events;

import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.core.engine.Event;

/**
 *
 * @author Pedro Marques da Silva
 */
public abstract class RepeatableEvent extends Event {

    short numberOfRepetitions;
    long interval;

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public short getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    public void setNumberOfRepetitions(short numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    @Override
    public void execute() {
        doActions();
        setTime(SimulationController.getInstance().getCurrentSimulationTime() + interval);
        if (numberOfRepetitions > 1) {
            SimulationController.getInstance().getSimulation().getSimulator().addEvent(this);
        }
        numberOfRepetitions--;
    }

    protected abstract void doActions() ;
}
