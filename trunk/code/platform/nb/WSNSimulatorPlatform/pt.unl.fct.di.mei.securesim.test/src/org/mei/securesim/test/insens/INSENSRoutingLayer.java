/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens;

import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.routing.RoutingLayer;

/**
 *
 * @author pedro
 */
public class INSENSRoutingLayer extends RoutingLayer{

    @Override
    public void receiveMessage(Object message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendMessageDone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean sendMessage(Object message, Application app) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
