/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.basestation;

import java.util.HashSet;
import java.util.Vector;
import org.wisenet.protocols.insens.basestation.dijkstra.model.Edge;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class GraphManager {

    Vector<Short> vertices = new Vector<Short>();
    HashSet<Edge> edges = new HashSet<Edge>();

    void addEdge(Short edge1, Short edge2) {
        if (!vertices.contains(edge1)) {
            vertices.add(edge1);
        }
        if (!vertices.contains(edge2)) {
            vertices.add(edge2);
        }

        edges.add(new Edge(edge1, edge2));
    }


    /**
     *
     * @return
     */
    public Vector<Short> getVertices() {
        return vertices;
    }

    void clear() {
        vertices.clear();
        edges.clear();
    }

}
