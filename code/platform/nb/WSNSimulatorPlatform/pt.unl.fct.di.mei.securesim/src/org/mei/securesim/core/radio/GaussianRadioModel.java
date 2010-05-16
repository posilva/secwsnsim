/*
 * Copyright (c) 2003, Vanderbilt University
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for any purpose, without fee, and without written agreement is
 * hereby granted, provided that the above copyright notice, the following
 * two paragraphs and the author appear in all copies of this software.
 * 
 * IN NO EVENT SHALL THE VANDERBILT UNIVERSITY BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
 * OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE VANDERBILT
 * UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * THE VANDERBILT UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
 * ON AN "AS IS" BASIS, AND THE VANDERBILT UNIVERSITY HAS NO OBLIGATION TO
 * PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 *
 * Author: Gyorgy Balogh, Gabor Pap, Miklos Maroti
 * Date last modified: 02/09/04
 */
package org.mei.securesim.core.radio;

import java.util.ArrayList;
import java.util.HashSet;

import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.utils.DebugUtils;

/**
 * This radio model uses the assumption that nodes are mainly static, they don't
 * change their positions to often. So it modulates the ideal radio strength
 * first when the {@link RadioModel#updateNeighborhoods} is called adding a
 * Gaussian noise to it, then it is further modulated on a per-transmission
 * basis again adding a Gaussian noise.
 * 
 * @author Gabor Pap, Gyorgy Balogh, Miklos Maroti
 */
public class GaussianRadioModel extends RadioModel {

    private static final double DEFAULT_DYNAMIC_RANDOM_FACTOR = 0.05;
    private static final double DEFAULT_RADIO_STRENGTH_CUTOFF = 0.1;
    private static final double DEFAULT_STATIC_RANDOM_FACTOR = 0.3;
    private static final double DEFAULT_FALLING_FACTOR_HALF = 1.1;
    /**
     * The exhibitor of the radio signal degradation, typically it is 2/2 if the
     * motes are well above ground, though at ground level this factor is closer
     * to 3/2. For efficiency reasons, this number is the half of the usually
     * defined falling factor.
     */
    public static double fallingFactorHalf = DEFAULT_FALLING_FACTOR_HALF;
    /**
     * This coefficient is used to "simulate" the static part of environmental
     * noise. It is used when the mote field is first set up.
     */
    public static double staticRandomFactor = DEFAULT_STATIC_RANDOM_FACTOR;
    /**
     * This limits the number of neighbours used to calculate interference
     * ratio. Using neighborhood instead of endless radio signals makes the
     * simulation somewhat faster.
     */
    public static double radioStrengthCutoff = DEFAULT_RADIO_STRENGTH_CUTOFF;
    /**
     * This coefficient is used to "simulate" the dynamic part of environmental
     * noise. The dynamic noise is recalculated for each transmission.
     */
    public double dynamicRandomFactor = DEFAULT_DYNAMIC_RANDOM_FACTOR;
    /**
     * 
     */
    private double maxDistance = 0;

    /**
     * A parameterized constructor used to set the simulator at creation time.
     *
     * @param sim
     *            a reference to the simulator
     */
    public GaussianRadioModel(Simulator sim) {
        setSimulator(sim);
    }

    /**
     *
     */
    public GaussianRadioModel() {
        super();
    }

    /**
     * (Re)calculates the neighborhoods of every node in the network. This
     * operation should be called whenever the location of the nodes changed.
     * This operation is extremely expensive and should be used sparsely.
     */
    public void updateNeighborhoods() {
        /**
         * Clear neighborhood information
         */
        for (Node srcNode : getSimulator().getNodes()) {
            srcNode.getMacLayer().getNeighborhood().neighbors.clear();
            srcNode.getMacLayer().getNeighborhood().neighborsSet.clear();
            srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMe.clear();
            srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMeSet.clear();
        }
        /**
         * Create neighborhood 
         */
        for (Node srcNode : getSimulator().getNodes()) {
            Neighborhood neighborhood = (Neighborhood) srcNode.getMacLayer().getNeighborhood();
            for (Node dstNode : getSimulator().getNodes()) {
                if (srcNode != dstNode) {
                    double staticRadioStrength = getStaticFading(srcNode, dstNode);
                    if (staticRadioStrength >= radioStrengthCutoff) {
                        final double d = srcNode.getDistance(dstNode);
                        maxDistance = (Math.max(d, maxDistance));
                        neighborhood.neighbors.add(dstNode);
                        dstNode.getMacLayer().getNeighborhood().neighborsThatKnowMe.add(srcNode);
                        neighborhood.staticFadings.add(staticRadioStrength);
                    }
                }
            }
            for (int i = 0; i < neighborhood.neighbors.size(); i++) {
                // TODO Optimizar
                neighborhood.dynamicStrengths.add(0.0);
            }
        }
        /**
         * Create direct access structures
         */
        for (Node srcNode : getSimulator().getNodes()) {
            srcNode.getMacLayer().getNeighborhood().neighborsSet = new HashSet(srcNode.getMacLayer().getNeighborhood().neighbors);
            srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMeSet = new HashSet(srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMe);
        }

    }

    /**
     * This is a factory method for creating radio model specific neigborhoods.
     */
    public RadioModel.Neighborhood createNeighborhood() {
        return new Neighborhood();
    }

    /**
     * Calculates the static part of the radio fading between two nodes based on
     * distance and a randomGenerator factor.
     *
     * @return The radio fading coefficient
     */
    protected double getStaticFading(Node sender, Node receiver) {
        double staticRandomFading = 1.0 + staticRandomFactor
                * Simulator.randomGenerator.random().nextGaussian();
        final double distanceSquare = sender.getDistanceSquare(receiver);
        return staticRandomFading <= 0.0 ? 0.0 : sender.getConfig().getMaximumRadioStrength() * staticRandomFading
                / (1.0 + Math.pow(distanceSquare, fallingFactorHalf));
    }

    /**
     * Calculates the received radio strength based on the static signal
     * strength determined by the {@link GaussianRadioModel#getStaticFading}, a
     * dynamic randomGenerator factor and the signal strength of the node, which can be
     * time variant.
     *
     * @param signalStrength
     *            the signal strength of the sender node
     * @param staticFading
     *            the static fading as returned by
     *            {@link GaussianRadioModel#getStaticFading}.
     * @return The signal strength at the receiver.
     */
    protected double getDynamicStrength(double signalStrength, double staticFading) {
        double dynamicRandomFading = 1.0 + dynamicRandomFactor * Simulator.randomGenerator.random().nextGaussian();
        return dynamicRandomFading <= 0.0 ? 0.0 : signalStrength * staticFading * dynamicRandomFading;
    }

    /**
     * This class stores all the node related data the GaussianRadioModel needs,
     * this includes an array of neighboring notes, the static fading and the
     * dynamic strentgh as well for every neighboring nodes plus the entity
     * being transmitted by the node.
     */
    public class Neighborhood extends RadioModel.Neighborhood {

        /**
         * The vector of static fading factors. These numbers shall be in the
         * <code>[0,1]</code> interval.
         */
        protected ArrayList<Double> staticFadings = new ArrayList<Double>();
        /**
         * This vector holds the signal strength values that we contributed to
         * the neighbours in the last transmission. We must keep these values
         * because we have to use the same strengths for the matching
         * {@link Node#receptionEnd} call.
         */
        protected ArrayList<Double> dynamicStrengths = new ArrayList<Double>();
        /**
         * Contains the stream object during an active transmission, or
         * <code>null</code> if we do not transmit.
         */
        protected Object stream = null;

        public ArrayList<Double> getDynamicStrengths() {
            return dynamicStrengths;
        }

        public ArrayList<Double> getStaticFadings() {
            return staticFadings;
        }

        public ArrayList<Node> getNeighbors() {
            return neighbors;
        }

        public HashSet<Node> getNeighborsSet() {
            return neighborsSet;
        }

        public ArrayList<Node> getNeighborsThatKnowMe() {
            return neighborsThatKnowMe;
        }

        public HashSet<Node> getNeighborsThatKnowMeSet() {
            return neighborsThatKnowMeSet;
        }

        Node getStream2Node() {
            return (Node) this.stream;
        }

        /**
         * Calculates the dynamic signal strength based on the static fading
         * factors and a per-transmission dynamic randomGenerator factor. Then calls the
         * {@link Node#receptionBegin} method on all neighbors.
         */
        public synchronized void beginTransmission(double strength, Object stream) {
            if (stream == null) {
                throw new IllegalArgumentException("The stream object must be non-null");
            } else if (this.stream != null) {
                throw new IllegalStateException("No nested transmissions are allowed");
            }

            DebugUtils.debugMessage("beginTransmission", stream);

            this.stream = stream;

            int i = neighbors.size();
//            Node node = (Node) stream;
            while (--i >= 0) {
                if (neighbors.get(i).isTurnedOn()) {
                    double dynamicStrength = getDynamicStrength(strength, staticFadings.get(i));
//                    if (dynamicStrengths.get(i) == null) {
//                        dynamicStrengths.add(dynamicStrength);
//                    }
                    dynamicStrengths.set(i, dynamicStrength);
//                    System.out.println("Send >"+ node.getId()  + ": "+ node.getMessage() + " to  "+ neighbors.get(i).getId());
                    neighbors.get(i).getMacLayer().receptionBegin(dynamicStrength, stream);
                }
            }
        }

        /**
         * Calls the {@link Node#receptionEnd} method on all neighboring nodes.
         * This method should always be called as the pair of the
         * {@link RadioModel.Neighborhood#beginTransmission} method.
         */
        public void endTransmission() {
            DebugUtils.debugMessage("endTransmission", stream);
            int i = neighbors.size();
            while (--i >= 0) {
                neighbors.get(i).getMacLayer().receptionEnd(dynamicStrengths.get(i), stream);
            }
            stream = null;
        }
        /**
         *
         * @param source
         * @param msg
         */
    }
}
