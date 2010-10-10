/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests.events;

import org.wisenet.simulator.components.evaluation.EvaluationManager;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class TestStartEvent extends AbstractTestEvent {

    @Override
    public void execute() {
        this.test.setEvaluationManager(new EvaluationManager());
        this.test.getEvaluationManager().startTest(this.test);
    }
}
