/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.protocols.flooding;

import org.wisenet.simulator.core.layers.mac.Mica2MACLayer;
import org.wisenet.simulator.core.nodes.factories.NodeFactory;

/**
 *
 * @author posilva
 */
public class FloodingNodeFactory extends NodeFactory {

    public void setup() {

        setApplicationClass(HelloApplication.class);
        setRoutingLayerClass(FloodingRoutingLayer.class);
        setNodeClass(FloodingNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }
}
