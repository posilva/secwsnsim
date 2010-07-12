/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens;

import java.util.HashSet;
import org.mei.securesim.components.ApplicationOutput;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.events.Timer;
import org.mei.securesim.protocols.insens.messages.INSENSDATAMessage;

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
        return new Timer(100, Simulator.ONE_SECOND * 5) {

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

    @Override
    public void receiveMessage(Object message) {
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
