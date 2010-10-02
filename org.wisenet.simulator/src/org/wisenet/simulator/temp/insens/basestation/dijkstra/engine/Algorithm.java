package org.wisenet.simulator.temp.insens.basestation.dijkstra.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.wisenet.simulator.temp.insens.basestation.dijkstra.model.Edge;
import org.wisenet.simulator.temp.insens.basestation.dijkstra.model.Graph;

public class Algorithm {

    private final List<Short> nodes;
    private final List<Edge> edges;
    private Set<Short> settledNodes;
    private Set<Short> unSettledNodes;
    private Map<Short, Short> predecessors;
    private Map<Short, Integer> distance;

    public Algorithm(Graph graph) {
        this.nodes = new ArrayList<Short>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Short source) {
        settledNodes = new HashSet<Short>();
        unSettledNodes = new HashSet<Short>();
        distance = new HashMap<Short, Integer>();
        predecessors = new HashMap<Short, Short>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Short node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Short node) {
        List<Short> adjacentNodes = getNeighbors(node);
        for (Short target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Short node, Short target) {
        for (Edge edge : edges) {
            if ((edge.getSource() == node) && (edge.getDestination() == target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Short> getNeighbors(Short node) {
        List<Short> neighbors = new ArrayList<Short>();
        for (Edge edge : edges) {
            if ((edge.getSource() == node) && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Short getMinimum(Set<Short> vertexes) {
        Short minimum = null;
        for (Short vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Short vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Short destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Short> getPath(Short target) {
        LinkedList<Short> path = new LinkedList<Short>();
        Short step = target;
        // Check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
