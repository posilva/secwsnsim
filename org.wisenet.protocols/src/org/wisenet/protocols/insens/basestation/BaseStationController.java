/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.basestation;

import java.util.ArrayList;
import java.util.Hashtable;

import java.util.List;
import java.util.Vector;
import org.jgrapht.graph.DefaultEdge;
import org.wisenet.protocols.insens.INSENSRoutingLayer;
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
    PathsFinder pathsFinder;
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
     * Utility function to print Forwarding tables
     */
    protected void printFwTables() {
        for (ForwardingTable forwardingTable : forwardingTables.values()) {
            System.out.println(forwardingTable);
        }
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
        pathsFinder = new PathsFinder(graph, start);
        return pathsFinder.findFirstPaths();
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
        int numOfFDBKMessages = feedbackMessagesTable.entrySet().size();
                System.out.println("RECEIVED " + numOfFDBKMessages + " FDBK MESSAGES AT BASESTATION");
                long start = System.currentTimeMillis();
                long partial = start;
                System.out.println("INITIATED FORWARDING TABLES CALCULATION");
                prepareBaseStation(basestation);

                System.out.println("prepared base station IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
                partial = System.currentTimeMillis();
                createGraph();
                System.out.println("created network graph IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
                partial = System.currentTimeMillis();
                Short startId = (Short) getBasestation().getUniqueId();
                pathsFinder = new PathsFinder(graph, startId);
                List paths1 = pathsFinder.findFirstPaths();

                System.out.println("first path calculated IN " + (System.currentTimeMillis() - partial) / 1000 + " SECONDS");
                partial = System.currentTimeMillis();
                List paths2 = pathsFinder.findOtherPaths(paths1);
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

    private List calculatePaths2(NetworkGraph graph, List<List> paths1) {
        return new PathsFinder(graph, (Short) getBasestation().getUniqueId()).findOtherPaths(paths1);
    }

    private void buildForwardingTables(List firstPathsL, List secondPathsL) {
        allPaths = new Vector();
        allPaths.addAll(firstPathsL);
        allPaths.addAll(secondPathsL);
        for (int i = 0; i < allPaths.size(); i++) {
            List path = (List) allPaths.get(i);
            if (path != null && path.size() > 2) {
                Hashtable table = path2RoutingTableEntryTable((ArrayList) path);
                saveTable(table);
            }
        }

    }
}
