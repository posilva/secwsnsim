/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.unl.fct.di.mei.securesim.network.nodes.basic;

import pt.unl.fct.di.mei.securesim.core.Simulator;
import pt.unl.fct.di.mei.securesim.core.layers.Mica2MACLayer;
import pt.unl.fct.di.mei.securesim.core.radio.RadioModel;
import pt.unl.fct.di.mei.securesim.network.nodes.SensorNode;

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

        setMacLayer(new Mica2MACLayer());
        getMacLayer().setNode(this);
        getMacLayer().setRadioModel(radioModel);
        getMacLayer().setNeighborhood(radioModel.createNeighborhood());
    }

    @Override
    public void init() {
    
    }

}
