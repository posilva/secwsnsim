/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.temp.flooding;

import org.wisenet.simulator.core.node.layers.mac.Mica2MACLayer;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class FloodingNodeFactory extends AbstractNodeFactory {

    public void setup() {

        setApplicationClass(HelloApplication.class);
        setRoutingLayerClass(FloodingRoutingLayer.class);
        setNodeClass(FloodingNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }
}
