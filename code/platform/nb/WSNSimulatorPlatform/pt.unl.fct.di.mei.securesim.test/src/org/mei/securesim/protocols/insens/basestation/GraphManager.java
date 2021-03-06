/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.basestation;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import org.mei.securesim.protocols.insens.basestation.dijkstra.model.Edge;

/**
 *
 * @author CIAdmin
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


    public Vector<Short> getVertices() {
        return vertices;
    }

    void clear() {
        vertices.clear();
        edges.clear();
    }

}
