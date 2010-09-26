package org.wisenet.simulator.components.topology;

import java.util.List;
import java.util.Vector;
import org.wisenet.simulator.common.Parameterizable;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;

public abstract class TopologyManager implements Parameterizable {

    AbstractNodeFactory nodeFactory = null;
    TopologyParameters parameters = null;
    boolean parametersRequired = true;

    public TopologyParameters getParameters() {
        return parameters;
    }

    public void setParameters(TopologyParameters parameters) {
        this.parameters = parameters;
    }

    public AbstractNodeFactory getNodeFactory() {
        return nodeFactory;
    }

    public void setNodeFactory(AbstractNodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public List<Node> createTopology() throws Exception {
        if (getNodeFactory() == null) {
            throw new IllegalStateException("NodeFactory instance cannot be NULL");
        }
        if (isParametersRequired()) {
            if (getParameters() == null) {
                throw new IllegalStateException("In this implementation TopologyParamenters cannot be NULL");
            }
        }
        return createTopologyImpl();
    }

    public boolean isParametersRequired() {
        return parametersRequired;
    }

    public void setParametersRequired(boolean parametersRequired) {
        this.parametersRequired = parametersRequired;
    }

    protected abstract List<Node> createTopologyImpl();

    public abstract Vector<Node> apply(Vector<Node> nodes, TopologyParameters parameters);
}
