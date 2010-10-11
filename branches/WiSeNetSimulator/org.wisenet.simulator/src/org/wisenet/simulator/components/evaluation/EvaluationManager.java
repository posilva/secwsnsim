/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.evaluation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.evaluation.tests.TestMessage;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.energy.GlobalEnergyDatabase;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;

/**
 *
 * @author posilva
 */
public class EvaluationManager {

    protected Set<Message> testMessages = new HashSet<Message>();
    GlobalEnergyDatabase energyDatabase;
    List<TestMessage> allMessages = new ArrayList<TestMessage>();
    AbstractTest test;
    private boolean started;

    /**
     * Notify test start 
     * @param test
     *          a test instance that started
     */
    public void startTest(AbstractTest test) {
        if (!started) {
            this.test = test;
            energyDatabase = test.getSimulation().getEnergyController().createDatabase(test.getName());
            started = true;
        }
    }

    /**
     * Notify test end
     */
    public void endTest() {
        if (started) {
        }
    }

    public void signalMessage(Message message) {
        testMessages.add(message);
    }

    public Set<Message> getTestMessages() {
        return testMessages;
    }

    public void registerMessageSent(Object message, RoutingLayer routing) {

    }

    public void registerMessageReceived(Object message, RoutingLayer routing) {
        // message Received by a node
    }

    public void registerMessageReceivedDone(Object message, RoutingLayer routing) {
        // Message received rigth
    }
}
