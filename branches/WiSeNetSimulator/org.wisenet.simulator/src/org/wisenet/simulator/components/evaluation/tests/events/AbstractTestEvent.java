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
public abstract class AbstractTestEvent extends Event {

    /**
     *
     * @return
     */
    public AbstractTest getTest() {
        return test;
    }

    /**
     *
     * @param test
     */
    public void setTest(AbstractTest test) {
        this.test = test;
    }
    AbstractTest test;
}
