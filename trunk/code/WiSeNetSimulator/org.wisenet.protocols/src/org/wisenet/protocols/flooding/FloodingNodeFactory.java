/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.protocols.flooding;

import org.wisenet.simulator.core.node.layers.mac.Mica2MACLayer;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;

/**
 *
 * @author posilva
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
