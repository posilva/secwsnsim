/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.temp.insens;

import javax.swing.JOptionPane;
import org.wisenet.simulator.components.output.ApplicationOutput;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.temp.insens.messages.INSENSDATAMessage;

/**
 *
 * @author pedro
 */
public class INSENSApplication extends Application {

    @Override
    public void run() {
        if (!getNode().getRoutingLayer().isStable()) {
            JOptionPane.showMessageDialog(null, "Routing is not stable yet!");
            return;
        }
        INSENSDATAMessage dataMessage = new INSENSDATAMessage("HELLLO".getBytes());
        dataMessage.setSource(getNode().getId());
        dataMessage.setDestination((short) 1);
        getNode().getRoutingLayer().sendMessage(dataMessage, this);
    }

    protected void onMessageReceived(Object message) {
        if (message instanceof byte[]) {
            ApplicationOutput.getInstance().output(this, "RECEIVED:  " + new String((byte[]) message));
        }
    }
}
