/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.aodv;

import org.mei.securesim.core.nodes.factories.NodeFactory;

/**
 *
 * @author POSilva
 */
public class AODVNodeFactory extends NodeFactory{

    @Override
    public void setup() {
        setRoutingLayerClass(AODVRoutingLayer.class);
        setApplicationClass(AODVApplication.class);
        setNodeClass(AODVNode.class);
    }
}
