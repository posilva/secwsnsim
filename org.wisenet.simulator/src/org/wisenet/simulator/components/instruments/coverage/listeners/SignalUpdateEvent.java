/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments.coverage.listeners;

import java.util.EventObject;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument.CoverageModelEnum;

/**
 *
 * @author Pedro Marques da Silva
 */
public class SignalUpdateEvent extends EventObject {

    CoverageModelEnum model;
    /**
     *
     */
    protected boolean signal;

    /**
     *
     * @param source
     */
    public SignalUpdateEvent(Object source) {
        super(source);
    }

    /**
     *
     * @return
     */
    public boolean isSignal() {
        return signal;
    }

    /**
     *
     * @param signal
     */
    public void setSignal(boolean signal) {
        this.signal = signal;
    }

    /**
     *
     * @return
     */
    public CoverageModelEnum getModel() {
        return model;
    }

    /**
     *
     * @param model
     */
    public void setModel(CoverageModelEnum model) {
        this.model = model;
    }
}
