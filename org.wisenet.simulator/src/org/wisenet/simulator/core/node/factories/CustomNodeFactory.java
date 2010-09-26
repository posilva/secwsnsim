/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.factories;

import org.wisenet.simulator.core.Simulator;

/**
 * This class its a tool for customize a factory setting the
 * layers of a node
 * EXAMPLE:
 *    CustomNodeFactory cnf = new CustomNodeFactory();
 *    cnf.setRoutingLayerClass(routingClass);
 *    cnf.setMACLayerClass(macClass);
 *    cnf.setApplicationLayerClass(appClass);
 *    cnf.setEnergyModel(energyModel);
 * 
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class CustomNodeFactory extends AbstractNodeFactory {

    public CustomNodeFactory() {
        super();
    }

    public CustomNodeFactory(Simulator s) {
        super(s);
    }

    @Override
    public void setup() {
        // this method is a dummy method because the setup must be handled
        // using setters and getters
        setApplicationClass(getApplication());
        setRoutingLayerClass(getRoutingLayer());
        setMacLayer(getMacLayer());
        setEnergyModel(getEnergyModel());
        setup = true;
    }
}
