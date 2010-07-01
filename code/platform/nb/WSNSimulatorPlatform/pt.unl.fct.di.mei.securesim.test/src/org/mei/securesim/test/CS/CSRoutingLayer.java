/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.CS;

import java.util.Hashtable;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.CS.messages.CSMessage;

/**
 *
 * @author Pedro Marques da Silva
 */
public class CSRoutingLayer extends RoutingLayer {
    long myGroupId;
    short myGroupSize;
    long oldGroupId;
    short oldGroupSize;
    Hashtable neighborsTable;
    Hashtable neighborsGroupsTable;

    @Override
    public void receiveMessage(Object message) {
        if (message instanceof CSMessage) {
        } else {
            log("Received Message Must be a CSMessage Instance");
        }
    }

    @Override
    public void sendMessageDone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean sendMessage(Object message, Application app) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void autostart() {
        neighborsGroupsTable = new Hashtable();
        neighborsTable = new Hashtable();

        oldGroupId = myGroupId = getNode().getId();
        oldGroupSize = myGroupSize = 1;

    }

    /**
     * Facilitator message for log
     * @param message
     */
    private void log(String message) {
        System.out.println("<" + getNode().getSimulator().getSimulationTime() + "> - [" + getNode().getId() + "] - " + message);
    }

    @Override
    public void routeMessage(Object message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}