/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.simulation.listeners;

import java.util.EventObject;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;

/**
 *
 * @author posilva
 */
public class SimulationTestEvent extends EventObject {

    /**
     *
     * @param test
     */
    public SimulationTestEvent(AbstractTest test) {
        super(test);
    }
}
