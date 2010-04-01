/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.broadcast;

import org.mei.securesim.core.nodes.factories.NodeFactory;

/**
 *
 * @author POSilva
 */
public class BroadcastNodeFactory extends NodeFactory{

    @Override
    public void setup() {
        setRoutingLayerClass(BroadcastRoutingLayer.class);
        setApplicationClass(BroadcastApplication.class);
        setNodeClass(BroadcastNode.class);
    }
}
