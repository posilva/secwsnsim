package org.wisenet.simulator.core.node.factories;

import java.lang.reflect.Constructor;
import java.util.Vector;
import java.util.List;
import org.wisenet.simulator.core.Application;
import org.wisenet.simulator.core.energy.Batery;

import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.node.layers.mac.MACLayer;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.radio.RadioModel;

/**
 *
 * @author posilva
 */
@SuppressWarnings("unchecked")
public abstract class AbstractNodeFactory {

    /**
     *
     */
    public static final int INITIAL_NODE_MAXRADIOSTRENGTH = 100;
    /**
     *
     */
    protected int nodeMaxRadioRange = INITIAL_NODE_MAXRADIOSTRENGTH;
    /**
     *
     */
    protected Class classOfNodes = null;
    /**
     *
     */
    protected Simulator simulator;
    /**
     *
     */
    protected Class application;
    /**
     *
     */
    protected Class routingLayer;
    /**
     *
     */
    protected EnergyModel energyModel;
    /**
     *
     */
    protected boolean setup = false;
    /**
     *
     */
    protected Class macLayer;
    /**
     *
     */
    protected boolean staticZ = true;
    /**
     *
     */
    protected double minZ = 0;
    /**
     *
     */
    protected double maxZ = 0;
    /**
     *
     */
    protected double environmentAttenuation = 0;

    /**
     *
     * @param simulator
     */
    public AbstractNodeFactory(Simulator simulator) {
        super();
        this.simulator = simulator;
    }

    /**
     *
     */
    public abstract void setup();

    /**
     *
     * @param nodeId
     * @param x
     * @param y
     * @param z
     * @return
     * @throws Exception
     */
    public Node createNode(short nodeId, double x, double y, double z) throws Exception {
        Node node = createNode(x, y, z);
        node.setPosition(x, y, z);
        node.setId(nodeId);
        return node;
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     * @throws Exception
     */
    public Node createNode(double x, double y, double z) throws Exception {
        Node node = nodeCreation();
        node.setPosition(x, y, z);
        return node;
    }

    /**
     *
     * @param nodeNum
     * @param areaWidth
     * @param maxElevation
     * @return
     * @throws Exception
     */
    public List<Node> createNodes(int nodeNum, double areaWidth, double maxElevation) throws Exception {
        Vector<Node> nodes = new Vector<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation();
            node.setPosition(areaWidth * Simulator.randomGenerator.random().nextDouble(),
                    areaWidth * Simulator.randomGenerator.random().nextDouble(),
                    maxElevation * Simulator.randomGenerator.random().nextDouble());
            nodes.add(node);
        }
        return nodes;
    }

    /**
     *
     * @param startNodeId
     * @param nodeNum
     * @param areaWidth
     * @param maxElevation
     * @return
     * @throws Exception
     */
    public List<Node> createNodes(int startNodeId, int nodeNum, double areaWidth, double maxElevation) throws Exception {
        Vector<Node> nodes = new Vector<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation((short) (startNodeId + i));
            node.setPosition(areaWidth * Simulator.randomGenerator.random().nextDouble(),
                    areaWidth * Simulator.randomGenerator.random().nextDouble(),
                    maxElevation * Simulator.randomGenerator.random().nextDouble());
            nodes.add(node);
        }
        return nodes;
    }

    /**
     *
     * @param nodeId
     * @return
     * @throws Exception
     */
    public Node createNode(short nodeId) throws Exception {
        return nodeCreation(nodeId);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public Node createNode() throws Exception {
        return nodeCreation();
    }

    /**
     *
     * @param startNodeId
     * @param nodeNum
     * @return
     * @throws Exception
     */
    public List<Node> createNodes(int startNodeId, int nodeNum) throws Exception {
        Vector<Node> nodes = new Vector<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation((short) (startNodeId + i));
            nodes.add(node);
        }
        return nodes;
    }

    /**
     *
     * @param nodeNum
     * @return
     * @throws Exception
     */
    public List<Node> createNodes(int nodeNum) throws Exception {
        Vector<Node> nodes = new Vector<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation();
            nodes.add(node);
        }
        return nodes;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    protected Node nodeCreation() throws Exception {
        if (!setup) {
            throw new IllegalStateException("Factory is not setted up!");
        }
        Constructor c = classOfNodes.getConstructor(new Class[]{Simulator.class, RadioModel.class});
        Node node = (Node) c.newInstance(new Object[]{this.simulator,
                    this.simulator.getRadioModel()});
        /* assign routing layer */
        node.setRoutingLayer((RoutingLayer) routingLayer.newInstance());
        /* assign application */
        node.setApplication((Application) application.newInstance());
        /* sets the radio strength*/
        node.getConfig().setSetRadioRange(getNodeMaxRadioRange() );
        /* assign MAC layer */
        node.setMacLayer(getMacLayerInstance());
        /* assign energy model */
        node.setBateryEnergy(new Batery(getEnergyModelInstance()));

        node.getBateryEnergy().setHostNode(node);
        
        node.setEnvironmentAttenuation(getEnvironmentAttenuation());
        node.init();
        return node;
    }

    /**
     *
     * @param nodeId
     * @return
     * @throws Exception
     */
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

    /**
     *
     * @return
     */
    public Class getApplication() {
        return application;
    }

    /**
     *
     * @param application
     */
    public void setApplicationClass(Class application) {
        this.application = application;
    }

    /**
     *
     * @return
     */
    public Class getRoutingLayer() {
        return routingLayer;
    }

    /**
     *
     * @param routingLayer
     */
    public void setRoutingLayerClass(Class routingLayer) {
        this.routingLayer = routingLayer;
    }

    /**
     *
     * @return
     */
    public Simulator getSimulator() {
        return simulator;
    }

    /**
     *
     * @param simulator
     */
    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    /**
     *
     * @return
     */
    public Class getClassOfNodes() {
        return classOfNodes;
    }

    /**
     *
     * @param classOfNodes
     */
    public void setNodeClass(Class classOfNodes) {
        this.classOfNodes = classOfNodes;
    }

    /**
     *
     * @return
     */
    public EnergyModel getEnergyModel() {
        return energyModel;
    }

    /**
     *
     * @param energyModel
     */
    public void setEnergyModel(EnergyModel energyModel) {
        this.energyModel = energyModel;
    }

    /**
     *
     * @return
     */
    public boolean isSetup() {
        return setup;
    }

    /**
     *
     * @param setup
     */
    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    /**
     *
     * @return
     */
    public Class getMacLayer() {
        return macLayer;
    }

    /**
     *
     * @param macLayer
     */
    public void setMacLayer(Class macLayer) {
        this.macLayer = macLayer;
    }

    /**
     *
     */
    public AbstractNodeFactory() {
        super();
        setup();
    }

    /**
     *
     * @return
     */
    public int getNodeMaxRadioRange() {
        return nodeMaxRadioRange;
    }

    /**
     *
     * @param nodeMaxRadioStregth
     */
    public void setNodeMaxRadioRange(int nodeMaxRadioStregth) {
        this.nodeMaxRadioRange = nodeMaxRadioStregth;
    }

    /**
     *
     * @return
     */
    public double getEnvironmentAttenuation() {
        return environmentAttenuation;
    }

    /**
     *
     * @param environmentAttenuation
     */
    public void setEnvironmentAttenuation(double environmentAttenuation) {
        this.environmentAttenuation = environmentAttenuation;
    }

    /**
     *
     * @return
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     *
     * @param maxZ
     */
    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    /**
     *
     * @return
     */
    public double getMinZ() {
        return minZ;
    }

    /**
     *
     * @param minZ
     */
    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    /**
     *
     * @return
     */
    public boolean isStaticZ() {
        return staticZ;
    }

    /**
     *
     * @param staticZ
     */
    public void setStaticZ(boolean staticZ) {
        this.staticZ = staticZ;
    }
}
