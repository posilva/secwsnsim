/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.evaluation.tests;

import java.util.List;
import org.wisenet.simulator.components.evaluation.tests.events.TestEndEvent;
import org.wisenet.simulator.components.evaluation.tests.events.TestExecutionEvent;
import org.wisenet.simulator.components.evaluation.tests.events.TestStartEvent;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author posilva
 */
public class DefaultTest extends AbstractTest {

    private static int messageCounter = 0;

    public DefaultTest(TestInputParameters inputParameters) {
        super(inputParameters);
    }

    public DefaultTest() {
        super();
    }

    @Override
    public boolean verifyPreConditions() {
        return true;
    }

    @Override
    public void beforeStart() {
    }

    @Override
    public void afterFinish() {
    }

    @Override
    public void execute() {
        if (getActiveSimulation() == null) {
            return;
        }
        // Starts Test
        TestStartEvent startEvent = new TestStartEvent();
        startEvent.setTest(this);
        getSimulation().getSimulator().addEvent(startEvent);

        // Build test conditions
        buildTestConditions();


        // Ends Test
        TestEndEvent endEvent = new TestEndEvent();
        endEvent.setTest(this);
        getSimulation().getSimulator().addEvent(endEvent);
    }

    private void buildTestConditions() {
        int stableNodes = simulation.getRoutingLayerController().getTotalStableNodes();
        int allNodes = simulation.getSimulator().getNodes().size();
        List sourceNodes = null;
        List receiverNodes = null;
        List attackNodes = null;
        long time = 0;

        if (inputParameters.getNumberOfSenderNodes() > 0) {
            int selectableNodes = inputParameters.isOnlyConsiderToSenderStableNodes() ? stableNodes : allNodes;
            int nNodes = inputParameters.isPercentOfSenderNodes() ? (inputParameters.getNumberOfSenderNodes() * 100 / selectableNodes) : (inputParameters.getNumberOfSenderNodes());

            sourceNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    if (inputParameters.isOnlyConsiderToSenderStableNodes()) {
                        return node.getRoutingLayer().isStable() && !node.isSinkNode();
                    }
                    return !node.isSinkNode();
                }
            });


            nNodes = inputParameters.getNumberOfReceiverNodes();
            receiverNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    if (inputParameters.isOnlyConsiderToReceiverSinkNodes()) {
                        return node.isSinkNode();
                    }
                    return true;
                }
            });
            selectableNodes = inputParameters.isOnlyConsiderToSenderStableNodes() ? stableNodes : allNodes;
            nNodes = inputParameters.isPercentOfAttackNodes() ? (inputParameters.getNumberOfAttackNodes() * 100 / selectableNodes) : (inputParameters.getNumberOfAttackNodes());
            final List src = sourceNodes;
            attackNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    if (inputParameters.isOnlyConsiderToAttackStableNodes()) {
                        return node.getRoutingLayer().isStable() && !node.isSinkNode() && !src.contains(node);
                    }
                    return !node.isSinkNode();
                }
            });
            time = getSimulation().getTime();
            for (Object s : sourceNodes) {
                Node srcNode = (Node) s;
                for (Object r : receiverNodes) {
                    Node rcvNode = (Node) r;
                    time += inputParameters.getIntervalBetweenMessagesSent() * Simulator.ONE_SECOND;
                    for (int i = 0; i < inputParameters.getNumberOfRetransmissions(); i++) {
                        DefaultTestExecutionEvent event = createEvent(srcNode, rcvNode, time);
                        getSimulation().getSimulator().addEvent(event);
                    }
                }
            }
        }

    }

    private TestMessage createTestMessage(Node s, Node r) {
        DefaultTestMessage m = new DefaultTestMessage();
        m.setSourceId(s.getUniqueID());
        m.setDestinationId(r.getUniqueID());
        m.setUniqueId(messageCounter++);
        return m;
    }

    private DefaultTestExecutionEvent createEvent(Node srcNode, Node rcvNode, long time) {
        DefaultTestExecutionEvent event = new DefaultTestExecutionEvent();
        TestMessage m = createTestMessage(srcNode, rcvNode);
        event.setTime(time);

        event.setTest(this);
        event.setTime(messageCounter);
        return event;
    }

    class DefaultTestExecutionEvent extends TestExecutionEvent {

        protected Node sourceNode;
        protected Node destNode;
        protected Message message;

        public Node getDestNode() {
            return destNode;
        }

        public void setDestNode(Node destNode) {
            this.destNode = destNode;
        }

        public Node getSourceNode() {
            return sourceNode;
        }

        public void setSourceNode(Node sourceNode) {
            this.sourceNode = sourceNode;
        }

        @Override
        public void execute() {
            super.execute();
            sourceNode.sendMessage(message);
        }
    }

    class DefaultTestMessage extends TestMessage {

        Object sourceId;
        Object destinationId;
        Object uniqueId;

        @Override
        public Object getDestinationId() {
            return destinationId;
        }

        @Override
        public Object getSourceId() {
            return sourceId;
        }

        @Override
        public Object getUniqueId() {
            return uniqueId;
        }

        @Override
        public void setDestinationId(Object id) {
            this.destinationId = id;
        }

        @Override
        public void setSourceId(Object id) {
            this.sourceId = id;
        }

        @Override
        public void setUniqueId(Object id) {
            uniqueId = id;
        }
    }
}
