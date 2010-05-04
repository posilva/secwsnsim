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
import org.mei.securesim.core.engine.DefaultMessage;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.insens.messages.FDBKMsg;
import org.mei.securesim.test.insens.messages.FDBKMsg.MACS;
import org.mei.securesim.test.insens.messages.FDBKMsg.PathInfo;
import org.mei.securesim.test.insens.messages.INSENSMsg;
import org.mei.securesim.test.insens.messages.MessagesFactory;
import org.mei.securesim.test.insens.messages.RREQMsg;
import org.mei.securesim.test.insens.messages.RUPDMsg;
import org.mei.securesim.test.insens.utils.INSENSFunctions;
import static org.mei.securesim.test.insens.utils.INSENSFunctions.cloneMAC;

/**
 *
 * @author pedro
 */
public class INSENSRoutingLayer extends RoutingLayer {

    boolean stable;
    private long currentOWS = -1;
    private RREQMsg firstRREQMsg;
    private int myParentId = -1;
    private int roundNumber = -1;
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
          System.out.println("DONE... ["+ getNode().getId()+"] message sended "+ getNode().getMessage());
    }

    @Override
    public boolean sendMessage(Object message, Application app) {

        DefaultMessage mm = (DefaultMessage) message;
//        System.out.println("Send Message: " +mm.getMessageNumber() +"/"+ DefaultMessage.getNumberOfTotalMessage());
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
        System.out.println(getNode().getId() + " Receive FDBK: " + message + " from " + m.getOrigin());
        if (getNode().isSinkNode()) {
            saveFeedbackMessage(m);
        } else {
            // verifica se eu sou o pai do vizinho que enviou a mensagem
            if (isFromMyChild(m)) {
                forwardMessage2BaseStation(m);
            } else {
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
                try {
                    // se receber um ows diferente do corrente
                    // então é uma nova epoca e esta é a primeira vez q recebe
                    // então faz broadcast
                    firstRREQMsg = (RREQMsg) msg.clone();

                    myParentId = msg.getId();
                    System.out.println(getNode().getId() + ": Recebi uma mensagem RREQ"+ msg.getMessageNumber()+" from " + msg.getOrigin());
                    newRouteDiscovery(msg);
                    // actualiza o ows corrente
                    currentOWS = ows;
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            addToNeighboorSet(senderID, INSENSFunctions.cloneMAC(parentMAC));
        }

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
//            System.out.println("APP START Message Received ");

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

        myMAC = cloneMAC(m.getMAC());

        if (myMAC == null) {
            System.out.println("MAC NULL");
        }

        sendDelayedMessage(m);
    }

    private void addToNeighboorSet(int id, byte[] mac) {
        neighboorsSet.put(id, mac);
    }

    private void newRouteDiscovery(RREQMsg reqMessage) {
        resetNeighboorSet();
        INSENSMsg m = MessagesFactory.createForwardRouteRequestMessage(reqMessage, getNode().getId(), ((INSENSNode) getNode()).getMacKey());
        if (m.getMAC() == null) {
            System.out.println("newRouteDiscovery: MAC == NULL");
        }
        myMAC = cloneMAC(m.getMAC());
        sendDelayedMessage(m);
        waitToSendFeedBack();

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
        try {
            FDBKMsg message = new FDBKMsg(null);
            System.out.println("[" + getNode().getId() + "] sending fdbk message" +message.getMessageNumber() +" to " + myParentId);

            NbrInfo nbrInfo = message.new NbrInfo();

            PathInfo pathInfo = message.new PathInfo();
            pathInfo.id = getNode().getId();
            pathInfo.size = firstRREQMsg.getSize();
            pathInfo.pathToMe = new Vector();

            for (Integer i : firstRREQMsg.getPath()) {
                pathInfo.pathToMe.addElement(i.intValue());
            }

            pathInfo.MACRx = cloneMAC(firstRREQMsg.getMAC());
            nbrInfo.size = neighboorsSet.size();
            Vector macs = new Vector();
            for (Integer key : neighboorsSet.keySet()) {
                macs.add(message.new MACS(key, neighboorsSet.get(key)));
            }
            nbrInfo.macs = macs;
            message = (FDBKMsg) MessagesFactory.createFeedbackMessage(currentOWS, ((INSENSNode) getNode()).getMacKey(), (NbrInfo) (nbrInfo.clone()), (PathInfo) (pathInfo.clone()), cloneMAC(neighboorsSet.get(myParentId)));
            message.setOrigin(getNode().getId());
            sendDelayedMessage(message);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isFromMyChild(FDBKMsg m) {
        if (myMAC == null) {
            System.out.println("MAC NULL");
            return false;
        }
        System.out.println(getNode().getId() + ":MyMAC= " + INSENSFunctions.toHex(myMAC)
                + " " + ":MACRParent= " + INSENSFunctions.toHex(m.getMACRParent()) + " forward by " + m.getForwardBy());

        return (Arrays.equals(m.getMACRParent(), myMAC));

    }

    /**
     * Ao receber uma mensagem de um nó descendente, é necessário copiar a msg
     * por forma a não arrastar referencias
     * @param m
     */
    private void forwardMessage2BaseStation(FDBKMsg m) {
        try {
            System.out.println(getNode().getId() + ": Forward Message "+ m.getMessageNumber() +" from " + m.getOrigin() + " By " + m.getForwardBy());
            // copia da mensagem
            FDBKMsg clone = (FDBKMsg) m.clone();

            System.out.println(getNode().getId() + ":CurrentParentMAC= " + INSENSFunctions.toHex(m.getMACRParent())
                    + " " + ":NewMACRParent= " + INSENSFunctions.toHex(neighboorsSet.get(myParentId)));
            clone.setMACRParent(cloneMAC(neighboorsSet.get(myParentId)));
            clone.setForwardBy(getNode().getId());
            sendDelayedMessage(clone);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * enviar uma mensagem usando um temporizador
     * @param m
     */
    private void sendDelayedMessage(INSENSMsg m) {
//        Event e = new DelayedMessageEvent(m);
//        getNode().getSimulator().addEvent(e);
        if (!getNode().getMacLayer().sendMessage(m, this)) {
            System.out.println("Sending failed");
        }

    }

    private void saveFeedbackMessage(FDBKMsg m) {
        System.out.println("Saved FDBK Message "+ m.getMessageNumber() + " From " + m.getOrigin());
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
            double result = INSENSConstants.MIN_DELAY_TIME_MESSAGE + (int) (Simulator.randomGenerator.random().nextDouble() * (INSENSConstants.MAX_DELAY_TIME_MESSAGE - INSENSConstants.MIN_DELAY_TIME_MESSAGE));
            setTime(getNode().getSimulator().getSimulationTime() + (int) result);
            this.message = message;
        }

        public DelayedMessageEvent(long time, Object message) {
            super(time);
            this.message = message;
        }

        @Override
        public void execute() {
            if (!getNode().getMacLayer().sendMessage(getMessage(), INSENSRoutingLayer.this)) {
                System.out.println("Sending failed");
            }
            ;
        }
    }
}
