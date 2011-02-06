/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens;

import org.wisenet.protocols.flooding.HelloApplication;
import org.wisenet.simulator.core.node.layers.mac.Mica2MACLayer;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;

/**
 *
 * @author pedro
 */
public class INSENSNodeFactory extends AbstractNodeFactory {

    /**
     *
     */
    public INSENSNodeFactory() {
    }

    @Override
    public void setup() {
        setApplicationClass(HelloApplication.class);
        setRoutingLayerClass(INSENSRoutingLayer.class);
        setNodeClass(INSENSNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }
}
