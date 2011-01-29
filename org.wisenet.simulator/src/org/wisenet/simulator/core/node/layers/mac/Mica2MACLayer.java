/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.mac;

import org.wisenet.simulator.core.energy.EnergyConsumptionAction;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.Event;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class Mica2MACLayer extends MACLayer {

    /**
     *
     */
    public short parentID = -1;
    /**
     * In this simulation not messages but references to motes are passed. All
     * this means is that the Mica2MACLayer has to hold the information on the
     * sender application which runs on this very mote.
     */
    RoutingLayer senderRoutingLayer;
    /**
     * State variable, true if radio failed to transmit a message do to high
     * radio traffic, this means it has to retry it later, which is done using
     * the {@link Mica2MACLayer#generateBackOffTime} function.
     */
    protected boolean sendingPostponed = false;
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
     * Parameters for this MACLayer implementation
     */
    private MACLayerParameters parameters = new Mica2MACLayerParameters();

    /**
     *
     * @param message
     * @return
     */
    public boolean deliverMessage(Object message) {
        setMessageColor(message);
        /**
         * Deliver the message to the handler we can intercept this handler to
         * notify controllers
         */
        getNode().getRoutingLayer().receivedMessageHandler(message);
        return true;
    }

    private void setMessageColor(Object message) {
        Message m = (Message) message;
        if (m.isShowColor()) {
            getNode().setMessageColor(m.getColor());
        }
    }

    private double applySignalAttenuation(double signalStrength) {
        return applyBatterySignalAttenuation(applyEnvironmentSignalAttenuation(signalStrength));
    }

    @Override
    public double applyBatterySignalAttenuation(double signal) {
        return getNode().getBateryEnergy().getDrainFunction(signal);
    }

    @Override
    public double applyEnvironmentSignalAttenuation(double signal) {
        double percent = getNode().getEnvironmentAttenuation();
        double v = signal * percent / 100;
        return signal - v;
    }

    /**
     *
     */
    protected void createParameters() {

        getController().setParameters(new Mica2MACLayerParameters());
    }

    @Override
    protected void setupLayerParameters() {
        if (!controllerUpdated) {
            getController().setParameters(new Mica2MACLayerParameters());
        }
        controllerUpdated = true;
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

                endTransmissionEvent.setTime(getTime() + (Integer) getController().getParameters().get("sendTransmissionTime"));
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
        // inicio da recepÃ§Ã£o, pode-se verificar o estado do nÃ³ e
        // caso o TX esteja ON recebe senÃ£o aborta
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
        controller.incrementTotalMessagesSent();
        if (sending) {
            controller.incrementTotalMessagesNotSent();
            log(" Messages not Sent: " + controller.getTotalMessagesNotSent());
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
                testChannelEvent.setTime(Simulator.getSimulationTime()
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
    public int generateWaitingTime() {
        return (Integer) getController().getParameters().get("sendMinWaitingTime")
                + (int) (Simulator.randomGenerator.random().nextDouble() * (Integer) getController().getParameters().get("sendRandomWaitingTime"));
    }

    /**
     * Generates a backoff time, adding a randomGenerator variable time to a constant
     * minimum.
     *
     * @return returns the backoff time in milliseconds
     */
    protected int generateBackOffTime() {
        return (Integer) getController().getParameters().get("sendMinBackOffTime")
                + (int) (Simulator.randomGenerator.random().nextDouble() * (Integer) getController().getParameters().get("sendRandomBackOffTime"));
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
        return noiseStrength < (Double) getController().getParameters().get("maxAllowedNoiseOnSending") * (Double) getController().getParameters().get("noiseVariance");
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
        return calcSNR(signal, noise) < (Double) parameters.get("corruptionSNR");
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
        signal = applySignalAttenuation(signal);
        return signal / ((Double) getController().getParameters().get("noiseVariance") + noise);
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
        return calcSNR(signal, noise) > (Double) getController().getParameters().get("receivingStartSNR"); // PMS
    }

    /**
     *
     */
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
                getController().incrementTotalMessagesNotReceived();
                log(" - Transmiting a message cannot receive");
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
        // guarda o ID e compara como o que recebeu no inicio da recepÃ§Ã£o
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
                controller.incrementTotalMessagesCorrupted();
                log("Corrupted message: " + controller.getTotalMessagesCorrupted());
            }

            signalStrength = 0;
            if (sendingPostponed) {
                sendingPostponed = false;
                testChannelEvent.setTime(Simulator.getSimulationTime()
                        + generateWaitingTime());
                getNode().getSimulator().addEvent(testChannelEvent);
            }
            parentID = -1;
        } else {
            noiseStrength -= level;
        }
    }

    /**
     *
     */
    @Override
    public void reset() {
        super.reset();
        parentID = -1;
        noiseStrength = 0;
        signalStrength = 0;
        senderRoutingLayer = null;
        testChannelEvent = new TestChannelEvent();
        endTransmissionEvent = new EndTransmissionEvent();
    }
}
