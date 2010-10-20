/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation;

import java.util.HashSet;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.core.energy.GlobalEnergyDatabase;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class TestManager {

    GlobalEnergyDatabase energyDatabase = new GlobalEnergyDatabase();
    MessageDatabase messageDatabase = new MessageDatabase();
    HashSet<AbstractTest> testTable = new HashSet<AbstractTest>();
    AbstractTest currentTest = null;

    public void onStartTest(AbstractTest test) {
        if (!isTestInExecution()) {
            currentTest = test;
            testTable.add(test);

        }
    }

    public void onEndTest(AbstractTest test) {
        if (isTestInExecution()) {

            currentTest = null;
        }
    }

    public void reset() {
        energyDatabase.reset();
        messageDatabase.reset();
        testTable.clear();
    }

    public boolean isTestInExecution() {
        return currentTest != null;
    }

    public boolean executeTest(AbstractTest test) {
        if (!isTestInExecution()) {
            test.execute();
            return true;
        } else {
            return false;
        }

    }
}
