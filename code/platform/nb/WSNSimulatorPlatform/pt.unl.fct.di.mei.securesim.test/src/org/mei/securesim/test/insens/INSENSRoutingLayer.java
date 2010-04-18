/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens;

import org.mei.securesim.test.insens.messages.APPMsg;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.insens.messages.FDBKMsg;
import org.mei.securesim.test.insens.messages.INSENSMsg;
import org.mei.securesim.test.insens.messages.RREQMsg;
import org.mei.securesim.test.insens.messages.RUPDMsg;

/**
 *
 * @author pedro
 */
public class INSENSRoutingLayer extends RoutingLayer {

    boolean stable;
    int nk;
    private long currentOWS;

    @Override
    public void receiveMessage(Object message) {
        if (message instanceof INSENSMsg) {
            handleMessageReceive(message);
        }
    }

    @Override
    public void sendMessageDone() {
    }

    @Override
    public boolean sendMessage(Object message, Application app) {
        if (message instanceof INSENSMsg) {
            this.application = app;
            handleMessageSend(message);
            return true;
        } else {
            return false;
        }
    }

    private void handleMessageSend(Object message) {
        if (message instanceof APPMsg) {
            handleAPPSend(message);
        }
    }

    private void handleMessageReceive(Object message) {
        if (message instanceof RREQMsg) {
            handleRREQReceive(message);
        } else if (message instanceof FDBKMsg) {
            handleFDBKReceive(message);
        } else if (message instanceof RUPDMsg) {
            handleRUPDReceive(message);
        }
    }

    private void handleRUPDReceive(Object message) {
    }

    private void handleFDBKReceive(Object message) {
    }

    private void handleRREQReceive(Object message) {
    }

    private void handleRUPDSend(Object message) {
    }

    private void handleFDBKSend(Object message) {
    }

    private void handleRREQSend(Object message) {
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }

    private void handleAPPSend(Object message) {
        APPMsg appMsg = (APPMsg) message;

        if (appMsg.getAction()==INSENSConstants.ACTION_START){
            if (getNode().isSinkNode()){
                // this is a base station so... lets start protocol
                startNewRound();
            }
        }
    }

    private void startNewRound() {
         broadcastInitialRouteRequest();
    }

    private void broadcastInitialRouteRequest() {
        // create de message instance

        RREQMsg routeRequestMessage = new RREQMsg(null);
        currentOWS=INSENSConstants.getNextOWS();

        routeRequestMessage.setOWS(INSENSConstants.getNextOWS());


    }
    



}
