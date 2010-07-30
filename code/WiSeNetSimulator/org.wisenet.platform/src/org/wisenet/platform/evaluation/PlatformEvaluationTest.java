/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.platform.evaluation;

import java.util.Collection;
import java.util.Vector;
import org.wisenet.simulator.components.evaluation.EvaluationTestSettings;
import org.wisenet.simulator.components.evaluation.EvaluationTestTypeEnum;
import org.wisenet.simulator.components.evaluation.IEvaluationTest;
import org.wisenet.simulator.components.instruments.IInstrumentHandler;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.components.instruments.SimulationController;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument;
import org.wisenet.simulator.components.instruments.reliability.ReliabilityInstrument;
import org.wisenet.simulator.core.nodes.Node;

/**
 *
 * @author CIAdmin
 */
public class PlatformEvaluationTest implements IEvaluationTest {

    private final EvaluationTestTypeEnum type;

    /**
     * 
     * @param evaluationTestTypeEnum
     * @param settings
     */
    public PlatformEvaluationTest(EvaluationTestTypeEnum evaluationTestTypeEnum, EvaluationTestSettings settings) {
        type = evaluationTestTypeEnum;
        Vector<Node> senderNodes;
        Vector<Node> receiverNodes;
        if (settings != null) {
            switch (evaluationTestTypeEnum) {
                case COVERAGE:
                    if (CoverageInstrument.getInstance().getMessageClass() == null) {
                        throw new IllegalStateException("Coverage message class must be set");
                    }
                    CoverageInstrument.getInstance().reset();
                    CoverageInstrument.getInstance().setAutonumber(true);
                    senderNodes = selectSenders(settings.getNrSenderNodes(), settings.isOnlyStableNodes(), settings.isSenderNodesPercent());
                    receiverNodes = selectReceivers(settings.getNrReceiverNodes(), settings.isOnlySinkNodes(), settings.isReceiverNodesPercent());
                    for (Node node : senderNodes) {
                        CoverageInstrument.getInstance().registerSender((IInstrumentHandler) node.getRoutingLayer());
                    }
                    for (Node node : receiverNodes) {
                        CoverageInstrument.getInstance().registerReceiver((IInstrumentHandler) node.getRoutingLayer());
                    }
                    CoverageInstrument.getInstance().setAutonumber(true);
                    CoverageInstrument.getInstance().setNotifyPanel(true);
                    ((CoverageInstrument) CoverageInstrument.getInstance()).setHowManyMessagesToSentPerSender(settings.getNrMessages());
                    ((CoverageInstrument) CoverageInstrument.getInstance()).setDelayToSentMessages(settings.getDelay());
                    ((CoverageInstrument) CoverageInstrument.getInstance()).setIntervalToSentMessages(settings.getInterval());
                    ((CoverageInstrument) CoverageInstrument.getInstance()).setTimesToSentMessages(settings.getNrRetransmissions());
                    //     CoverageInstrument.getInstance().probe();
                    break;
                case ENERGY:

                    break;
                case LATENCY:
                    break;
                case RELIABILITY:
                    if (ReliabilityInstrument.getInstance().getMessageClass() == null) {
                        throw new IllegalStateException("Reliability message class must be set");
                    }
                    ReliabilityInstrument.getInstance().reset();
                    ReliabilityInstrument.getInstance().setAutonumber(true);
                    senderNodes = selectSenders(settings.getNrSenderNodes(), settings.isOnlyStableNodes(), settings.isSenderNodesPercent());
                    receiverNodes = selectReceivers(settings.getNrReceiverNodes(), settings.isOnlySinkNodes(), settings.isReceiverNodesPercent());
                    for (Node node : senderNodes) {
                        ReliabilityInstrument.getInstance().registerSender((IInstrumentHandler) node.getRoutingLayer());
                    }
                    for (Node node : receiverNodes) {
                        ReliabilityInstrument.getInstance().registerReceiver((IInstrumentHandler) node.getRoutingLayer());
                    }
                    ReliabilityInstrument.getInstance().setAutonumber(true);
                    ReliabilityInstrument.getInstance().setNotifyPanel(true);
                    ((ReliabilityInstrument) ReliabilityInstrument.getInstance()).setHowManyMessagesToSentPerSender(settings.getNrMessages());
                    ((ReliabilityInstrument) ReliabilityInstrument.getInstance()).setDelayToSentMessages(settings.getDelay());
                    ((ReliabilityInstrument) ReliabilityInstrument.getInstance()).setIntervalToSentMessages(settings.getInterval());
                    ((ReliabilityInstrument) ReliabilityInstrument.getInstance()).setTimesToSentMessages(settings.getNrRetransmissions());
//                    ReliabilityInstrument.getInstance().probe();
                    break;
            }
        }
    }

    private Vector<Node> selectSenders(int nrNodes, final boolean onlyStable, boolean percent) {
        int numNodes = nrNodes;
        if (percent) { // TOD: Get the number of stable nodes
            numNodes = nrNodes * 100 / SimulationController.getInstance().getSimulation().getSimulator().getNodes().size();
        }

        Collection<Node> nodes = SimulationController.getInstance().selectRandomNodes(numNodes, new NodeSelectionCondition() {

            public boolean select(Node node) {
                if (onlyStable) {
                    return node.getRoutingLayer().isStable();
                } else {
                    return true;
                }
            }
        });
        return new Vector<Node>(nodes);
    }

    private Vector<Node> selectReceivers(int nrNodes, final boolean onlySink, boolean percent) {
        int numNodes = nrNodes;
        if (percent) {
            numNodes = nrNodes * 100 / SimulationController.getInstance().getSimulation().getSimulator().getNodes().size();
        }

        Collection<Node> nodes = SimulationController.getInstance().selectRandomNodes(numNodes, new NodeSelectionCondition() {

            public boolean select(Node node) {
                if (onlySink) {
                    return node.isSinkNode();
                } else {
                    return true;
                }
            }
        });
        return new Vector<Node>(nodes);
    }

    public void test() {
        switch (type) {
            case COVERAGE:
                CoverageInstrument.getInstance().probe();
                break;
            case ENERGY:

                break;
            case LATENCY:
                break;
            case RELIABILITY:

                ReliabilityInstrument.getInstance().probe();

                break;
        }
    }

    public Object getResult() {
        switch (type) {
            case COVERAGE:
                return ((CoverageInstrument) CoverageInstrument.getInstance()).getLastProbeResult();
            case ENERGY:

                break;
            case LATENCY:
                break;
            case RELIABILITY:
                return ((ReliabilityInstrument) ReliabilityInstrument.getInstance()).getLastProbeResult();
        }
        return null;

    }
}
