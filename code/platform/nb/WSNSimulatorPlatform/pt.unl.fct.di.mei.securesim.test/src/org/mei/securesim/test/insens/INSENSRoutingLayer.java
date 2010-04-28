/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.test.insens.messages.FDBKMsg.NbrInfo;
import org.mei.securesim.test.insens.utils.INSENSConstants;
import org.mei.securesim.test.insens.messages.APPMsg;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.insens.messages.FDBKMsg;
import org.mei.securesim.test.insens.messages.FDBKMsg.MACS;
import org.mei.securesim.test.insens.messages.FDBKMsg.PathInfo;
import org.mei.securesim.test.insens.messages.INSENSMsg;
import org.mei.securesim.test.insens.messages.MessagesFactory;
import org.mei.securesim.test.insens.messages.RREQMsg;
import org.mei.securesim.test.insens.messages.RUPDMsg;
import org.mei.securesim.test.insens.utils.INSENSFunctions;

/**
 *
 * @author pedro
 */
public class INSENSRoutingLayer extends RoutingLayer {

    boolean stable;
    private long currentOWS=-1;
    private RREQMsg firstRREQMsg;
    private int myParentId=-1;
    private int roundNumber=-1;
    private HashMap<Integer, byte[]> neighboorsSet = new HashMap<Integer, byte[]>();
    private byte[] myMAC;

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
        System.out.println("Sending message");
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
        FDBKMsg m = (FDBKMsg) message;
        if (getNode().isSinkNode()){
            saveFeedbackMessage(m);
        }else{
            // verifica se eu sou o pai do vizinho que enviou a mensagem
            if (isMyChild(m) ){
                sendMessage2BaseStation(m);
            }
        }
    }
    /**
     * tratamento de recepção de mensagem de Route Requests
     * @param message
     */
    private void handleRREQReceive(Object message) {
        if (getNode().isSinkNode()) {
            // DESCARTO AS MENSAGENS RECEBIDAS
        } else {
                RREQMsg msg = (RREQMsg) message;
                int senderID = msg.getId();
                long ows = msg.getOWS();
                byte[] parentMAC = msg.getMAC();
                if (ows != currentOWS) {
                    // se receber um ows diferente do currente
                    // então é uma nova epoca e esta é a primeira vez q recebe
                    // então faz broadcast
                    firstRREQMsg=msg;
                    myParentId=msg.getId();
                    newRouteDiscovery(msg);
                    // actualiza o ows corrente
                    currentOWS=ows;
                }
                addToNeighboorSet(senderID,parentMAC);
        }

    }
   
    private void handleRUPDSend(Object message) {
    }

    private void handleFDBKSend(Object message) {
    }

    private void handleRREQSend(Object message) {
    }
    /**
     * permite controlar se se pode utilizar o encaminhamento para
     * comunicação de dados ou se encontra em fase de organização da rede
     * @return
     */
    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }
    /**
     * Mensagens especiais para interacção com a camada de aplicação
     * @param message
     */
    private void handleAPPSend(Object message) {
        APPMsg appMsg = (APPMsg) message;

        if (appMsg.getAction() == INSENSConstants.ACTION_START) {
            System.out.println("APP START Message Received ");

            if (getNode().isSinkNode()) {
                // this is a base station so... lets start protocol
                reorganizeNetwork();
            }
        }
    }
    /**
     * Inicio de uma nova organização da rede
     */
    private void reorganizeNetwork() {
        roundNumber++;
        currentOWS = INSENSFunctions.getNextOWS(roundNumber);
        startNetworkOrganization();
    }
    /**
     * Mensagem inicial de reorganização da rede
     */
    private void startNetworkOrganization() {
        // create de message instance
        RREQMsg m = (RREQMsg) MessagesFactory.createRouteRequestMessage(getNode().getId(), currentOWS, ((INSENSNode) getNode()).getMacKey());
        myMAC=m.getMAC();
        sendDelayedMessage(m);
    }

    private void addToNeighboorSet(int id, byte[] mac) {
        neighboorsSet.put(id,mac);
    }

    private void newRouteDiscovery(RREQMsg reqMessage) {
        resetNeighboorSet();
        INSENSMsg m =MessagesFactory.createForwardRouteRequestMessage(reqMessage, getNode().getId(), ((INSENSNode)getNode()).getMacKey());
        myMAC=m.getMAC();
        sendDelayedMessage(m);
        waitToSendFeedBack();

    }
    /**
     * enviar uma mensagem usando um temporizador
     * @param m
     */
    private void sendDelayedMessage(INSENSMsg m) {
        Event e = new DelayedMessageEvent(m);
        getNode().getSimulator().addEvent(e);
    }
    /**
     * Reinicia os dados referentes aos vizinhos conhecidos
     */
    private void resetNeighboorSet() {
        neighboorsSet.clear();
    }
    /**
     * Temporizador que controla o tempo de inicio de envio de Mensagem de
     * Feedback
     */
    private void waitToSendFeedBack() {
        Event e = new FeedbackWaitEvent();
        getNode().getSimulator().addEvent(e);
    }

    private void sendFeedbackMessage() {
        FDBKMsg message =new FDBKMsg(null);
        NbrInfo nbrInfo = message.new NbrInfo();
        PathInfo pathInfo = message.new PathInfo();

        pathInfo.id = getNode().getId();
        pathInfo.size = firstRREQMsg.getSize();
        pathInfo.pathToMe= firstRREQMsg.getPath();
        pathInfo.MACRx = firstRREQMsg.getMAC();

        nbrInfo.size = neighboorsSet.size();

        Vector macs = new Vector();
        for (Integer key : neighboorsSet.keySet()) {
            macs.add(message.new MACS(key, neighboorsSet.get(key)));
        }
        nbrInfo.macs=macs;

        message = (FDBKMsg)MessagesFactory.createFeedbackMessage(currentOWS, ((INSENSNode)getNode()).getMacKey(),nbrInfo ,pathInfo , neighboorsSet.get(myParentId));

        sendDelayedMessage(message);
    }

    private boolean isMyChild(FDBKMsg m) {
        return  (Arrays.equals(m.getMACRParent(),myMAC));

    }

    private void sendMessage2BaseStation(FDBKMsg m) {
        try {
            FDBKMsg clone = (FDBKMsg) m.clone();
            clone.setMACRParent(neighboorsSet.get(myParentId));
            sendDelayedMessage(m);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void saveFeedbackMessage(FDBKMsg m) {
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Class para materializar um evento de temporização
     * de mensagens de feedback
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
     * Class que possibilita o envio de uma mensagens usando
     * um temporizador
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
            System.out.println("Executing delayed Message");
            getNode().getMacLayer().sendMessage(getMessage(), INSENSRoutingLayer.this);
        }
    }
}
