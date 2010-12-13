/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.basestation.jgrapht;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.wisenet.protocols.insens.basestation.dijkstra.model.Edge;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class DijkstraCalculator {

    protected static DijkstraCalculator instance = null;
    DirectedGraph<Short, DefaultEdge> directedGraph;

    public static DijkstraCalculator get() {
        if (instance == null) {
            instance = new DijkstraCalculator();
        }
        return instance;
    }

    public void addEdge(short v1, short v2) {
        directedGraph.addEdge(v1, v2);
    }

    public void addVertices(Collection<Short> colV) {
        for (Short v : colV) {
            directedGraph.addVertex(v);
        }
    }

    public DijkstraCalculator() {
        directedGraph = new DefaultDirectedGraph<Short, DefaultEdge>(DefaultEdge.class);
    }

    public List calculatePath(short from, short to) {
        try {
            List<Short> path = transform(DijkstraShortestPath.findPathBetween(directedGraph, from, to), directedGraph);
            return path;
        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }
        return null;
    }

    public Hashtable calculateAllPathsFromTo(short from, Set toSet) {
        Short v1 = (Short) from;
        Hashtable pathsTo = new Hashtable();
        for (Object to : toSet) {
            Short v2 = (Short) to;
            if (toSet.contains(v2)) {
                if (v1 != v2) {
                    List path = calculatePath(v1, v2);
                    if (path != null) {
                        pathsTo.put(v2, path);
                    }
                }

            }
        }
        return pathsTo;
    }

    public Hashtable calculateAllPathsFrom(short from) {

        Short v1 = (Short) from;
        Hashtable pathsTo = new Hashtable();
        for (Iterator to = directedGraph.vertexSet().iterator(); to.hasNext();) {
            Short v2 = (Short) to.next();
            if (v1 != v2) {
                List path = calculatePath(v1, v2);
                if (path != null) {
                    pathsTo.put(v2, path);
                }
            }
        }
        return pathsTo;
    }

    public Hashtable calculateAllPaths() {
        Hashtable pathsTable = new Hashtable();
        for (Iterator from = directedGraph.vertexSet().iterator(); from.hasNext();) {
            Short v1 = (Short) from.next();
            Hashtable pathsTo = new Hashtable();
            for (Iterator to = directedGraph.vertexSet().iterator(); to.hasNext();) {
                Short v2 = (Short) to.next();
                if (v1 != v2) {
                    List path = calculatePath(v1, v2);
                    if (path != null) {
                        pathsTo.put(v2, path);
                    }
                }
            }
            pathsTable.put(v1, pathsTo);
        }
        return pathsTable;
    }

    public void addEdges(HashSet<Edge> edges) {
        for (Edge edge : edges) {
            addEdge(edge.getSource(), edge.getDestination());
        }
    }

    public void addVertexs(Vector<Short> vertices) {
        for (Short v : vertices) {
            directedGraph.addVertex(v);
        }
    }

    public void prepare() {
    }

    private List<Short> transform(List<DefaultEdge> path, DirectedGraph<Short, DefaultEdge> graph) {
        ArrayList<Short> newList = new ArrayList<Short>();
        if (path.isEmpty()) {
            return newList;
        }
        for (DefaultEdge defaultEdge : path) {
            newList.add(graph.getEdgeSource(defaultEdge));
        }
        newList.add(graph.getEdgeTarget(path.get(path.size() - 1)));
        return newList;
    }
}
