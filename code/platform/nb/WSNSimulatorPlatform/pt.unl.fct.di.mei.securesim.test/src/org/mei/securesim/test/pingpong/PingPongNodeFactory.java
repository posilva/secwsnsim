/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.pingpong;

import org.mei.securesim.core.layers.mac.Mica2MACLayer;
import org.mei.securesim.core.nodes.factories.NodeFactory;
import org.mei.securesim.test.pingpong.PingPongApplication;
import org.mei.securesim.test.pingpong.PingPongNode;
import org.mei.securesim.test.pingpong.PingPongRoutingLayer;

/**
 *
 * @author posilva
 */
public class PingPongNodeFactory extends NodeFactory{

  

    public void setup() {
        setApplicationClass(PingPongApplication.class);
        setRoutingLayerClass(PingPongRoutingLayer.class);
        setNodeClass(PingPongNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }

}
