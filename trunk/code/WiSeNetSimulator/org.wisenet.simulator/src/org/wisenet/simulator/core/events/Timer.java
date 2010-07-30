/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.core.events;

import org.wisenet.simulator.components.instruments.SimulationController;
import org.wisenet.simulator.core.engine.Event;

/**
 *
 * @author Pedro Marques da Silva
 */
public abstract class Timer extends Event {

    int times = -1;
    int timesCount = 1;
    long delay = 1;
    boolean stop = false;

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Timer() {
        times = -1;
        delay = 1;
    }

    public Timer(long delay) {
        this.delay = delay;
    }

    public Timer(int times, long delay) {
        this.times = times;
        this.delay = delay;
    }

    @Override
    public void execute() {

        execution();
        if (!stop) {
            reschedule();
        }
    }

    public void reschedule() {
        setTime(getTime() + delay);
        SimulationController.getInstance().getSimulation().getSimulator().addEvent(this);
    }

    public void stop() {
        stop = true;
    }

    public void start() {
        stop = false;
        setTime(SimulationController.getInstance().getSimulation().getTime());
        reschedule();
    }

    public void start(int times, long delay) {
        this.times = times;
        this.delay = delay;
        start();
    }

    public abstract void executeAction();

    private void execution() {
        timesCount++;

        executeAction();
        if (times > -1 && timesCount > times) {
            stop();
        }
    }
}
