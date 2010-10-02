/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import org.wisenet.simulator.core.Event;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class TestEvent extends Event {

    protected AbstractTest relatedTest;
    protected boolean cancel;

    public boolean isCanceled() {
        return cancel;
    }

    public void cancel(boolean cancel) {
        this.cancel = cancel;
    }

    public AbstractTest getRelatedTest() {
        return relatedTest;
    }

    public void setRelatedTest(AbstractTest relatedTest) {
        this.relatedTest = relatedTest;
    }
}
