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
public abstract class AbstractTestEvent extends Event {

    public AbstractTest getTest() {
        return test;
    }

    public void setTest(AbstractTest test) {
        this.test = test;
    }
    AbstractTest test;
}
