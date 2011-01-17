/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.basestation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.util.VertexPair;
import org.wisenet.protocols.insens.basestation.jgrapht.NetworkGraph;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class PathsFinder {

    public static final int NUMBER_OF_THREADS_FOR_1ST_PATHS = 2;
    public static final int NUMBER_OF_THREADS_FOR_2ND_PATHS = 2;
    private NetworkGraph graph = null;
    private Short start = null;
    private Queue vertices = null;
    private List firstPaths = new ArrayList();
    private List otherPaths = new ArrayList();
    private Queue firstPathsQueue;

    public static synchronized Collection findPath(NetworkGraph graph, Short start, Short end) {
        try {
            return DijkstraShortestPath.findPathBetween(graph, start, end);
        } catch (Exception e) {
            Logger.getLogger(PathsFinder.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }

    public List findFirstPaths() {
        vertices = new ArrayDeque(graph.vertexSet());
        Thread[] workers = new Thread[NUMBER_OF_THREADS_FOR_1ST_PATHS];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(new FirstFinder());
            workers[i].start();
        }
        for (int i = 0; i < workers.length; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(PathsFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return firstPaths;
    }

    /**
     * Extract all vertices from edge path
     * @param graph_thread
     * @param path
     * @param start
     * @param end
     * @return
     */
    public static synchronized List extractVerticesFromEdges(NetworkGraph graph, List<DefaultEdge> path, Short start, short end) {
        List list = new ArrayList();
        if (path.isEmpty()) {
            return list;
        }
        Short curr = start;
        Short next = null;
        for (DefaultEdge defaultEdge : path) {
            VertexPair<Short> vp = new VertexPair<Short>(graph.getEdgeSource(defaultEdge), graph.getEdgeTarget(defaultEdge));
            next = vp.getOther(curr);
            list.add(curr);
            curr = next;
        }

        list.add(end);
        return list;
    }

    public List getPaths() {
        return firstPaths;
    }

    public Short getStart() {
        return start;
    }

    public Queue getVertices() {
        return vertices;
    }

    PathsFinder(NetworkGraph graph, short start) {
        this.graph = graph;
        this.start = start;

    }

    private synchronized Short getNextEnd() {
        return (Short) vertices.poll();
    }

    private synchronized void addToPaths(Collection path) {
        firstPaths.add(path);
    }

    public List findOtherPaths(List firstPaths) {
        firstPathsQueue = new ArrayDeque(firstPaths);
        Thread[] workers = new Thread[NUMBER_OF_THREADS_FOR_2ND_PATHS];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(new OtherFinder((NetworkGraph) graph.clone()));
            workers[i].start();
        }
        for (int i = 0; i < workers.length; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(PathsFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return otherPaths;
    }

//        for (List firstPath : firstPaths) {
//            List secPath = calculateOtherPaths(graph_thread, firstPath);
//            if (!secPath.isEmpty()) {
//                paths2.add(secPath);
//            }
//        }
//        return paths2;
    class FirstFinder implements Runnable {

        public void run() {
            Short end = null;
            do {
                end = getNextEnd();
                if (end != (Short) start && end != null) {

                    List<DefaultEdge> path = (List<DefaultEdge>) findPath(graph, start, end);
                    if (path != null) {
                        addToPaths(extractVerticesFromEdges(graph, path, start, end));
                    }
                }
            } while (end != null);
        }
    }

    class OtherFinder implements Runnable {

        NetworkGraph graph_thread;

        public OtherFinder(NetworkGraph graph) {
            this.graph_thread = graph;
        }

        public void run() {
            List nextPath = null;
            do {
                nextPath = getNextPath();
                if (nextPath != null && !nextPath.isEmpty()) {

                    List<DefaultEdge> path = processOtherPath(nextPath);
                    if (path != null && !path.isEmpty()) {
                        addToOtherPaths(path);
                    }
                }
            } while (nextPath != null);
        }

        private Set getAllEdgesFromSet(NetworkGraph graph, Set<Short> set) {
            Set edgesSet = new HashSet();

            for (Short s : set) {
                Set e = new HashSet(graph.incomingEdgesOf(s));
                e.addAll(new HashSet(graph.outgoingEdgesOf(s)));
                edgesSet.addAll(e);
            }
            return edgesSet;
        }

        private Set getAllNeighborsOf(NetworkGraph graph, Set<Short> edgesSet, Short first, Short last) {
            Set neighborsSet = new HashSet();


            for (Object edge : edgesSet) {
                neighborsSet.add(graph.getEdgeSource((DefaultEdge) edge));
                neighborsSet.add(graph.getEdgeTarget((DefaultEdge) edge));
            }
            neighborsSet.remove(first);
            neighborsSet.remove(last);

            return neighborsSet;
        }

        private List<DefaultEdge> processOtherPath(List nextPath) {
            LinkedList<Short> list = new LinkedList<Short>(nextPath);
            Short first = list.removeFirst();
            Short last = list.removeLast();
            List result = new ArrayList();

            if (list.size() == 0) {
                return result;
            }
            /**
             * Todos os nos do caminho excepto 1 e ultimo
             */
            NetworkGraph g = (NetworkGraph) graph_thread.clone();
            Set S1 = new HashSet<Short>(list);
            Set edgesS1 = getAllEdgesFromSet(graph_thread, S1);
//            g.removeAllVertices(S1);
//            List<DefaultEdge> pathS1 = (List<DefaultEdge>) findPath(g, first, last);
//
            Set S2 = getAllNeighborsOf(graph_thread, edgesS1, first, last);
            Set edgesS2 = getAllEdgesFromSet(graph_thread, S2);
//            g.removeAllVertices(S2);
//            List<DefaultEdge> pathS2 = (List<DefaultEdge>) findPath(g, first, last);

            Set S3 = getAllNeighborsOf(graph_thread, edgesS2, first, last);
            Set edgesS3 = getAllEdgesFromSet(graph_thread, S3);
            g.removeAllVertices(S3);
            List<DefaultEdge> pathS3 = (List<DefaultEdge>) findPath(g, first, last);
            if (pathS3 == null) {
                putBack(g, edgesS3, S3);
                g.removeAllVertices(S2);
                List<DefaultEdge> pathS2 = (List<DefaultEdge>) findPath(g, first, last);
                if (pathS2 == null) {
                    putBack(g, edgesS2, S2);
                    g.removeAllVertices(S1);
                    List<DefaultEdge> pathS1 = (List<DefaultEdge>) findPath(g, first, last);
                    if (pathS1 == null) {
                    } else {
                        result = extractVerticesFromEdges(g, pathS1, first, last);
                    }
                } else {
                    result = extractVerticesFromEdges(g, pathS2, first, last);
                }
            } else {
                result = extractVerticesFromEdges(g, pathS3, first, last);
            }

            return result;
        }

        private void putBack(NetworkGraph g, Set edges, Set vertices) {
            for (Object s : vertices) {
                g.addVertex((Short)s);
            }
            for (Object e : edges) {
                Short s = g.getEdgeSource((DefaultEdge) e);
                Short t = g.getEdgeTarget((DefaultEdge) e);
                g.addEdge(s, t);
            }
        }
    }

    private synchronized void addToOtherPaths(List path) {
        otherPaths.add(path);
    }

    private synchronized List getNextPath() {
        return (List) firstPathsQueue.poll();
    }
}
