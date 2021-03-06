/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.layers.mac;

import org.mei.securesim.core.energy.EnergyConsumptionAction;
import org.mei.securesim.core.engine.Message;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class Mica2MACLayer extends MACLayer {

    public short parentID = -1;
    /**
     * In this simulation not messages but references to motes are passed. All
     * this means is that the Mica2MACLayer has to hold the information on the
     * sender application which runs on this very mote.
     */
    RoutingLayer senderRoutingLayer;
    /**
     * This node is the one that sent the last message or the one this node is
     * receiving a message from right now. It is mainly used for display
     * purposes, as you know this information is not embedded into any TinyOS
     * message.
     */
    //protected Node parentNode = null;
    /**
     * State variable, true if radio failed to transmit a message do to high
     * radio traffic, this means it has to retry it later, which is done using
     * the {@link Mica2MACLayer#generateBackOffTime} function.
     */
    protected boolean sendingPostponed = false;
    // //////////////////////////////
    // MAC layer specific constants
    // //////////////////////////////
    /** The constant component of the time spent waiting before a transmission. */
    public static int sendMinWaitingTime = 200;//200;
    /** The variable component of the time spent waiting before a transmission. */
    public static int sendRandomWaitingTime = 128;//128;
    /** The constant component of the backoff time. */
    public static int sendMinBackOffTime = 100;
    /** The variable component of the backoff time. */
    public static int sendRandomBackOffTime = 30;
    /** The time of one transmission in 1/{@link Simulator#ONE_SECOND} second. */
    public static int sendTransmissionTime = 960;
    // //////////////////////////////
    // EVENTS
    // //////////////////////////////
    /**
     * Every mote has to test the radio traffic before transmitting a message,
     * if there is to much traffic this event remains a test and the mote
     * repeats it later, if there is no significant traffic this event initiates
     * message transmission and posts a {@link Mica2MACLayer#EndTransmissionEvent}
     * event.
     */
    protected TestChannelEvent testChannelEvent = new TestChannelEvent();
    /**
     * Signals the end of a transmission.
     */
    protected EndTransmissionEvent endTransmissionEvent = new EndTransmissionEvent();
    /**
     * The constant self noise level. See either the {@link Mica2MACLayer#calcSNR}
     * or the {@link Mica2MACLayer#isChannelFree} function.
     */
    public double noiseVariance = 0.025;
    /**
     * The maximum noise level that is allowed on sending. This is actually a
     * multiplicator of the {@link Mica2MACLayer#noiseVariance}.
     */
    public double maxAllowedNoiseOnSending = 5;//5;
    /** The minimum signal to noise ratio required to spot a message in the air. */
    public double receivingStartSNR = 4.0; //4.0
    /**
     * The maximum signal to noise ratio below which a message is marked
     * corrupted.
     */
    public double corruptionSNR = 2.0;//2.0

    public boolean deliverMessage(Object message) {
        setMessageColor(message);
        /**
         * Deliver the message to the handler we can intercept this handler to
         * notify controllers 
         */
        getNode().getRoutingLayer().receiveMessageHandler(message);
        return true;
    }

    @Override
    public void init() {
    }

    private String getSimData() {
        return "<" + getNode().getSimulator().getSimulationTime() + ">" + getNode().getId();
    }

    private void setMessageColor(Object message) {
        Message m = (Message) message;
        if (m.isShowColor()) {
            getNode().setMessageColor(m.getColor());
        }
    }

    /**
     * Inner class TestChannelEvent. Represents a test event, this happens when
     * the mote listens for radio traffic to decide about transmission.
     */
    protected class TestChannelEvent extends Event {

        /**
         * If the radio channel is clear it begins the transmission process,
         * otherwise generates a backoff and restarts testing later. It also
         * adds noise to the radio channel if the channel is free.
         */
        public void execute() {

            if (isChannelFree(noiseStrength)) {
                // start transmitting
                transmitting = true;
                final Node node = getNode();
                getNode().getTransceiver().executeTransmission(new EnergyConsumptionAction() {

                    Message m = (Message) node.getMessage();

                    public void execute() {
                        transmitMessage();
                    }

                    public int getNumberOfUnits() {
                        return m.size();
                    }
                });

                endTransmissionEvent.setTime(getTime() + sendTransmissionTime);
                getNode().getSimulator().addEvent(endTransmissionEvent);
            } else {
                time += generateBackOffTime();
                getNode().getSimulator().addEvent(this);
            }
        }

        @Override
        public String toString() {
            return Long.toString(time) + "\tTestChannelEvent\t"
                    + getNode();
        }
    }

    /**
     * Inner class EndTransmissionEvent. Represents the end of a transmission.
     */
    protected class EndTransmissionEvent extends Event {

        /**
         * Removes the noise generated by the transmission and sets the state
         * variables accordingly.
         */
        public void execute() {
            transmitting = false;
            sending = false;
            endTransmission();
            senderRoutingLayer.sendMessageDone();
        }

        @Override
        public String toString() {
            return Long.toString(time) + "\tNode.EndTransmissionEvent\t"
                    + getNode();
        }
    }

    /**
     * Calls the {@link Mica2MACLayer#addNoise} method. See also
     * {@link Node#receptionBegin} for more information.
     */
    public synchronized void receptionBegin(double strength, Object stream) {
        // inicio da recepção, pode-se verificar o estado do nó e
        // caso o TX esteja ON recebe senão aborta
        addNoise(strength, stream);
    }

    /**
     * Calls the {@link Mica2MACLayer#removeNoise} method. See also
     * {@link Node#receptionEnd} for more information.
     */
    public synchronized void receptionEnd(double strength, Object stream) {
        removeNoise(strength, stream);
    }

    /**
     * Sends out a radio message. If the node is in receiving mode the sending
     * is postponed until the receive is finished. This method behaves exactly
     * like the SendMsg.send command in TinyOS.
     *
     * @param message
     *            the message to be sent
     * @param app
     *            the application sending the message
     * @return If the node is in sending state it returns false otherwise true.
     */
    public synchronized boolean sendMessage(Object message, RoutingLayer app) {
        totalMessagesSent++;
        if (sending) {
            totalMessagesNotSent++;
            if (isDebugEnabled()) {
                System.out.println(getSimData() + " - Messages not Sent: " + totalMessagesNotSent);
            }
            return false;
        } else {
            sending = true;
            transmitting = false;
            this.getNode().setMessage(message);
            senderRoutingLayer = (RoutingLayer) app;

            if (receiving) {
                sendingPostponed = true;
            } else {
                sendingPostponed = false;
                testChannelEvent.setTime(getNode().getSimulator().getSimulationTime()
                        + generateWaitingTime());
                getNode().getSimulator().addEvent(testChannelEvent);
            }
            return true;
        }
    }

    /**
     * Generates a waiting time, adding a randomGenerator variable time to a constant
     * minimum.
     *
     * @return returns the waiting time in milliseconds
     */
    public static int generateWaitingTime() {
        return sendMinWaitingTime
                + (int) (Simulator.randomGenerator.random().nextDouble() * sendRandomWaitingTime);
    }

    /**
     * Generates a backoff time, adding a randomGenerator variable time to a constant
     * minimum.
     *
     * @return returns the backoff time in milliseconds
     */
    protected static int generateBackOffTime() {
        return sendMinBackOffTime
                + (int) (Simulator.randomGenerator.random().nextDouble() * sendRandomBackOffTime);
    }

    /**
     * Tells if the transmitting media is free of transmissions based on the
     * noise level.
     *
     * @param noiseStrength
     *            the level of noise right before transmission
     * @return returns true if the channel is free
     */
    protected boolean isChannelFree(double noiseStrength) {
        return noiseStrength < maxAllowedNoiseOnSending * noiseVariance;
    }

    /**
     * Tells if the transmitting media is free of transmissions based on the
     * noise level.
     *
     * @param signal
     *            the signal strength
     * @param noise
     *            the noise level
     * @return returns true if the message is corrupted
     */
    public boolean isMessageCorrupted(double signal, double noise) {
        return calcSNR(signal, noise) < corruptionSNR;
    }

    /**
     * Inner function for calculating the signal noise ratio the following way: <br>
     * signal / (noiseVariance + noise).
     *
     * @param signal
     *            the signal strength
     * @param noise
     *            the noise level
     * @return returns the SNR
     */
    protected double calcSNR(double signal, double noise) {
        return signal / (noiseVariance + noise);
    }

    /**
     * Tells if the incomming message signal is corrupted by another signal.
     *
     * @param signal
     *            the signal strength of the incomming message
     * @param noise
     *            the noise level
     * @return returns true if the message is corrupted
     */
    public boolean isReceivable(double signal, double noise) {
        return calcSNR(signal, noise) > receivingStartSNR; // PMS
    }

    protected void transmitMessage() {
        beginTransmission(1, getNode());
    }

    /**
     * Adds the noice generated by other motes, and breaks up a transmission if
     * the noise level is too high. Also checks if the noise is low enough to
     * hear incomming messages or not.
     *
     * @param level
     *            the level of noise
     * @param stream
     *            a reference to the incomming message
     */
    protected void addNoise(double level, Object stream) {

        if (receiving) {
            noiseStrength += level;
            if (isMessageCorrupted(signalStrength, noiseStrength)) {
                corrupted = true;
            }
        } else {
            if (!transmitting && isReceivable(level, noiseStrength)) {
                // start receiving
                getNode().setParentNode((Node) stream);
                parentID = ((Node) stream).getId();
                receiving = true;
                corrupted = false;
                signalStrength = level;
            } else {
                if (isDebugEnabled()) {
                    System.out.println(getSimData() + " - Transmiting a message cannot receive");
                }
                noiseStrength += level;
            }
        }
    }

    /**
     * Removes the noise, if a transmission is over, though if the source is the
     * sender of the message being transmitted there is some post processing
     * accordingly, the addressed application is notified about the incomming
     * message.
     *
     * @param stream
     *            a reference to the incomming messagethe incomming message
     * @param level
     *            the level of noise
     */
    protected void removeNoise(double level, final Object stream) {
        // guarda o ID e compara como o que recebeu no inicio da recepção
        if (parentID == ((Node) stream).getId()) {
            receiving = false;
            if (!corrupted) {
                final Node node = Node.cast(stream);
                getNode().getTransceiver().executeReception(new EnergyConsumptionAction() {

                    Message m = (Message) node.getMessage();

                    public void execute() {
                        deliverMessage(m);
                    }

                    public int getNumberOfUnits() {
                        return m.size();
                    }
                });
            } else {
                totalMessagesCorrupted++;
                if (isDebugEnabled()) {
                    System.out.println(getSimData() + " - Corrupted message: " + totalMessagesCorrupted);
                }
            }

            signalStrength = 0;
            if (sendingPostponed) {
                sendingPostponed = false;
                testChannelEvent.setTime(getNode().getSimulator().getSimulationTime()
                        + generateWaitingTime());
                getNode().getSimulator().addEvent(testChannelEvent);
            }
            parentID = -1;
        } else {
            noiseStrength -= level;
        }

    }
}
