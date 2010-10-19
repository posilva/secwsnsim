package org.wisenet.protocols.insens.basestation.dijkstra.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.wisenet.protocols.insens.basestation.dijkstra.model.Edge;
import org.wisenet.protocols.insens.basestation.dijkstra.model.Graph;

/**
 *
 * @author posilva
 */
@SuppressWarnings("unchecked")
public class Calculator {

    private static Calculator instance;
//    private List<Short> nodes = null;
    private Set<Short> nodes = null;
    private List<Edge> edges = null;
    Graph graph;
    Algorithm dijkstra;

    /**
     *
     */
    public Calculator() {
        super();
//        nodes = new ArrayList<Short>();
        nodes = new HashSet<Short>();
        edges = new ArrayList<Edge>();
    }

    /**
     *
     * @return
     */
    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator();
        }
        return instance;
    }

    /**
     *
     * @param vertexs
     */
    public void addVertexs(Collection vertexs) {
        nodes.addAll(vertexs);
    }

    /**
     *
     * @param id
     */
    public void addVertex(short id) {
        nodes.add(id);
    }

    /**
     *
     * @param v1
     * @param v2
     */
    public void addEdge(short v1, short v2) {
//        int i1 = nodes.indexOf(v1);
//        int i2 = nodes.indexOf(v2);
//        edges.add(new Edge(nodes.get(i1), nodes.get(i2)));
        edges.add(new Edge(v1, v2));
    }

    /**
     *
     * @param edges
     */
    public void addEdges(Collection<Edge> edges) {
        this.edges.addAll(edges);
    }

    /**
     *
     * @return
     * @throws CalculatorException
     */
    public Calculator prepare() throws CalculatorException {
        if (nodes.isEmpty()) {
            throw new CalculatorException("Nodes cannot be empty");
        }
        if (edges.isEmpty()) {
            throw new CalculatorException("Edges cannot be empty");
        }
//        graph = new Graph(nodes, edges);
        graph = new Graph(new LinkedList<Short>(nodes), edges);
        dijkstra = new Algorithm(graph);

        return instance;
    }

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public LinkedList calculatePath(short from, short to) {
//        int i1 = nodes.indexOf(from);
//        int i2 = nodes.indexOf(to);
//        dijkstra.execute(nodes.get(i1));
//        LinkedList<Short> path = dijkstra.getPath(nodes.get(i2));
        dijkstra.execute(from);
        LinkedList<Short> path = dijkstra.getPath(to);
        return path;
    }

    /**
     *
     * @param from
     * @param toSet
     * @return
     */
    public Hashtable calculateAllPathsFromTo(short from, Set toSet) {
        Short v1 = (Short) from;
        Hashtable pathsTo = new Hashtable();
        for (Object to : toSet) {
            Short v2 = (Short) to;
            if (toSet.contains(v2)) {
                if (v1 != v2) {
                    LinkedList path = calculatePath(v1, v2);
                    if (path != null) {
                        pathsTo.put(v2, path);
                    }
                }

            }
        }
        return pathsTo;
    }

    /**
     *
     * @param from
     * @return
     */
    public Hashtable calculateAllPathsFrom(short from) {

        Short v1 = (Short) from;
        Hashtable pathsTo = new Hashtable();
        for (Iterator to = nodes.iterator(); to.hasNext();) {
            Short v2 = (Short) to.next();
            if (v1 != v2) {
                LinkedList path = calculatePath(v1, v2);
                if (path != null) {
                    pathsTo.put(v2, path);
                }
            }
        }
        return pathsTo;
    }

    /**
     *
     * @return
     */
    public Hashtable calculateAllPaths() {
        Hashtable pathsTable = new Hashtable();
        for (Iterator from = nodes.iterator(); from.hasNext();) {
            Short v1 = (Short) from.next();
            Hashtable pathsTo = new Hashtable();
            for (Iterator to = nodes.iterator(); to.hasNext();) {
                Short v2 = (Short) to.next();
                if (v1 != v2) {
                    LinkedList path = calculatePath(v1, v2);
                    if (path != null) {
                        pathsTo.put(v2, path);
                    }
                }
            }
            pathsTable.put(v1, pathsTo);
        }
        return pathsTable;
    }
}
