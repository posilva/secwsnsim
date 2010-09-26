/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens;

import javax.swing.JOptionPane;
import org.wisenet.protocols.insens.messages.INSENSDATAMessage;
import org.wisenet.simulator.components.output.ApplicationOutput;
import org.wisenet.simulator.core.Application;

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

    @Override
    public void receiveMessage(Object message) {
        if (message instanceof byte[]) {
            ApplicationOutput.getInstance().output(this, "RECEIVED:  " + new String((byte[]) message));
        }
    }
}
