package org.mei.securesim.core.layers;

import org.mei.securesim.components.LayerOutput;
import org.mei.securesim.core.nodes.Node;

public abstract class Layer {

    protected LayerOutput output;
    private Node node;
    protected boolean debugEnabled = true;

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
            if (output != null) {
                output.output(this, message);
            } else {
                System.out.println("{" + getClass().getSimpleName() + "} <" + getNode().getSimulator().getSimulationTime() + "> - [" + getNode().getId() + "] - " + message);
            }
        }
    }
    protected void log(String message, Throwable ex) {

        String error = "";
        if (isDebugEnabled()) {
            error += ex.getMessage() + "\n";
            for (int i = 0; i < ex.getStackTrace().length; i++) {
                StackTraceElement stackTraceElement = ex.getStackTrace()[i];
                error += stackTraceElement.toString() + "\n";
            }
            if (output != null) {
                output.output(this, "["+message +"]\n"+error);
            } else {
                System.out.println("{" + getClass().getSimpleName() + "} <" + getNode().getSimulator().getSimulationTime() + "> - [" + getNode().getId() + "] - " + "["+message +"]\n"+error);
            }
        }
    }

    protected void log(Throwable ex) {
        String message = "";
        if (isDebugEnabled()) {
            message += ex.getMessage() + "\n";
            for (int i = 0; i < ex.getStackTrace().length; i++) {
                StackTraceElement stackTraceElement = ex.getStackTrace()[i];
                message += stackTraceElement.toString() + "\n";
            }
            if (output != null) {
                output.output(this, message);
            } else {
                System.out.println("{" + getClass().getSimpleName() + "} <" + getNode().getSimulator().getSimulationTime() + "> - [" + getNode().getId() + "] - " + message);
            }
        }
    }
}
