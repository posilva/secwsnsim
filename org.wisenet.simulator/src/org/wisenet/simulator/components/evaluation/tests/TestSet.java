/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class TestSet {

    /**
     * 
     */
    Set testSet = new HashSet();
    /**
     * 
     */
    HashMap<TestTypeEnum, LinkedList<AbstractTest>> testByTypesTable = new HashMap<TestTypeEnum, LinkedList<AbstractTest>>();

    /**
     * Add a test to the Set
     * @param test
     */
    void add(AbstractTest test) {
        if (!testSet.contains(test)) {
            testSet.add(test);
            LinkedList<AbstractTest> list = testByTypesTable.get(test.getType());
            if (list == null) {
                list = new LinkedList<AbstractTest>();
            }
            list.add(test);
            testByTypesTable.put(test.getType(), list);
        }
    }

    /**
     * Remove a test from the Set
     * @param test
     */
    void remove(AbstractTest test) {
        if (testSet.contains(test)) {
            testSet.remove(test);
            LinkedList<AbstractTest> list = testByTypesTable.get(test.getType());
            if (list == null) {
                list = new LinkedList<AbstractTest>();
            }
            list.remove(test);
            testByTypesTable.put(test.getType(), list);
        }
    }

    /**
     *
     * @param testType
     * @return
     */
    public List getTestByType(TestTypeEnum testType) {
        return testByTypesTable.get(testType);
    }
}
