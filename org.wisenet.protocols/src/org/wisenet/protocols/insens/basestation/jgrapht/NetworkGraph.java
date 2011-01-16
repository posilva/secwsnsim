/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.basestation.jgrapht;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class NetworkGraph extends DefaultDirectedGraph<Short, DefaultEdge> {

    public NetworkGraph(Class type) {
        super(type);
    }

    public NetworkGraph(EdgeFactory<Short, DefaultEdge> ef) {
        super(ef);
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
