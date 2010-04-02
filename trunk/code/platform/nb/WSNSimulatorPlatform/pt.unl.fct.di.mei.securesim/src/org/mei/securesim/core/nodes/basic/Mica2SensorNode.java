/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.core.nodes.basic;

import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.radio.RadioModel;

/**
 *
 * @author posilva
 */
public abstract class Mica2SensorNode extends SensorNode {

    public Mica2SensorNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
    }

    @Override
    public void configureMACLayer(RadioModel radioModel) {

        if (macLayer == null ) throw  new IllegalStateException("MacLayer cannot be null");
        getMacLayer().setNode(this);
        getMacLayer().setRadioModel(radioModel);
        getMacLayer().setNeighborhood(radioModel.createNeighborhood());
    }

    @Override
    public void init() {
       super.init();
    }

}
