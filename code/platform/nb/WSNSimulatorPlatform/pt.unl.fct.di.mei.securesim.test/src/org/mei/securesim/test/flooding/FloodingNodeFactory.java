/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.flooding;

import org.mei.securesim.core.layers.mac.Mica2MACLayer;
import org.mei.securesim.core.nodes.factories.NodeFactory;
import org.mei.securesim.test.common.BasicNode;
import org.mei.securesim.test.pingpong.PingPongApplication;

/**
 *
 * @author posilva
 */
public class FloodingNodeFactory extends NodeFactory{

  

    public void setup() {
        setApplicationClass(PingPongApplication.class);
        setRoutingLayerClass(FloodingRoutingLayer.class);
        setNodeClass(BasicNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }

}
