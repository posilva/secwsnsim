package pt.unl.fct.di.mei.securesim.network.nodes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import pt.unl.fct.di.mei.securesim.core.Application;

import pt.unl.fct.di.mei.securesim.core.Simulator;
import pt.unl.fct.di.mei.securesim.core.layers.RoutingLayer;
import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.core.radio.RadioModel;

@SuppressWarnings("unchecked")
public abstract class NodeFactory {

    protected Class classOfNodes = null;
    protected Simulator simulator;
    protected Class application;
    protected Class routingLayer;

    public NodeFactory(Simulator simulator, Class classOfNodes, Class application, Class routingLayer) {
        super();
        this.simulator = simulator;
        this.classOfNodes = classOfNodes;
        this.application = application;
        this.routingLayer = routingLayer;

    }

    public NodeFactory(Simulator simulator) {
        super();
        this.simulator = simulator;
    }

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
            node.setPosition(areaWidth * Simulator.random.nextDouble(),
                    areaWidth * Simulator.random.nextDouble(),
                    maxElevation * Simulator.random.nextDouble());
            nodes.add(node);
        }
        return nodes;
    }

    public List<Node> createNodes(int startNodeId, int nodeNum, double areaWidth, double maxElevation) throws Exception {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < nodeNum; ++i) {
            Node node = nodeCreation((short) (startNodeId + i));
            node.setPosition(areaWidth * Simulator.random.nextDouble(),
                    areaWidth * Simulator.random.nextDouble(),
                    maxElevation * Simulator.random.nextDouble());
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

    protected Node nodeCreation() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, SecurityException {
        Constructor c = classOfNodes.getConstructor(new Class[]{Simulator.class, RadioModel.class});
        Node node = (Node) c.newInstance(new Object[]{this.simulator,
                    this.simulator.getRadioModel()});
        node.setRoutingLayer((RoutingLayer) routingLayer.newInstance());
        node.setApplication((Application) application.newInstance());
        return node;
    }

    protected Node nodeCreation(short nodeId) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, SecurityException {
        Node node = nodeCreation();
        node.setId(nodeId);
        return node;
    }

}
