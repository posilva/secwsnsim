/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens;

import org.mei.securesim.core.layers.mac.Mica2MACLayer;
import org.mei.securesim.core.nodes.factories.NodeFactory;

/**
 *
 * @author pedro
 */
//org.mei.securesim.test.insens.INSENSNodeFactory
public class INSENSNodeFactory extends NodeFactory{

    @Override
    public void setup() {
        setApplicationClass(INSENSApplication.class);
        setRoutingLayerClass(INSENSRoutingLayer.class);
        setNodeClass(INSENSNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }

}
