package pt.unl.fct.di.mei.securesim.network.nodes;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;

@SuppressWarnings("unchecked")
public abstract class NodeFactory {
	protected Class classOfNodes = null;
	protected Simulator simulator;

	public NodeFactory(Simulator simulator, Class classOfNodes) {
		super();
		this.simulator = simulator;
		this.classOfNodes = classOfNodes;
	}

	public Node createNode(short nodeId, double x, double y, double z)
			throws Exception {
		Constructor c = classOfNodes.getConstructor(new Class[] {
				Simulator.class, RadioModel.class });
		Node node = (Node) c.newInstance(new Object[] { this.simulator,
				this.simulator.getRadioModel() });
		node.setPosition(x, y, z);
		node.setId(nodeId);
		return node;
	}

	public List<Node> createNodes(int startNodeId, int nodeNum,
			double areaWidth, double maxElevation) throws Exception {
		ArrayList<Node> nodes = new ArrayList<Node>();
		Constructor c = classOfNodes.getConstructor(new Class[] {
				Simulator.class, RadioModel.class });
		for (int i = 0; i < nodeNum; ++i) {
			Node node = (Node) c.newInstance(new Object[] { this.simulator,
					this.simulator.getRadioModel() });
			node.setPosition(areaWidth * this.simulator.random.nextDouble(),
					areaWidth * this.simulator.random.nextDouble(),
					maxElevation * this.simulator.random.nextDouble());
			node.setId((short) (startNodeId + i));
			nodes.add(node);
		}
		return nodes;
	}

	public Node createNode(short nodeId) throws Exception {
		Constructor c = classOfNodes.getConstructor(new Class[] {Simulator.class, RadioModel.class });
		Node node = (Node) c.newInstance(new Object[] { this.simulator, this.simulator.getRadioModel() });
		node.setId(nodeId);
		return node;
	}

	public List<Node> createNodes(int startNodeId, int nodeNum) throws Exception {
		ArrayList<Node> nodes = new ArrayList<Node>();
		Constructor c = classOfNodes.getConstructor(new Class[] {
				Simulator.class, RadioModel.class });
		for (int i = 0; i < nodeNum; ++i) {
			Node node = (Node) c.newInstance(new Object[] { this.simulator,
					this.simulator.getRadioModel() });
			node.setId((short) (startNodeId + i));
			nodes.add(node);
		}
		return nodes;
	}

}
