/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.protocols.flooding;

import org.mei.securesim.core.layers.mac.Mica2MACLayer;
import org.mei.securesim.core.nodes.factories.NodeFactory;
import org.mei.securesim.protocols.common.BasicNode;

/**
 *
 * @author posilva
 */
public class FloodingNodeFactory extends NodeFactory{

    public void setup() {
       
        setApplicationClass(HelloApplication.class);
        setRoutingLayerClass(FloodingRoutingLayer.class);
        setNodeClass(FloodingNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }

}
