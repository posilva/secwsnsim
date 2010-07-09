/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens;

import org.mei.securesim.protocols.insens.*;
import org.mei.securesim.core.layers.mac.Mica2MACLayer;
import org.mei.securesim.core.nodes.factories.NodeFactory;
import org.mei.securesim.test.insens.INSENSApplication;
import org.mei.securesim.test.insens.INSENSNode;
import org.mei.securesim.test.insens.INSENSRoutingLayer;

/**
 *
 * @author pedro
 */
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
