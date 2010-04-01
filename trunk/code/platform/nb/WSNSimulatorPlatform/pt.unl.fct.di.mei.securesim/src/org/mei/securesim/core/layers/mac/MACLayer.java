/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.layers.mac;

import org.mei.securesim.core.layers.Layer;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.core.radio.RadioModel.Neighborhood;

/**
 *
 * @author posilva
 */
public abstract class MACLayer extends Layer {

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

    protected final void beginTransmission(double strength, Object stream) {
        neighborhood.beginTransmission(strength, stream);
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
}
