/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.coverage.listeners;

import java.util.EventObject;
import org.mei.securesim.components.instruments.coverage.CoverageInstrument.CoverageModelEnum;

/**
 *
 * @author Pedro Marques da Silva
 */
public class SignalUpdateEvent extends EventObject {

    CoverageModelEnum model;
    protected boolean signal;

    public SignalUpdateEvent(Object source) {
        super(source);
    }

    public boolean isSignal() {
        return signal;
    }

    public void setSignal(boolean signal) {
        this.signal = signal;
    }

    public CoverageModelEnum getModel() {
        return model;
    }

    public void setModel(CoverageModelEnum model) {
        this.model = model;
    }
}
