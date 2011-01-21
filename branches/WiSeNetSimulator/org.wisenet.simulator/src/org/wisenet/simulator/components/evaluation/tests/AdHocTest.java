package org.wisenet.simulator.components.evaluation.tests;

import java.util.Collection;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author posilva
 */
public class AdHocTest extends BaseTest {

    /**
     *
     * @param inputParameters
     */
    public AdHocTest(TestInputParameters inputParameters) {
        super(inputParameters);
        
    }

    /**
     *
     */
    public AdHocTest() {
        super();
    }

    /**
     *
     */
    @Override
    public void prepare() {
        Collection<Node> nodes = simulation.getSimulator().getNodes();
        for (Node node : nodes) {
            if (node.isSource()) {
                sourceNodes.add(node);
            }
            if (node.isReceiver()) {
                receiverNodes.add(node);
            }
            if (node.getRoutingLayer().isUnderAttack()) {
                attackNodes.add(node);
            }
        }
        prepared = true;
    }
}
