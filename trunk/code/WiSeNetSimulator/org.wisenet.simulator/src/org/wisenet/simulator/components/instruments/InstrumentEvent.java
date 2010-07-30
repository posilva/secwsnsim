/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.instruments;

import org.wisenet.simulator.core.engine.Event;

/**
 *
 * @author CIAdmin
 */
public class InstrumentEvent extends Event {

    private IInstrumentMessage message;
    private final IInstrumentHandler sender;
    private final int times;
    private final long interval;
    private int timesCt = 0;

    public InstrumentEvent(IInstrumentMessage message, IInstrumentHandler sender, int times, long delay, long interval) {
        this.message = message;
        this.sender = sender;
        this.times = times;
        this.interval = interval;
        setTime(SimulationController.getInstance().getSimulation().getTime() + delay);
    }

    @Override
    public void execute() {
        sender.probing(message);
        timesCt++;
        reschedule();
    }

    private void reschedule() {
        if (timesCt < times) {
            setTime(getTime() + interval);
        }
    }
}
