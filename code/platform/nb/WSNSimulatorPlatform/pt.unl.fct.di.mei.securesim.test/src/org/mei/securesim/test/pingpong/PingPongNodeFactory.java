/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.pingpong;

import org.mei.securesim.core.factories.NodeFactory;

/**
 *
 * @author posilva
 */
public class PingPongNodeFactory extends NodeFactory{

  

    public void setup() {
        setApplicationClass(PingPongApplication.class);
        setRoutingLayerClass(PingPongRoutingLayer.class);
        setNodeClass(PingPongNode.class);

    }

}
