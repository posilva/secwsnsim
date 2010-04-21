/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.test.insens.utils.INSENSConstants;
import org.mei.securesim.test.insens.messages.APPMsg;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.insens.messages.FDBKMsg;
import org.mei.securesim.test.insens.messages.INSENSMsg;
import org.mei.securesim.test.insens.messages.MessagesFactory;
import org.mei.securesim.test.insens.messages.RREQMsg;
import org.mei.securesim.test.insens.messages.RUPDMsg;
import org.mei.securesim.test.insens.utils.INSENSUtils;
import org.mei.securesim.utils.DataUtils;

/**
 *
 * @author pedro
 */
public class INSENSRoutingLayer extends RoutingLayer {

    boolean stable;
    private long currentOWS;
    private int myParent;
    private int roundNumber;
    private Set neighboorsSet = new HashSet();

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
        if (getNode().isSinkNode()) {
            // DESCARTO AS MENSAGENS RECEBIDAS
        } else {
            try {
                RREQMsg msg = (RREQMsg) message;
                byte[] payload = msg.getPayload();
                DataInputStream dis = DataUtils.createDataFromByteArray(payload);
                int type = dis.readInt();
                int senderID = dis.readInt();
                addToNeighboorSet(senderID);
                long ows = dis.readLong();
                byte[] parentMAC = new byte[CryptoFunctions.MAC_SIZE];
                dis.read(parentMAC);
                if (ows != currentOWS) {
                    // é novo ou então não é fresco
                    // se for novo
                    newRouteDiscovery(senderID, ows, parentMAC);
                }
                addToNeighboorSet(senderID);

            } catch (IOException ex) {
                Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }



        }

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

        if (appMsg.getAction() == INSENSConstants.ACTION_START) {
            if (getNode().isSinkNode()) {
                // this is a base station so... lets start protocol
                startNewRound();
            }
        }
    }

    private void startNewRound() {
        roundNumber++;
        currentOWS = INSENSUtils.getNextOWS(roundNumber);
        broadcastInitialRouteRequest();
    }

    private void broadcastInitialRouteRequest() {
        // create de message instance
        RREQMsg m = (RREQMsg) MessagesFactory.getInitialRouteRequestMessage(getNode().getId(), currentOWS, ((INSENSNode) getNode()).getMacKey());
        sendDelayedMessage(m);
    }

    private void addToNeighboorSet(int id) {
        neighboorsSet.add(id);
    }

    private void newRouteDiscovery(int parentId, long ows, byte[] macP) {
        myParent = parentId;
        resetNeighboorSet();
        INSENSMsg m = MessagesFactory.getRouteRequestMessage(getNode().getId(), ows, macP, ((INSENSNode) getNode()).getMacKey());
        sendDelayedMessage(m);
        waitToSendFeedBack();

    }

    private void sendDelayedMessage(INSENSMsg m) {
        Event e = new DelayedMessageEvent(m);
        getNode().getSimulator().addEvent(e);
    }

    private void resetNeighboorSet() {
        neighboorsSet.clear();
    }

    private void waitToSendFeedBack() {
        Event e = new FeedbackWaitEvent();
        getNode().getSimulator().addEvent(e);
    }

    private void sendFeedbackMessage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 
     */
    class FeedbackWaitEvent extends Event {

        public FeedbackWaitEvent() {
            setTime(getNode().getSimulator().getSimulationTime() + INSENSConstants.FEEDBACK_WAITING_TIME);
        }

        @Override
        public void execute() {
            sendFeedbackMessage();
        }
    }

    /**
     * 
     */
    class DelayedMessageEvent extends Event {

        Object message;

        public Object getMessage() {
            return message;
        }

        public DelayedMessageEvent(Object message) {
            setTime(getNode().getSimulator().getSimulationTime() + INSENSConstants.DELAY_TIME_MESSAGE);
            this.message = message;
        }

        public DelayedMessageEvent(long time, Object message) {
            super(time);
            this.message = message;
        }

        @Override
        public void execute() {
            getNode().getMacLayer().sendMessage(getMessage(), INSENSRoutingLayer.this);
        }
    }
}
