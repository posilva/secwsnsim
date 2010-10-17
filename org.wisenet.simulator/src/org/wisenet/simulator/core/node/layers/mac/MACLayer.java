package org.wisenet.simulator.core.node.layers.mac;

import org.wisenet.simulator.core.node.layers.Layer;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.core.radio.RadioModel.Neighborhood;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class MACLayer extends Layer {

    /**
     *
     */
    protected static MACLayerController controller = new MACLayerController();
    RadioModel radioModel;
    RadioModel.Neighborhood neighborhood;
    /**
     *
     */
    protected static boolean controllerUpdated = false;
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
    /**
     *
     */
    protected double noiseStrength;
    /**
     *
     */
    protected double signalStrength;

    /**
     *
     */
    public MACLayer() {
        init();
    }

    /**
     *
     * @return
     */
    public boolean isCorrupted() {
        return corrupted;
    }

    /**
     *
     * @param corrupted
     */
    public void setCorrupted(boolean corrupted) {
        this.corrupted = corrupted;
    }

    /**
     *
     * @return
     */
    public boolean isReceiving() {
        return receiving;
    }

    /**
     *
     * @param receiving
     */
    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }

    /**
     *
     * @return
     */
    public boolean isSending() {
        return sending;
    }

    /**
     *
     * @param sending
     */
    public void setSending(boolean sending) {
        this.sending = sending;
    }

    /**
     *
     * @return
     */
    public boolean isTransmitting() {
        return transmitting;
    }

    /**
     *
     * @param transmitting
     */
    public void setTransmitting(boolean transmitting) {
        this.transmitting = transmitting;
    }

    /**
     *
     * @return
     */
    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    /**
     *
     * @param neighborhood
     */
    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     *
     * @return
     */
    public RadioModel getRadioModel() {
        return radioModel;
    }

    /**
     *
     * @param radioModel
     */
    public void setRadioModel(RadioModel radioModel) {
        this.radioModel = radioModel;
    }

    /**
     *
     * @param strength
     * @param stream
     */
    protected final void beginTransmission(final double strength, Object stream) {
        Node n = (Node) stream;
        neighborhood.beginTransmission(strength, n);
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
    /**
     *
     * @param message
     * @param layer
     * @return
     */
    public abstract boolean sendMessage(Object message, RoutingLayer layer);

    /**
     * Apply the battery consumption attenuation to the radio signal
     * @param signal
     * @return signal value after attenuation
     */
    public abstract double applyBatterySignalAttenuation(double signal);

    /**
     * Apply the environment attenuation to the radio signal
     * (the humidity is a environment variable that can influence
     * the signal strength )
     * @param signal
     * @return signal value after attenuation
     */
    public abstract double applyEnvironmentSignalAttenuation(double signal);

    /**
     *
     * @return
     */
    public static long getTotalMessagesCorrupted() {
        return controller.getTotalMessagesCorrupted();
    }

    /**
     *
     * @return
     */
    public static long getTotalMessagesNotSent() {
        return controller.getTotalMessagesNotSent();
    }

    /**
     *
     * @return
     */
    public static long getTotalMessagesSent() {
        return controller.getTotalMessagesSent();
    }

    /**
     *
     * @return
     */
    public double getNoiseStrength() {
        return noiseStrength;
    }

    /**
     *
     * @param noiseStrength
     */
    public void setNoiseStrength(double noiseStrength) {
        this.noiseStrength = noiseStrength;
    }

    /**
     *
     * @return
     */
    public double getSignalStrength() {
        return signalStrength;
    }

    /**
     *
     * @param signalStrength
     */
    public void setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
    }

    /**
     *
     * @return
     */
    public static MACLayerController getController() {
        return controller;
    }

    private void init() {
        setupLayerParameters();
    }

    /**
     *
     */
    @Override
    public void reset() {
        super.reset();
        receiving = false;
        sending = false;
        corrupted = false;
        transmitting = false;
    }

    /**
     * Sets the common parameters for the mac layer
     */
    protected abstract void setupLayerParameters();
}
