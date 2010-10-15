package org.wisenet.simulator.components.evaluation;

import java.util.ArrayList;
import java.util.List;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.energy.GlobalEnergyDatabase;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;

/**
 *
 * @author posilva
 */
public class EvaluationManager {

    MessageDatabase messageDatabase = new MessageDatabase();
    GlobalEnergyDatabase energyDatabase;
    List<Message> allMessages = new ArrayList<Message>();
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
            energyDatabase = test.getSimulation().getEnergyController().createDatabase(test.getName(), true);
            started = true;
        }
    }

    /**
     * Notify test end
     */
    public void endTest() {
        if (started) {
            energyDatabase = test.getSimulation().getEnergyController().getDatabase(test.getName());
        }
    }

    public void registerMessageSent(Object message, RoutingLayer routing) {
        messageDatabase.registerMessageSent((Message) message, routing);
    }

    public void registerMessageArrived(Object message, RoutingLayer routing) {
    }

    public void registerMessageReceivedDone(Object message, RoutingLayer routing) {
        // Message received right
        messageDatabase.registerMessageReceived((Message) message, routing);
    }

    public GlobalEnergyDatabase getEnergyDatabase() {
        return energyDatabase;
    }

    public MessageDatabase getMessageDatabase() {
        return messageDatabase;
    }

    public boolean isStarted() {
        return started;
    }
}
