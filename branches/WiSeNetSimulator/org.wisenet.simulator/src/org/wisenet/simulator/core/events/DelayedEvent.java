/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.events;

import org.wisenet.simulator.core.Event;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class DelayedEvent extends Event {

    long delay = 0;

    public long getDelay() {
        return delay;
    }

//    public DelayedEvent(long delay) {
//        super(delay);
//    }
    public DelayedEvent(long time, long delay) {
        super(time + delay);
        this.delay = delay;
    }

    public DelayedEvent() {
        delay = 0;
    }

    public void setDelay(long delay) {
        this.delay = delay;
        time += delay;
    }
}