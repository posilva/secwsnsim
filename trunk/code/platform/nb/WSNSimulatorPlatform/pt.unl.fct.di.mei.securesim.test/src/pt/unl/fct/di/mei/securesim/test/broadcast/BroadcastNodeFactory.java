/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.unl.fct.di.mei.securesim.test.broadcast;

import pt.unl.fct.di.mei.securesim.core.Simulator;
import pt.unl.fct.di.mei.securesim.network.nodes.NodeFactory;

/**
 *
 * @author POSilva
 */
public class BroadcastNodeFactory extends NodeFactory{

    public BroadcastNodeFactory(Simulator simulator, Class classOfNodes, Class application, Class routingLayer) {
        super(simulator, classOfNodes, application, routingLayer);
    }


    public BroadcastNodeFactory(Simulator simulator) {
        super(simulator);
        this.routingLayer= BroadcastRoutingLayer.class;
        this.classOfNodes=BroadcastNode.class;
        this.application=BroadcastApplication.class;
    }
}
