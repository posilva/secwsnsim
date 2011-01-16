/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.basestation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.util.VertexPair;
import org.wisenet.protocols.insens.INSENSRoutingLayer;
import org.wisenet.protocols.insens.basestation.dijkstra.engine.CalculatorException;
import org.wisenet.protocols.insens.basestation.jgrapht.DijkstraCalculator;
import org.wisenet.protocols.insens.basestation.jgrapht.NetworkGraph;
import org.wisenet.protocols.insens.messages.data.FDBKPayload;
import org.wisenet.protocols.insens.utils.NeighborInfo;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class BaseStationController {
    //TODO: Test other implementation

    NetworkGraph graph = new NetworkGraph(DefaultEdge.class);
    Hashtable firstPaths = new Hashtable();
    Hashtable secondPaths = new Hashtable();
    Hashtable feedbackMessagesTable = new Hashtable();
    Hashtable networkNeighborsTable = new Hashtable();
    Hashtable<Short, ForwardingTable> forwardingTables = new Hashtable<Short, ForwardingTable>();
    /**
     * 
     */
    private GraphManager networkGraph = new GraphManager();
    private Iterator threadFirstPathsIter;
    private Vector allPaths;
    private final INSENSRoutingLayer basestation;

    /**
     *
     * @return
     */
    public INSENSRoutingLayer getBasestation() {
        return basestation;
    }

    /**
     *
     * @param basestation
     */
    public BaseStationController(INSENSRoutingLayer basestation) {
        this.basestation = basestation;

    }

    /**
     *
     * @return
     */
    public Vector getAllPaths() {
        return allPaths;
    }

    /**
     *
     * @return
     */
    public Hashtable getFirstPaths() {
        return firstPaths;
    }

    /**
     *
     * @return
     */
    public Hashtable getNetworkNeighborsTable() {
        return networkNeighborsTable;
    }

    /**
     *
     * @return
     */
    public Hashtable getSecondPaths() {
        return secondPaths;
    }

    /**
     *
     * @return
     */
    public Hashtable getFeedbackMessagesTable() {
        return feedbackMessagesTable;
    }

    /**
     * Adds a message to the feedback messages
     * @param message
     */
    public void addFeedbackMessages(FDBKPayload message) {
        if (verifyFeedbackMessage(message)) {
            feedbackMessagesTable.put(message.sourceId, message);
            processFeedbackMessage(message);
        }
    }

    /**
     * Makes all topology verification based on nested MAC's
     * @return
     */
    public boolean verifyTopology() {
        return true; //TODO: Verify Topology
    }

    /**
     * Verify if the MAC is correct
     * @param message
     * @return
     */
    public boolean verifyFeedbackMessage(FDBKPayload message) {
        return true; // TODO: verifyFeedbackMessage
    }

    /**
     * Save the message for each source Node
     * @param message
     */
    public void processFeedbackMessage(FDBKPayload message) {
        short src = message.sourceId;
        NeighborInfo neighborInfo = new NeighborInfo();
        neighborInfo.fromByteArray(message.neighborInfo);
        networkNeighborsTable.put(src, neighborInfo);
    }

    /**
     * Calculates a second path excluding first path nodes and neighbors
     * @param firstpath
     * @return
     * @throws CalculatorException
     */
    public ArrayList calculateSecondPath(ArrayList firstpath) throws CalculatorException {
        ArrayList path = new ArrayList();
        HashSet S1 = new HashSet();
        HashSet S2 = new HashSet();
        HashSet S3 = new HashSet();
        if (firstpath.isEmpty()) {
            return path;
        }
        Short from = (Short) firstpath.get(0);
        Short to = (Short) firstpath.get(firstpath.size() - 1);
        ArrayList workingPath = new ArrayList(firstpath);
        workingPath.remove(from);
        workingPath.remove(to);

        if (workingPath.isEmpty()) {
            return path;
        }


        for (Object object : workingPath) {
            S1.add(object);
            S2.add(object);
            S3.add(object);
            NeighborInfo ni = (NeighborInfo) this.networkNeighborsTable.get(object);
            if (ni != null) {
                Vector v = new Vector(ni.keySet());
                v.remove(from);
                v.remove(to);
                for (Object object1 : v) {
                    S2.add(object1);
                    S3.add(object1);
                    NeighborInfo ni2 = (NeighborInfo) this.networkNeighborsTable.get(object1);
                    if (ni2 != null) {
                        Vector v2 = new Vector(ni.keySet());
                        v2.remove(from);
                        v2.remove(to);
                        for (Object object2 : v2) {
                            S3.add(object2);
                        }
                    }
                }
            }
        }
        ArrayList lst1 = new ArrayList();
        lst1 = calculatePathWithOutFromTo(S3, from, to);
        if (lst1 == null || lst1.isEmpty()) {
            lst1 = calculatePathWithOutFromTo(S2, from, to);
            if (lst1 == null || lst1.isEmpty()) {
                lst1 = calculatePathWithOutFromTo(S1, from, to);
            }
        }

        return lst1;
    }

    /**
     * Calculates a path without a set of nodes
     * @param S1
     * @param from
     * @param to
     * @return
     */
    private ArrayList calculatePathWithOutFromTo(HashSet S1, Short from, Short to) throws CalculatorException {
        List lst1;
        GraphManager g1 = createNetworkGraphWithOut(S1);
        DijkstraCalculator c1 = new DijkstraCalculator();
        c1.addVertexs(g1.vertices);
        c1.addEdges(g1.edges);
        c1.prepare();
        lst1 = c1.calculatePath(from, to);
        return (ArrayList) lst1;
    }

    /**
     * Creates a network graph without a set of nodes
     * @param nodes
     * @return
     */
    public GraphManager createNetworkGraphWithOut(Set nodes) {
        GraphManager graphManager = new GraphManager();
        for (Object e1 : this.networkNeighborsTable.keySet()) { // por cada nó
            if (!nodes.contains(e1)) {
                Short edge1 = (Short) e1;
                NeighborInfo neighborInfo1 = (NeighborInfo) networkNeighborsTable.get(edge1); // ler a tabela de vizinhos
                if (neighborInfo1 != null) {
                    for (Object e2 : neighborInfo1.keySet()) {
                        if (!nodes.contains(e2)) {
                            Short edge2 = (Short) e2;
                            NeighborInfo neighborInfo2 = (NeighborInfo) networkNeighborsTable.get(e2);
                            if (neighborInfo2 != null) {
                                if (neighborInfo2.containsKey(edge1)) {
                                    graphManager.addEdge(edge1, edge2);
                                }
                            }
                        }
                    }
                }
            }

        }
        return graphManager;
    }

    /**
     * Creates the network Graph
     */
    public void createNetworkGraph() {
        networkGraph.clear();
        for (Object e1 : this.networkNeighborsTable.keySet()) { // por cada nó que enviou com sucesso feedback message
            Short edge1 = (Short) e1; // Nó com feedback message recebida
            NeighborInfo neighborInfo1 = (NeighborInfo) networkNeighborsTable.get(edge1); // ler a tabela de vizinhos
            if (neighborInfo1 != null) {
                for (Object e2 : neighborInfo1.keySet()) {
                    Short edge2 = (Short) e2;
                    NeighborInfo neighborInfo2 = (NeighborInfo) networkNeighborsTable.get(e2);
                    if (neighborInfo2 != null) {
                        if (neighborInfo2.containsKey(edge1)) {
                            networkGraph.addEdge(edge1, edge2);
                        }
                    }
                }
            }
        }

    }

    public void calculateForwardingTables2() {
        long start = System.currentTimeMillis();
        long partial = start;
        System.out.println("INITIATED FORWARDING TABLES CALCULATION");
        prepareBaseStation(basestation);

        System.out.println("prepared base station IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
        partial = System.currentTimeMillis();
        createGraph();
        System.out.println("created network graph IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
        partial = System.currentTimeMillis();

        Hashtable firstPathsTtemp = calculateFirstPaths(basestation.getNode().getId());

        System.out.println("first path calculated IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
        partial = System.currentTimeMillis();
        Hashtable secondPathsTemp = calculateSecondPaths(firstPathsTtemp);
        System.out.println("second path calculated IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");

        partial = System.currentTimeMillis();
        buildForwardingTables(firstPathsTtemp, secondPathsTemp);
        System.out.println("builded tables IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");

        System.out.println("ENDED FORWARDING TABLES CALCULATION IN " + (System.currentTimeMillis() - start) / 1000 + " SECONDS");

    }

    /**
     * Utility function to print Forwarding tables
     */
    protected void printFwTables() {
        for (ForwardingTable forwardingTable : forwardingTables.values()) {
            System.out.println(forwardingTable);
        }
    }

    /**
     * Creates the second paths knowing the first paths
     * @param firstPathsTtemp
     * @return
     */
    private Hashtable calculateSecondPaths(Hashtable firstPathsTtemp) {
        try {

            threadFirstPathsIter = firstPathsTtemp.keySet().iterator();
            Thread t1 = new Thread(new TaskSecondPathsCalculator());
            Thread t2 = new Thread(new TaskSecondPathsCalculator());
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            return secondPaths;
        } catch (InterruptedException ex) {
            Logger.getLogger(BaseStationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Utility functions to print paths
     * @param allPaths
     */
    protected void printPaths(Vector allPaths) {
        for (Object object : allPaths) {
            ArrayList list = (ArrayList) object;
            System.out.print("[");
            for (Object object1 : list) {
                System.out.print(" " + object1 + " ");
            }
            System.out.println("]");
        }
    }

    private List callPathFinder(NetworkGraph graph, ArrayList list) {
        short start = (Short) getBasestation().getUniqueId();
        PathsFinder finder = new PathsFinder(graph,start);
        return finder.findFirstPaths();
    }

    /**
     * Thread to calculate a second path
     */
    protected class TaskSecondPathsCalculator implements Runnable {
        /**
         *
         */
        public void run() {

            Object key;
            do {
                key = getNextKey();
                if (key != null) {
                    try {
                        List value = (List) firstPaths.get(key);
                        ArrayList new_path = calculateSecondPath((ArrayList) value);
                        if (new_path != null) {
                            if (!new_path.isEmpty()) {
                                updateSecondPath(key, new_path);
                            }
                        }
                    } catch (CalculatorException ex) {
                        Logger.getLogger(BaseStationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (key != null);
        }

        private synchronized Object getNextKey() {
            if (threadFirstPathsIter.hasNext()) {
                return threadFirstPathsIter.next();
            } else {
                return null;
            }
        }

        private synchronized void updateSecondPath(Object key, ArrayList value) {
            secondPaths.put(key, value);
        }
    }

    /**
     * Calculates the first set of paths to the network
     * @param start
     * @return
     */
    private Hashtable calculateFirstPaths(short start) {
        final short graphSize = (short) networkGraph.vertices.size();
        final short graphHalf = (short) (graphSize / 2);
        firstPaths.clear();
        try {
            Thread t1 = new Thread(new TaskPathCalculator(start, (short) 0, (short) (graphHalf - 1)));
            Thread t2 = new Thread(new TaskPathCalculator(start, graphHalf, (short) (graphSize - 1)));
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            return firstPaths;
        } catch (InterruptedException ex) {
            Logger.getLogger(BaseStationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Function to create the Forwarding tables knowing the first and second
     * paths
     * @param firstPathsT
     * @param secondPathsT
     */
    private void buildForwardingTables(Hashtable firstPathsT, Hashtable secondPathsT) {
        allPaths = new Vector();
        allPaths.addAll(firstPathsT.values());
        allPaths.addAll(secondPathsT.values());
        for (int i = 0; i < allPaths.size(); i++) {
            List path = (List) allPaths.get(i);
            Hashtable table = path2RoutingTableEntryTable((ArrayList) path);
            saveTable(table);
        }

    }

    private synchronized void saveTable(Hashtable table) {
        for (Object o : table.keySet()) {
            Short node = (Short) o;
            ForwardingTable fwt = forwardingTables.get(node);
            if (fwt == null) {
                fwt = new ForwardingTable(node);
            }
            ArrayList t = (ArrayList) table.get(o);
            if (t.size() > 0) {
                fwt.add((RoutingTableEntry) t.get(0));
            }
            if (t.size() > 1) {
                fwt.add((RoutingTableEntry) t.get(1));
            }
            forwardingTables.put(node, fwt);
        }
    }

    /**
     * Thread to calculate the first path
     */
    class TaskPathCalculator implements Runnable {

        short start;
        short from;
        short to;
        Hashtable h;

        public TaskPathCalculator(short start, short from, short to) {
            this.start = start;
            this.from = from;
            this.to = to;
        }

        public void run() {
            DijkstraCalculator c = new DijkstraCalculator();
            c.addVertexs(networkGraph.vertices);
            c.addEdges(networkGraph.edges);
            c.prepare();
            Set s = new HashSet(networkGraph.vertices.subList(from, to));
            h = c.calculateAllPathsFromTo(start, s);
            updatePaths(h);
        }
    }

    private synchronized void updatePaths(Hashtable h) {
        firstPaths.putAll(h);
    }

    /**
     * Prepare the base station adding the base station info 
     * @param baseStation
     */
    private void prepareBaseStation(INSENSRoutingLayer baseStation) {
        NeighborInfo n = new NeighborInfo();
        n.fromByteArray(baseStation.getNeighborInfo().toByteArray());
        networkNeighborsTable.put(baseStation.getNode().getId(), n);
    }

    /**
     *  Calculate the paths
     * @param path
     * @return
     */
    private Hashtable path2RoutingTableEntryTable(ArrayList path) {
        Short destination;
        Short source;
        Hashtable table = new Hashtable();
//        if (path.size() == 2) {
//            return table;
//        }
//        if (path.isEmpty()) return table;
        destination = (Short) path.get(path.size() - 1);
        source = (Short) path.get(0);
        for (int i = 1; i < path.size(); i++) {
            Short node = (Short) path.get(i);
            ArrayList t = new ArrayList();
            t.add(new RoutingTableEntry(source, destination, (Short) path.get(i - 1)));
            if (i < path.size() - 1) {
                t.add(new RoutingTableEntry(destination, source, (Short) path.get(i + 1)));
            }
            table.put(node, t);
        }
        return table;
    }

    /**
     * Gets the Forwarding Tables
     * @return
     */
    public Hashtable<Short, ForwardingTable> getForwardingTables() {
        return forwardingTables;
    }

    /**
     ******************************************************************************************
     ******************************************************************************************
     ******************************************************************************************
     ******************************************************************************************
     ******************************************************************************************
     */
    /**
     * Perform the calculations related with building forwarding tables
     */
    public void calculateForwardingTables() {

        long start = System.currentTimeMillis();
        long partial = start;
        System.out.println("INITIATED FORWARDING TABLES CALCULATION");
        prepareBaseStation(basestation);

        System.out.println("prepared base station IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
        partial = System.currentTimeMillis();
        createGraph();
        System.out.println("created network graph IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
        partial = System.currentTimeMillis();

        List paths1 = calculatePaths(graph); // first paths

        System.out.println("first path calculated IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
        partial = System.currentTimeMillis();
        List paths2 = calculatePaths2(graph, paths1);
        System.out.println("second path calculated IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");

        partial = System.currentTimeMillis();
        buildForwardingTables(paths1, paths2);
        System.out.println("builded tables IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");

        System.out.println("ENDED FORWARDING TABLES CALCULATION IN " + (System.currentTimeMillis() - start) / 1000 + " SECONDS");
    }

    public void createGraph() {
        for (Object e1 : this.networkNeighborsTable.keySet()) { // por cada nó que enviou com sucesso feedback message
            Short edge1 = (Short) e1; // Nó com feedback message recebida
            NeighborInfo neighborInfo1 = (NeighborInfo) networkNeighborsTable.get(edge1); // ler a tabela de vizinhos
            if (neighborInfo1 != null) {
                for (Object e2 : neighborInfo1.keySet()) {
                    Short edge2 = (Short) e2;
                    NeighborInfo neighborInfo2 = (NeighborInfo) networkNeighborsTable.get(e2);
                    if (neighborInfo2 != null) {
                        if (neighborInfo2.containsKey(edge1)) {
                            graph.addVertex(edge1);
                            graph.addVertex(edge2);
                            if (!graph.containsEdge(edge2, edge1) && !graph.containsEdge(edge1, edge2)) {
                                graph.addEdge(edge1, edge2);
                                graph.addEdge(edge2, edge1);
                            }
                        }
                    }
                }
            }
        }
    }

    private List calculatePaths(NetworkGraph graph) {
        ArrayList list = new ArrayList();
        return callPathFinder(graph, list);
    }

    /**
     * Extract all vertices from edge path
     * @param graph
     * @param path
     * @param start
     * @param end
     * @return
     */
    private List extractVertexs(NetworkGraph graph, List<DefaultEdge> path, Short start, short end) {
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

    private List calculatePaths2(NetworkGraph graph, List<List> paths1) {

//        List<List> paths2 = new ArrayList();
//        for (List firstPath : paths1) {
//            List secPath = calculate2Path(graph, firstPath);
//            if (!secPath.isEmpty()) {
//                paths2.add(secPath);
//            }
//        }
//        return paths2;

        return new PathsFinder(graph, (Short)getBasestation().getUniqueId()).findOtherPaths(paths1);

    }

    private List calculate2Path(NetworkGraph graph, List firstPath) {
        LinkedList<Short> list = new LinkedList<Short>(firstPath);
        Short first = list.removeFirst();
        Short last = list.removeLast();
        List result = new ArrayList();

        if (list.size() == 0) {
            return result;
        }
        /**
         * Todos os nos do caminho excepto 1 e ultimo
         */
        NetworkGraph g = (NetworkGraph) graph.clone();
        Set S1 = new HashSet<Short>(list);
        Set edgesS1 = getAllEdgesFromSet(graph, S1);
        g.removeAllVertices(S1);
        List<DefaultEdge> pathS1 = DijkstraShortestPath.findPathBetween(g, first, last);

        Set S2 = getAllNeighborsOf(graph, edgesS1, first, last);
        Set edgesS2 = getAllEdgesFromSet(graph, S2);
        g.removeAllVertices(S2);
        List<DefaultEdge> pathS2 = DijkstraShortestPath.findPathBetween(g, first, last);

        Set S3 = getAllNeighborsOf(graph, edgesS2, first, last);
        Set edgesS3 = getAllEdgesFromSet(graph, S3);
        g.removeAllVertices(S3);
        List<DefaultEdge> pathS3 = DijkstraShortestPath.findPathBetween(g, first, last);
        if (pathS3 == null) {
            if (pathS2 == null) {
                if (pathS1 == null) {
                } else {
                    result = extractVertexs(g, pathS1, first, last);
                }
            } else {
                result = extractVertexs(g, pathS2, first, last);
            }
        } else {
            result = extractVertexs(g, pathS3, first, last);
        }

        return result;
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

//        // retirar das contas o primeiro e o ultimo dos nos
//        for (DefaultEdge edge : graph.edgeSet()) {
//            if ((edgeBelongsToSet(graph, set, edge))
//                    && (!vertexBelongsToEdge(graph, edge, first)
//                    && !vertexBelongsToEdge(graph, edge, last))) {
//                edgesSet.add(edge); // SOADICIONA SE PERTTENCER
//                neighborsSet.add(graph.getEdgeSource(edge));
//                neighborsSet.add(graph.getEdgeTarget(edge));
//            }
//        }
        return neighborsSet;
    }

//    private boolean edgeBelongsToSet(NetworkGraph graph, Set<Short> set, DefaultEdge edge) {
//        return set.contains(graph.getEdgeSource(edge)) || set.contains(graph.getEdgeTarget(edge));
//    }
//
//    private boolean vertexBelongsToEdge(NetworkGraph graph, DefaultEdge edge, Short vertex) {
//        return graph.getEdgeTarget(edge).equals(vertex) || graph.getEdgeSource(edge).equals(vertex);
//    }

    private void buildForwardingTables(List firstPathsL, List secondPathsL) {
        allPaths = new Vector();
        allPaths.addAll(firstPathsL);
        allPaths.addAll(secondPathsL);
        for (int i = 0; i < allPaths.size(); i++) {
            List path = (List) allPaths.get(i);
            Hashtable table = path2RoutingTableEntryTable((ArrayList) path);
            saveTable(table);
        }

    }
}
