/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.pingpong;

import org.mei.securesim.core.layers.mac.SecuredMica2MACLayer;
import org.mei.securesim.core.nodes.factories.NodeFactory;

/**
 *
 * @author posilva
 */
public class PingPongSecureNodeFactory extends NodeFactory{

  

    public void setup() {
        setApplicationClass(PingPongApplication.class);
        setRoutingLayerClass(PingPongRoutingLayer.class);
        setNodeClass(PingPongNode.class);
        setMacLayer(SecuredMica2MACLayer.class);
        setSetup(true);
    }

}
