/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import org.wisenet.simulator.components.evaluation.tests.events.TestStartEvent;
import org.wisenet.simulator.components.evaluation.tests.events.TestEndEvent;
import org.wisenet.simulator.components.evaluation.tests.events.TestExecutionEvent;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ReliabilityTest extends AbstractTest {

    public ReliabilityTest(TestInputParameters inputParameters) {
        super(inputParameters);
    }

    public ReliabilityTest() {
        super();
    }

    @Override
    public boolean verifyPreConditions() {
        return true;
    }

    @Override
    public void beforeStart() {
        System.out.println("Execution of the " + getName() + " test is going to start");
    }

    @Override
    public void afterFinish() {
        System.out.println("Execution of the " + getName() + " test finished");
        // collects results info
    }

    @Override
    public void execute() {
        // Starts Test
        TestStartEvent startEvent = new TestStartEvent();
        startEvent.setTest(this);
        getSimulation().getSimulator().addEvent(startEvent);

        // Build test conditions



        // Ends Test
        TestEndEvent endEvent = new TestEndEvent();
        endEvent.setTest(this);
        getSimulation().getSimulator().addEvent(endEvent);

    }

    public void saveToXML(String file) throws PersistantException {
    }

    public void loadFromXML(String file) throws PersistantException {
    }

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
    }

    class ReliabilityTestExecutionEvent extends TestExecutionEvent {

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
    class ReliabilityTestMessage extends TestMessage{

        @Override
        public Object getSourceId() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object getDestinationId() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long getUniqueId() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setSourceId(Object id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setDestinationId(Object id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setUniqueId(long id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
