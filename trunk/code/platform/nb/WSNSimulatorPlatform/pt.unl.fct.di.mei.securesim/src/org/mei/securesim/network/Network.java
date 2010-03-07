package org.mei.securesim.network;

import java.util.logging.Logger;

import org.mei.securesim.configuration.ConfigurableObject;
import org.mei.securesim.core.Simulator;
import org.mei.securesim.core.nodes.SensorNode;
import org.mei.securesim.topology.TopologyManager;

public abstract class Network extends ConfigurableObject {

    private final static Logger LOG = Logger.getLogger(Network.class.getName());
    /**
     *
     */
    protected NodeDB nodeDB = new NodeDB();
    /**
     *
     */
    protected NodeDB sinkNodes = new NodeDB();
    /**
     *
     */
    protected NodeDB simpleNodes = new NodeDB();
    /**
     *
     */
    private Simulator simulator = null;

    /**
     * @param nodeDB
     *            the nodeDB to set
     */
    public void setNodeDB(NodeDB nodeDB) {
        this.nodeDB = nodeDB;
    }

    /**
     * @return the nodeDB
     */
    public NodeDB getNodeDB() {
        return nodeDB;
    }

    public void addNode(SensorNode node) {
        if (!node.isSinkNode()) {
            addSimpleNode(node);

        } else {
            addSinkNode(node);
        }
    }

    public void addSimpleNode(SensorNode node) {
        if (!node.isSinkNode()) {
            simpleNodes.store(node);
            nodeDB.store(node);
            LOG.finest("Added simple node");
        } else {
            throw new IllegalArgumentException("Cannot be Node Sink");
        }
    }

    public void addSinkNode(SensorNode node) {
        if (node.isSinkNode()) {
            sinkNodes.store(node);
            nodeDB.store(node);
            LOG.finest("Added sink node");
        } else {
            throw new IllegalArgumentException("Must be Node Sink");
        }

    }

    public void removeNode(int id) {
    }

    public void removeSimpleNode(int id) {
    }

    public void removeSinkNode(int id) {
    }

    /**
     * @param simulator the simulator to set
     */
    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    /**
     * @return the simulator
     */
    public Simulator getSimulator() {
        return simulator;
    }

    public void applyTopology(TopologyManager topologyManager) {
        //topologyManager.apply(this.getNodeDB(),);
    }
}
