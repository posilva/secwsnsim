package org.mei.securesim.core.nodes.factories;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.energy.Batery;

import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.energy.EnergyModel;
import org.mei.securesim.core.layers.mac.MACLayer;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.core.radio.RadioModel;

@SuppressWarnings("unchecked")
public abstract class NodeFactory {

    protected Class classOfNodes = null;
    protected Simulator simulator;
    protected Class application;
    protected Class routingLayer;
    protected EnergyModel energyModel;
    protected boolean setup = false;
    protected Class macLayer;

    public NodeFactory(Simulator simulator) {
        super();
        this.simulator = simulator;
        setup();
    }

    public abstract void setup();

    public Node createNode(short nodeId, double x, double y, double z) throws Exception {
        Node node = createNode(x, y, z);
        node.setPosition(x, y, z);
        node.setId(nodeId);
        return node;
    }

    public Node createNode(double x, double y, double z) throws Exception {
        Node node = nodeCreation();
        node.setPosition(x, y, z);
        return node;
    }

    public List<Node> createNodes(int nodeNum, double areaWidth, double maxElevation) throws Exception {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation();
            node.setPosition(areaWidth * Simulator.randomGenerator.random().nextDouble(),
                    areaWidth * Simulator.randomGenerator.random().nextDouble(),
                    maxElevation * Simulator.randomGenerator.random().nextDouble());
            nodes.add(node);
        }
        return nodes;
    }

    public List<Node> createNodes(int startNodeId, int nodeNum, double areaWidth, double maxElevation) throws Exception {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation((short) (startNodeId + i));
            node.setPosition(areaWidth * Simulator.randomGenerator.random().nextDouble(),
                    areaWidth * Simulator.randomGenerator.random().nextDouble(),
                    maxElevation * Simulator.randomGenerator.random().nextDouble());
            nodes.add(node);
        }
        return nodes;
    }

    public Node createNode(short nodeId) throws Exception {
        return nodeCreation(nodeId);
    }

    public List<Node> createNodes(int startNodeId, int nodeNum) throws Exception {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation((short) (startNodeId + i));
            nodes.add(node);
        }
        return nodes;
    }

    public List<Node> createNodes(int nodeNum) throws Exception {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation();
            nodes.add(node);
        }
        return nodes;
    }

    protected Node nodeCreation() throws Exception{
        if (!setup) {
            throw new IllegalStateException("Factory is not setup!");
        }
        Constructor c = classOfNodes.getConstructor(new Class[]{Simulator.class, RadioModel.class});
        Node node = (Node) c.newInstance(new Object[]{this.simulator,
                    this.simulator.getRadioModel()});
        /* assign routing layer */
        node.setRoutingLayer((RoutingLayer) routingLayer.newInstance());
        /* assign application */
        node.setApplication((Application) application.newInstance());
        /* assign MAC layer */
        node.setMacLayer(getMacLayerInstance());
        /* assign energy model */
        node.setBateryEnergy(new Batery(getEnergyModelInstance()));
        node.getBateryEnergy().setHostNode(node);

        node.init();
        return node;
    }

    protected Node nodeCreation(short nodeId) throws Exception {

        Node node = nodeCreation();
        node.setId(nodeId);

        return node;
    }

    private EnergyModel getEnergyModelInstance() {
        if (energyModel == null) {
            throw new IllegalStateException("EnergyModel is not instanciated!");
        }
        return getEnergyModel();
    }

    private MACLayer getMacLayerInstance() throws Exception {
        if (macLayer == null) {
            throw new IllegalStateException("MACLayer Class is not instanciated!");
        }
        return (MACLayer) macLayer.newInstance();
    }

    public Class getApplication() {
        return application;
    }

    public void setApplicationClass(Class application) {
        this.application = application;
    }

    public Class getRoutingLayer() {
        return routingLayer;
    }

    public void setRoutingLayerClass(Class routingLayer) {
        this.routingLayer = routingLayer;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    public Class getClassOfNodes() {
        return classOfNodes;
    }

    public void setNodeClass(Class classOfNodes) {
        this.classOfNodes = classOfNodes;
    }

    public EnergyModel getEnergyModel() {
        return energyModel;
    }

    public void setEnergyModel(EnergyModel energyModel) {
        this.energyModel = energyModel;
    }

    public boolean isSetup() {
        return setup;
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    public Class getMacLayer() {
        return macLayer;
    }

    public void setMacLayer(Class macLayer) {
        this.macLayer = macLayer;
    }

    public NodeFactory() {
        super();
        setup();
    }
}
