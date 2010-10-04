/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens;

import java.util.HashSet;
import org.wisenet.protocols.insens.messages.INSENSDATAMessage;
import org.wisenet.simulator.components.output.ApplicationOutput;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.events.Timer;

/**
 *
 * @author pedro
 */
public class EvaluateINSENSApplication extends Application {

    static int sendedMessagesCounter = 0;
    HashSet<String> receivedBag = new HashSet<String>();
    int receivedMessagesCounter = 0;
    Timer reliabilityEvaluationTimer;

    Timer createNewEvaluationTimer() {
        return new Timer(getNode().getSimulator(),100, Simulator.ONE_SECOND * 5) {

            @Override
            public void executeAction() {
                String data = "" + sendedMessagesCounter++;

                INSENSDATAMessage dataMessage = new INSENSDATAMessage(data.getBytes());
                dataMessage.setSource(getNode().getId());
                dataMessage.setDestination((short) 1);
                getNode().getRoutingLayer().sendMessage(dataMessage, EvaluateINSENSApplication.this);

            }
        };
    }

    @Override
    public void run() {
        if (!getNode().getRoutingLayer().isStable()) {
            ApplicationOutput.getInstance().output(this, "Routing is not stable yet!");
            return;
        }
        if (reliabilityEvaluationTimer != null) {
            reliabilityEvaluationTimer.stop();
        }
        reliabilityEvaluationTimer = createNewEvaluationTimer();
        reliabilityEvaluationTimer.start();
    }

        protected void onMessageReceived(Object message) {
        if (message instanceof byte[]) {
            String receivedData = new String((byte[]) message);
            if (!receivedBag.contains(receivedData)) {

                ApplicationOutput.getInstance().output(this, "" + receivedMessagesCounter + " From " + sendedMessagesCounter);
                receivedMessagesCounter++;
            }
            receivedBag.add(receivedData);
        }
    }
}
