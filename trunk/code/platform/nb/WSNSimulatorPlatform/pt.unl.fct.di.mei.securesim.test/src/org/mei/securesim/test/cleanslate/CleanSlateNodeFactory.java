/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.cleanslate;

import org.mei.securesim.core.layers.mac.Mica2MACLayer;
import org.mei.securesim.core.nodes.factories.NodeFactory;

/**
 *
 * @author Pedro Marques da Silva
 */
public class CleanSlateNodeFactory extends NodeFactory{

    @Override
    public void setup() {
        setApplicationClass(CleanSlateApplication.class);
        setRoutingLayerClass(CleanSlateRoutingLayer.class);
        setNodeClass(CleanSlateNode.class);
        setMacLayer(Mica2MACLayer.class);
        setSetup(true);
    }
}
