package org.mei.securesim.core.layers;

import org.mei.securesim.core.nodes.Node;

public abstract class Layer {

    private Node node;
    protected boolean debugEnabled = false;

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

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    /**
     * Helper function for log facility
     * @param message
     */
    protected void log(String message) {
        if (isDebugEnabled()) {
            System.out.println("{" + getClass().getSimpleName() + "} <" + getNode().getSimulator().getSimulationTime() + "> - [" + getNode().getId() + "] - " + message);
        }
    }
}
