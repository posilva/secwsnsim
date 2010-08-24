/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.platform.evaluation;

import java.util.Collection;
import java.util.Vector;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.simulator.components.evaluation.EvaluationTestSettings;
import org.wisenet.simulator.components.evaluation.EvaluationTestTypeEnum;
import org.wisenet.simulator.components.evaluation.IEvaluationTest;
import org.wisenet.simulator.components.instruments.IInstrumentHandler;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument;
import org.wisenet.simulator.components.instruments.reliability.ReliabilityInstrument;
import org.wisenet.simulator.core.node.Node;

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
                    if (getCoverageInstrument().getMessageClass() == null) {
                        throw new IllegalStateException("Coverage message class must be set");
                    }
                    getCoverageInstrument().reset();
                    getCoverageInstrument().setAutonumber(true);
                    senderNodes = selectSenders(settings.getNrSenderNodes(), settings.isOnlyStableNodes(), settings.isSenderNodesPercent());
                    receiverNodes = selectReceivers(settings.getNrReceiverNodes(), settings.isOnlySinkNodes(), settings.isReceiverNodesPercent());
                    for (Node node : senderNodes) {
                        getCoverageInstrument().registerSender((IInstrumentHandler) node.getRoutingLayer());
                    }
                    for (Node node : receiverNodes) {
                        getCoverageInstrument().registerReceiver((IInstrumentHandler) node.getRoutingLayer());
                    }
                    getCoverageInstrument().setAutonumber(true);
                    getCoverageInstrument().setNotifyPanel(true);
                    ((CoverageInstrument) getCoverageInstrument()).setHowManyMessagesToSentPerSender(settings.getNrMessages());
                    ((CoverageInstrument) getCoverageInstrument()).setDelayToSentMessages(settings.getDelay());
                    ((CoverageInstrument) getCoverageInstrument()).setIntervalToSentMessages(settings.getInterval());
                    ((CoverageInstrument) getCoverageInstrument()).setTimesToSentMessages(settings.getNrRetransmissions());
                    //     getCoverageInstrument().probe();
                    break;
                case ENERGY:

                    break;
                case LATENCY:
                    break;
                case RELIABILITY:
                    if (getReliabilityInstrument().getMessageClass() == null) {
                        throw new IllegalStateException("Reliability message class must be set");
                    }
                    getReliabilityInstrument().reset();
                    getReliabilityInstrument().setAutonumber(true);
                    senderNodes = selectSenders(settings.getNrSenderNodes(), settings.isOnlyStableNodes(), settings.isSenderNodesPercent());
                    receiverNodes = selectReceivers(settings.getNrReceiverNodes(), settings.isOnlySinkNodes(), settings.isReceiverNodesPercent());
                    for (Node node : senderNodes) {
                        getReliabilityInstrument().registerSender((IInstrumentHandler) node.getRoutingLayer());
                    }
                    for (Node node : receiverNodes) {
                        getReliabilityInstrument().registerReceiver((IInstrumentHandler) node.getRoutingLayer());
                    }
                    getReliabilityInstrument().setAutonumber(true);
                    getReliabilityInstrument().setNotifyPanel(true);
                    ((ReliabilityInstrument) getReliabilityInstrument()).setHowManyMessagesToSentPerSender(settings.getNrMessages());
                    ((ReliabilityInstrument) getReliabilityInstrument()).setDelayToSentMessages(settings.getDelay());
                    ((ReliabilityInstrument) getReliabilityInstrument()).setIntervalToSentMessages(settings.getInterval());
                    ((ReliabilityInstrument) getReliabilityInstrument()).setTimesToSentMessages(settings.getNrRetransmissions());
//                    getReliabilityInstrument().probe();
                    break;
            }
        }
    }

    private Vector<Node> selectSenders(int nrNodes, final boolean onlyStable, boolean percent) {
        if (!PlatformManager.getInstance().haveActiveSimulation()) {
            return new Vector<Node>();
        }
        int numNodes = nrNodes;
        if (percent) { // TOD: Get the number of stable nodes
            numNodes = nrNodes * 100 / PlatformManager.getInstance().getActiveSimulation().getSimulator().getNodes().size();
        }

        Collection<Node> nodes = PlatformManager.getInstance().getActiveSimulation().selectRandomNodes(numNodes, new NodeSelectionCondition() {

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
            numNodes = nrNodes * 100 / PlatformManager.getInstance().getActiveSimulation().getSimulator().getNodes().size();
        }

        Collection<Node> nodes = PlatformManager.getInstance().getActiveSimulation().selectRandomNodes(numNodes, new NodeSelectionCondition() {

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
                getCoverageInstrument().probe();
                break;
            case ENERGY:

                break;
            case LATENCY:
                break;
            case RELIABILITY:

                getReliabilityInstrument().probe();

                break;
        }
    }

    public Object getResult() {
        switch (type) {
            case COVERAGE:
                return ((CoverageInstrument) getCoverageInstrument()).getLastProbeResult();
            case ENERGY:

                break;
            case LATENCY:
                break;
            case RELIABILITY:
                return ((ReliabilityInstrument) getReliabilityInstrument()).getLastProbeResult();
        }
        return null;

    }

    private CoverageInstrument getCoverageInstrument() {
        return PlatformManager.getInstance().getActiveSimulation().getCoverageInstrument();
    }

    private ReliabilityInstrument getReliabilityInstrument() {
        return PlatformManager.getInstance().getActiveSimulation().getReliabilityInstrument();
    }
}
