/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests.events;

import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.core.Event;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class TestEvent extends Event {

    /**
     *
     */
    protected AbstractTest relatedTest;
    /**
     *
     */
    protected boolean cancel;

    /**
     *
     * @return
     */
    public boolean isCanceled() {
        return cancel;
    }

    /**
     *
     * @param cancel
     */
    public void cancel(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     *
     * @return
     */
    public AbstractTest getRelatedTest() {
        return relatedTest;
    }

    /**
     *
     * @param relatedTest
     */
    public void setRelatedTest(AbstractTest relatedTest) {
        this.relatedTest = relatedTest;
    }
}
