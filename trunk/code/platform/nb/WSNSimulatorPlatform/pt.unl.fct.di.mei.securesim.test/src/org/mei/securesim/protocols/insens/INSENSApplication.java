/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens;

import javax.swing.JOptionPane;
import org.mei.securesim.components.ApplicationOutput;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.protocols.insens.messages.INSENSDATAMessage;

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
        dataMessage.setDestination((short)1);
        getNode().getRoutingLayer().sendMessage(dataMessage, this);
    }

    @Override
    public void receiveMessage(Object message) {
        if (message instanceof byte[]){
            ApplicationOutput.getInstance().output(this,"RECEIVED:  "+ new String((byte[])message));
        }
    }

}
