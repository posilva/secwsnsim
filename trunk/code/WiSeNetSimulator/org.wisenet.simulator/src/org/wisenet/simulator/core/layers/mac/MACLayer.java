package org.wisenet.simulator.core.layers.mac;

import org.wisenet.simulator.core.layers.Layer;
import org.wisenet.simulator.core.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.nodes.Node;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.core.radio.RadioModel.Neighborhood;

/**
 *
 * @author posilva
 */
public abstract class MACLayer extends Layer {
   
    protected static long totalMessagesSent = 0;
    protected static long totalMessagesNotSent = 0;
    protected static long totalMessagesCorrupted = 0;
    RadioModel radioModel;
    RadioModel.Neighborhood neighborhood;
    // //////////////////////////////
    // STATE VARIABLES
    // //////////////////////////////
    /**
     * State variable, true if radio is in sending mode, this means it has a one
     * message long buffer, which is full and the Node is trying to transmit its
     * content.
     */
    protected boolean sending = false;
    /** State variable, true if the radio is transmitting a message right now. */
    protected boolean transmitting = false;
    /** State variable, true if the radio is in receiving mode */
    protected boolean receiving = false;
    /** State variable, true if the last received message got corrupted by noise */
    protected boolean corrupted = false;
    protected double noiseStrength;
    protected double signalStrength;

    public boolean isCorrupted() {
        return corrupted;
    }

    public void setCorrupted(boolean corrupted) {
        this.corrupted = corrupted;
    }

    public boolean isReceiving() {
        return receiving;
    }

    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }

    public boolean isSending() {
        return sending;
    }

    public void setSending(boolean sending) {
        this.sending = sending;
    }

    public boolean isTransmitting() {
        return transmitting;
    }

    public void setTransmitting(boolean transmitting) {
        this.transmitting = transmitting;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public RadioModel getRadioModel() {
        return radioModel;
    }

    public void setRadioModel(RadioModel radioModel) {
        this.radioModel = radioModel;
    }

    
    protected final void beginTransmission(final double strength, Object stream) {
        Node n = (Node) stream;
        neighborhood.beginTransmission(strength, n);
//        final Node nd = Node.cast(stream);
//        getNode().getTransceiver().executeTransmission(new EnergyConsumptionAction() {
//
//            Node n = nd;
//
//            public void execute() {
//                neighborhood.beginTransmission(strength, n);
//            }
//
//            public int getNumberOfUnits() {
//                return ((DefaultMessage) n.getMessage()).size();
//            }
//        });

    }

    /**
     * Called by the derived class implementing the MAC layer when radio
     * transmission is finished. This method will call the
     * {@link Node#receptionEnd} method in each of the neighboring nodes with
     * the same <code>stream</code> object but with a diminished radio strength.
     * Derived classes must make sure that this method is invoked only once for
     * each matching {@link Node#beginTransmission} call.
     */
    protected final void endTransmission() {
        neighborhood.endTransmission();
    }

    /**
     * Called for each transmission of a neighboring node by the radio model.
     * The <code>recpetionBegin</code> and <code>receptionEnd</code> calles can
     * be nested or interleaved, but they are always coming in pairs. The
     * derived class implementing the MAC protocol must select the transmission
     * that it wants to receive based on some heuristics on the radio signal
     * stregths. Note that these methods are called even when the nodes is
     * currently transmitting.
     *
     * @param strength
     *            The radio strength of the incoming signal.
     * @param stream
     *            The object representing the incoming data. This stream object
     *            is never <code>null</code>.
     * @see #receptionEnd
     */
    public abstract void receptionBegin(double strength, Object stream);

    /**
     * Called for each transmission of a neighboring node by the radio model.
     * This method is always invoked after a corresponding
     * {@link #receptionBegin} method invokation with the exact same parameters.
     *
     * @param strength
     *            The radio strength of the incoming signal.
     * @param stream
     *            The received object message.
     */
    public abstract void receptionEnd(double strength, Object stream);

    /*
     * (non-Javadoc)
     *
     * @see net.tinyos.prowler.INode#sendMessage(java.lang.Object,
     * net.tinyos.prowler.Application)
     */
    public abstract boolean sendMessage(Object message, RoutingLayer layer);

    /**
     * initialize procedure
     */
    public abstract void init();

    public static long getTotalMessagesCorrupted() {
        return totalMessagesCorrupted;
    }

    public static long getTotalMessagesNotSent() {
        return totalMessagesNotSent;
    }

    public static long getTotalMessagesSent() {
        return totalMessagesSent;
    }

    public double getNoiseStrength() {
        return noiseStrength;
    }

    public void setNoiseStrength(double noiseStrength) {
        this.noiseStrength = noiseStrength;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
    }

  

}
