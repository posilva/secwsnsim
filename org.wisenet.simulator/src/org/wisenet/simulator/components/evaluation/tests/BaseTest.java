package org.wisenet.simulator.components.evaluation.tests;

import org.wisenet.simulator.components.evaluation.tests.events.TestEndEvent;
import org.wisenet.simulator.components.evaluation.tests.events.TestExecutionEvent;
import org.wisenet.simulator.components.evaluation.tests.events.TestStartEvent;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.core.Event;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author posilva
 */
public class BaseTest extends AbstractTest {

    /**
     *
     * @param inputParameters
     */
    public BaseTest(TestInputParameters inputParameters) {
        super(inputParameters);
    }

    /**
     *
     */
    public BaseTest() {
        super();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean verifyPreConditions() {
        return true;
    }

    /**
     *
     */
    @Override
    public void beforeStart() {
    }

    /**
     *
     */
    @Override
    public void afterFinish() {
    }

    /**
     *
     */
    @Override
    public void execute() {
        if (getSimulation() == null) {
            log("no simulation defined");
            return;
        }
        testEvents.clear();
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

        // registers test events into the simulator
        if (testEvents.size() > 2) {
            log("insert " + testEvents.size() + " events into the simulator");
            for (Event event : testEvents) {
                getSimulation().getSimulator().addEvent(event);
            }
        }
    }

    /**
     * 
     */
    private void buildTestConditions() {
        // create events to send messages
        if (!sourceNodes.isEmpty()) {
            for (int k = 0; k < inputParameters.getNumberOfMessagesPerNode(); k++) {
                for (Object s : sourceNodes) {
                    Node srcNode = (Node) s;
                    for (Object r : receiverNodes) {
                        Node rcvNode = (Node) r;
                        testTime += (inputParameters.getIntervalBetweenMessagesSent() * Simulator.ONE_SECOND);
                        for (int i = 0; i < inputParameters.getNumberOfRetransmissions(); i++) {
                            DefaultTestExecutionEvent event = createEvent(srcNode, rcvNode, testTime, i > 0);
                            testEvents.add(event);
                        }
                    }
                }
            }
        }

    }

    private Message createTestMessage(Node s, Node r, boolean retransmission) {
        DefaultTestMessage m = new DefaultTestMessage();
        m.setSourceId(s.getUniqueID());
        m.setDestinationId(r.getUniqueID());
        if (!retransmission) {
            messageCounter++;
        }
        m.setUniqueId(messageCounter);
        return m;
    }

    private DefaultTestExecutionEvent createEvent(Node srcNode, Node rcvNode, long time, boolean retransmission) {
        DefaultTestExecutionEvent event = new DefaultTestExecutionEvent();
        Message m = createTestMessage(srcNode, rcvNode, retransmission);
        event.setMessage(m);
        event.setSourceNode(srcNode);
        event.setDestNode(rcvNode);
        event.setTime(time);
        event.setTest(this);
        return event;
    }

    /**
     *
     */
    @Override
    public void prepare() {
        prepared = false;
        /* prepare test pre-conditions*/
        int stableNodes = simulation.getRoutingLayerController().getTotalStableNodes();
        int allNodes = simulation.getSimulator().getNodes().size();
        int nNodes = 0;
        int selectableNodes = 0;
        for (Object node : simulation.getSimulator().getNodes()) {
            Node n = (Node) node;
            n.setSource(false);
            n.setReceiver(false);
            n.getRoutingLayer().setUnderAttack(false);

        }
        /* prepare receiver nodes */
        if (inputParameters.getNumberOfReceiverNodes() > 0) {
            int sinknodes = simulation.getNumberOfSinkNodes();

            selectableNodes = inputParameters.isOnlyConsiderToReceiverSinkNodes() ? sinknodes : allNodes;
            nNodes = inputParameters.isPercentOfReceiverNodes() ? (inputParameters.getNumberOfReceiverNodes() * selectableNodes / 100) : (inputParameters.getNumberOfReceiverNodes());
            receiverNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    if (inputParameters.isOnlyConsiderToReceiverSinkNodes()) {
                        return node.isSinkNode();
                    }
                    return true;
                }
            });
            for (Object node : receiverNodes) {
                Node n = (Node) node;
                n.setReceiver(true);
            }
            log("Selected " + receiverNodes.size() + " Sensors  as Receiver");

            /* prepare attacked nodes */
            if (inputParameters.getNumberOfAttackNodes() > 0) {
                selectableNodes = inputParameters.isOnlyConsiderToSenderStableNodes() ? stableNodes : allNodes;
                nNodes = inputParameters.isPercentOfAttackNodes() ? (inputParameters.getNumberOfAttackNodes() * selectableNodes / 100) : (inputParameters.getNumberOfAttackNodes());
                attackNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                    public boolean select(Node node) {
                        if (inputParameters.isOnlyConsiderToAttackStableNodes()) {
                            return node.getRoutingLayer().isStable() && !node.isSinkNode() && !receiverNodes.contains(node);
                        }
                        return !node.isSinkNode() && !receiverNodes.contains(node);

                    }
                });
            }

            log("Selected " + attackNodes.size() + " Sensors  as Attacked");
            if (!this.getInputParameters().getAttackSelected().equals("None")) {
                /* Enable attacks in the selected nodes */
                for (Object a : attackNodes) {
                    Node attackedNode = (Node) a;
                    attackedNode.getRoutingLayer().setUnderAttack(true);
                    for (int i = 0; i < attackedNode.getRoutingLayer().getAttacks().getAttacksList().size(); i++) {
                        if (attackedNode.getRoutingLayer().getAttacks().getAttacksList().get(i).getLabel().toLowerCase().equals(getInputParameters().getAttackSelected().toLowerCase())) {
                            attackedNode.getRoutingLayer().getAttacks().getAttacksList().get(i).setEnable(true);
                        }
                    }
                }
            }
            /* Prepare sender nodes */
            if (inputParameters.getNumberOfSenderNodes()
                    > 0) {
                selectableNodes = inputParameters.isOnlyConsiderToSenderStableNodes() ? stableNodes : allNodes;
                nNodes = inputParameters.isPercentOfSenderNodes() ? (inputParameters.getNumberOfSenderNodes() * selectableNodes / 100) : (inputParameters.getNumberOfSenderNodes());
                sourceNodes = getSimulation().selectRandomNodes(nNodes, new NodeSelectionCondition() {

                    public boolean select(Node node) {
                        if (inputParameters.isOnlyConsiderToSenderStableNodes()) {
                            return node.getRoutingLayer().isStable() && !node.isSinkNode() && !attackNodes.contains(node) && !receiverNodes.contains(node);
                        }
                        return !node.isSinkNode() && !attackNodes.contains(node) && !receiverNodes.contains(node);
                    }
                });
                for (Object node : sourceNodes) {
                    Node n = (Node) node;
                    n.setSource(true);
                }
            }

            log("Selected "
                    + sourceNodes.size() + " Sensors  as Senders");

        } else {
            throw new IllegalStateException("No receivers defined. Cannot prepare test conditions");


        }
        prepared = true;
    }

    class DefaultTestExecutionEvent
            extends TestExecutionEvent {

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

    class DefaultTestMessage extends Message {

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
