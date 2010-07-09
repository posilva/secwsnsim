/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens;

import org.mei.securesim.core.layers.mac.Mica2MACLayer;
import org.mei.securesim.core.nodes.factories.NodeFactory;

/**
 *
 * @author pedro
 */
public class INSENSNodeFactory extends NodeFactory {

    public INSENSNodeFactory() {
    }

    @Override
    public void setup() {
        setApplicationClass(EvaluateINSENSApplication.class);
        setRoutingLayerClass(INSENSRoutingLayer.class);
        setNodeClass(INSENSNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }
}
