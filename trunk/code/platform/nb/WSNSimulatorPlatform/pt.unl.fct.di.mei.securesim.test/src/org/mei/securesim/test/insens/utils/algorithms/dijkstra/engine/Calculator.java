package org.mei.securesim.test.insens.utils.algorithms.dijkstra.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.mei.securesim.test.insens.utils.algorithms.dijkstra.model.Edge;
import org.mei.securesim.test.insens.utils.algorithms.dijkstra.model.Graph;

@SuppressWarnings("unchecked")
public class Calculator {

    private static Calculator instance;
    private List<Short> nodes = null;
    private List<Edge> edges = null;
    Graph graph;
    Algorithm dijkstra;

    /**
     *
     */
    public Calculator() {
        super();
        nodes = new ArrayList<Short>();
        edges = new ArrayList<Edge>();
    }

    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator();
        }
        return instance;
    }

    public void addVertexs(Collection vertexs) {
        nodes.addAll(vertexs);
    }

    public void addVertex(short id) {
        nodes.add(id);
    }

    public void addEdge(short v1, short v2) {
        int i1 = nodes.indexOf(v1);
        int i2 = nodes.indexOf(v2);
        edges.add(new Edge(nodes.get(i1), nodes.get(i2)));

    }

    public void addEdges(Collection<Edge> edges) {
        edges.addAll(edges);
    }

    public Calculator prepare() {
        if (nodes.size() == 0) {
            throw new RuntimeException("Nodes cannot be empty");
        }
        if (edges.size() == 0) {
            throw new RuntimeException("Edges cannot be empty");
        }
        graph = new Graph(nodes, edges);
        dijkstra = new Algorithm(graph);

        return instance;
    }

    public LinkedList calculatePath(short from, short to) {
        int i1 = nodes.indexOf(from);
        int i2 = nodes.indexOf(to);


        dijkstra.execute(nodes.get(i1));
        LinkedList<Short> path = dijkstra.getPath(nodes.get(i2));
        return path;
    }

    public Hashtable calculateAllPaths() {
        Hashtable pathsTable = new Hashtable();
        for (Iterator from = nodes.iterator(); from.hasNext();) {
            Short v1 = (Short) from.next();
            Hashtable pathsTo = new Hashtable();
            for (Iterator to = nodes.iterator(); to.hasNext();) {
                Short v2 = (Short) to.next();
                if (v1 != v2) {
                    LinkedList path = calculatePath(v1, v2);
                    pathsTo.put(v2, path);
                }
            }
            pathsTable.put(v1, pathsTo);
        }
        return pathsTable;
    }
}
