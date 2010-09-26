/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments;

import org.wisenet.simulator.core.Event;

/**
 * This class represents a Instrument event that enable the evaluation
 * procedure 
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class InstrumentEvent extends Event {

    private IInstrumentMessage message;
    private final IInstrumentHandler sender;
    private final int times;
    private final long interval;
    private int timesCt = 0;

    public InstrumentEvent(long time, IInstrumentMessage message, IInstrumentHandler sender, int times, long delay, long interval) {
        this.message = message;
        this.sender = sender;
        this.times = times;
        this.interval = interval;
        setTime(time + delay);
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
