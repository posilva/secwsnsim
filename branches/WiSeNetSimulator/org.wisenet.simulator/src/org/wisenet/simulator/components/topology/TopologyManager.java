package org.wisenet.simulator.components.topology;

import java.util.List;
import java.util.Vector;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.Parameterizable;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;

/**
 *
 * @author posilva
 */
public abstract class TopologyManager implements Parameterizable {

    AbstractNodeFactory nodeFactory = null;
    TopologyParameters parameters = null;
    boolean parametersRequired = true;

    /**
     *
     * @return
     */
    public TopologyParameters getParameters() {
        return parameters;
    }

    /**
     *
     * @param parameters
     */
    public void setParameters(ObjectParameters parameters) {
        this.parameters = (TopologyParameters) parameters;
    }

    /**
     *
     * @return
     */
    public AbstractNodeFactory getNodeFactory() {
        return nodeFactory;
    }

    /**
     *
     * @param nodeFactory
     */
    public void setNodeFactory(AbstractNodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    /**
     *
     * @return
     * @throws Exception
     */
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

    /**
     *
     * @return
     */
    public boolean isParametersRequired() {
        return parametersRequired;
    }

    /**
     *
     * @param parametersRequired
     */
    public void setParametersRequired(boolean parametersRequired) {
        this.parametersRequired = parametersRequired;
    }

    /**
     *
     * @return
     */
    protected abstract List<Node> createTopologyImpl();

    /**
     *
     * @param nodes
     * @param parameters
     * @return
     */
    public abstract Vector<Node> apply(Vector<Node> nodes, TopologyParameters parameters);
}
