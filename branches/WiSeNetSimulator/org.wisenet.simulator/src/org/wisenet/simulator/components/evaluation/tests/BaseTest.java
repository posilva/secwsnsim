/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.evaluation.tests;

import java.util.LinkedList;
import java.util.List;
import org.wisenet.simulator.components.evaluation.tests.events.TestEndEvent;
import org.wisenet.simulator.components.evaluation.tests.events.TestExecutionEvent;
import org.wisenet.simulator.components.evaluation.tests.events.TestStartEvent;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.core.Event;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksEntry;

/**
 *
 * @author posilva
 */
public class BaseTest extends AbstractTest {

    List<Event> testEvents = new LinkedList<Event>();
    private static int messageCounter = 0;
    long testTime = 0;

    public BaseTest(TestInputParameters inputParameters) {
        super(inputParameters);
    }

    public BaseTest() {
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

        if (getSimulation() == null) {
            return;
        }

        testTime = getSimulation().getTime() + (Simulator.ONE_SECOND * 5);

        // Starts Test
        TestStartEvent startEvent = new TestStartEvent();
        startEvent.setTest(this);
        startEvent.setTime(testTime);
        testEvents.add(startEvent);
        testTime += Simulator.ONE_SECOND * 5;

        // Build test conditions
        buildTestConditions();
        testTime += Simulator.ONE_SECOND * 5;
        // Ends Test
        TestEndEvent endEvent = new TestEndEvent();
        endEvent.setTest(this);
        endEvent.setTime(testTime);
        testEvents.add(endEvent);

        if (testEvents.size() > 2) {
            for (Event event : testEvents) {
                getSimulation().getSimulator().addEvent(event);

            }
        }
    }

    private void buildTestConditions() {
        int stableNodes = simulation.getRoutingLayerController().getTotalStableNodes();
        int allNodes = simulation.getSimulator().getNodes().size();
        List sourceNodes = null;
        List receiverNodes = null;
        List attackNodes = null;
        Integer interval = inputParameters.getIntervalBetweenMessagesSent();



        if (inputParameters.getNumberOfSenderNodes() > 0) {
            int selectableNodes = inputParameters.isOnlyConsiderToSenderStableNodes() ? stableNodes : allNodes;
            int nNodes = inputParameters.isPercentOfSenderNodes() ? (inputParameters.getNumberOfSenderNodes() * selectableNodes / 100) : (inputParameters.getNumberOfSenderNodes());

            sourceNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    if (inputParameters.isOnlyConsiderToSenderStableNodes()) {
                        return node.getRoutingLayer().isStable() && !node.isSinkNode();
                    }
                    return !node.isSinkNode();
                }
            });

            int sinknodes = simulation.getNumberOfSinkNodes();
            final List<Node> srcNodes = sourceNodes;

            selectableNodes = inputParameters.isOnlyConsiderToReceiverSinkNodes() ? sinknodes : allNodes;
            nNodes = inputParameters.isPercentOfReceiverNodes() ? (inputParameters.getNumberOfReceiverNodes() * selectableNodes / 100) : (inputParameters.getNumberOfSenderNodes());

            receiverNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    if (inputParameters.isOnlyConsiderToReceiverSinkNodes()) {
                        return node.isSinkNode() && !srcNodes.contains(node);
                    }
                    return true;
                }
            });
            selectableNodes = inputParameters.isOnlyConsiderToSenderStableNodes() ? stableNodes : allNodes;
            nNodes = inputParameters.isPercentOfAttackNodes() ? (inputParameters.getNumberOfAttackNodes() * selectableNodes / 100) : (inputParameters.getNumberOfAttackNodes());
            final List src = sourceNodes;
            attackNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    if (inputParameters.isOnlyConsiderToAttackStableNodes()) {
                        return node.getRoutingLayer().isStable() && !node.isSinkNode() && !src.contains(node);
                    }
                    return !node.isSinkNode();
                }
            });

            for (Object s : sourceNodes) {
                Node srcNode = (Node) s;

                for (Object r : receiverNodes) {
                    Node rcvNode = (Node) r;
                    testTime += (interval * Simulator.ONE_SECOND);
                    for (int i = 0; i < inputParameters.getNumberOfRetransmissions(); i++) {
                        DefaultTestExecutionEvent event = createEvent(srcNode, rcvNode, testTime);
                        testEvents.add(event);
                    }
                }
            }

            for (Object a : attackNodes) {
                Node attackedNode = (Node) a;
                attackedNode.getRoutingLayer().isUnderAttack();
                LinkedList<AttacksEntry> list =attackedNode.getRoutingLayer().getAttacks().getAttacksList();
                for (AttacksEntry attacksEntry : list) {
                    attacksEntry.getAttack().enable();
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
        event.setMessage(m);
        event.setSourceNode(srcNode);
        event.setDestNode(rcvNode);
        event.setTime(time);

        event.setTest(this);
        event.setTime(messageCounter);
        return event;
    }

    class DefaultTestExecutionEvent extends TestExecutionEvent {

        protected Node sourceNode;
        protected Node destNode;

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
            sourceNode.sendMessage(getMessage());
        }
    }

    class DefaultTestMessage extends TestMessage {

        public DefaultTestMessage() {
            setPayload("TEST".getBytes());
        }

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
