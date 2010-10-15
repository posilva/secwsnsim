/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests.events;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class TestEndEvent extends AbstractTestEvent {

    @Override
    public void execute() {

        getTest().endTest();
    }
}
