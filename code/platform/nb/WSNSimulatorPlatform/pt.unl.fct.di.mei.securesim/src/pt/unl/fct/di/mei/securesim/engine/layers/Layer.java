package pt.unl.fct.di.mei.securesim.engine.layers;

import pt.unl.fct.di.mei.securesim.engine.nodes.Node;



public abstract class Layer {
	private Node node ;

	public Layer() {
		super();
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}
	
}
