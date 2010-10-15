/**
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.Parameterizable;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;
import org.wisenet.simulator.common.Persistent;
import org.wisenet.simulator.components.evaluation.EvaluationManager;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.core.Event;
import org.wisenet.simulator.core.Simulator;

/**
 * This class represents a test to be executed by the simulation
 * A test intends to be a procedure that receives input parameters and
 * generates a output result.
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class AbstractTest extends PersistantObject implements Persistent, Parameterizable {

    protected boolean prepared = false;
    protected boolean debugEnabled = true;
    protected static String PREFIX_CFG = "test";
    protected static int messageCounter = 0;
    protected String name;
    protected String description;
    protected TestTypeEnum type;
    protected TestInputParameters inputParameters;
    protected TestOutputParameters outputParameters;
    protected Simulation simulation;
    protected boolean executed = false;
    protected boolean executing = false;
    protected boolean enabled = true;
    protected ObjectParameters parameters = new TestParameters();
    protected Simulation activeSimulation;
    private EvaluationManager evaluationManager;
    protected long startSimulationTime = 0;
    protected long endSimulationTime = 0;
    protected boolean batchMode = true;
    protected List sourceNodes = new LinkedList();
    protected List receiverNodes = new LinkedList();
    protected List attackNodes = new LinkedList();
    protected List<Event> testEvents = new LinkedList<Event>();
    long testTime = 0;

    public AbstractTest() {
        inputParameters = new TestInputParameters();
        outputParameters = new TestOutputParameters(inputParameters);
    }

    public AbstractTest(TestInputParameters inputParameters) {
        this.inputParameters = inputParameters;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public TestInputParameters getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(TestInputParameters inputParameters) {
        this.inputParameters = inputParameters;
    }

    public TestOutputParameters getOutputParameters() {
        return outputParameters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestTypeEnum getType() {
        return type;
    }

    public void setType(TestTypeEnum type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExecuted() {
        return executed;
    }

    public boolean isExecuting() {
        return executing;
    }

    public ObjectParameters getParameters() {
        return parameters;
    }

    public void setParameters(ObjectParameters params) {
        this.parameters = params;
    }

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        configuration.setProperty(PREFIX_CFG + ".name", name);
        configuration.setProperty(PREFIX_CFG + ".description", description);
        configuration.setProperty(PREFIX_CFG + ".debug", debugEnabled);
        configuration.setProperty(PREFIX_CFG + ".batch", batchMode);
        inputParameters.saveToXML(configuration);
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        setName(configuration.getString(PREFIX_CFG + ".name"));
        setDescription(configuration.getString(PREFIX_CFG + ".description"));
        setDebugEnabled(configuration.getBoolean(PREFIX_CFG + ".debug"));
        setBatchMode(configuration.getBoolean(PREFIX_CFG + ".batch"));
        inputParameters.loadFromXML(configuration);
    }

    public abstract boolean verifyPreConditions();

    public abstract void beforeStart();

    public abstract void afterFinish();

    public abstract void execute();

    public abstract void prepare();

    public void run() {
        if (!prepared) {
            prepare();
        }
        if (prepared) {
            execute();
        }
    }

    public void setEvaluationManager(EvaluationManager evaluationManager) {
        this.evaluationManager = evaluationManager;
    }

    public EvaluationManager getEvaluationManager() {
        return evaluationManager;
    }

    public void beginTest() {
        startSimulationTime = Simulator.getSimulationTime();
        log("activating");
        setEvaluationManager(new EvaluationManager());
        getSimulation().getRoutingLayerController().setActiveTest(this);
        getEvaluationManager().startTest(this);
        getSimulation().notifyStartTest(this);
    }

    public void endTest() {
        endSimulationTime = Simulator.getSimulationTime();
        if (getEvaluationManager() != null) {
            log("deactivating");
            getEvaluationManager().endTest();
            getSimulation().notifyEndTest(this);
        }

    }

    /**
     * 
     * @param msg
     */
    protected void log(String msg) {
        if (debugEnabled) {
            System.out.println("[" + Simulator.getSimulationTime() + "]:" + getClass().getSimpleName() + " - " + msg);
        }
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public long getElapsedTime() {
        return endSimulationTime - startSimulationTime;
    }

    public boolean isBatchMode() {
        return batchMode;
    }

    public void setBatchMode(boolean batchMode) {
        this.batchMode = batchMode;
    }

    public List getAttackNodes() {
        return attackNodes;
    }

    public void setAttackNodes(List attackNodes) {
        this.attackNodes = attackNodes;
    }

    public List getReceiverNodes() {
        return receiverNodes;
    }

    public void setReceiverNodes(List receiverNodes) {
        this.receiverNodes = receiverNodes;
    }

    public List getSourceNodes() {
        return sourceNodes;
    }

    public void setSourceNodes(List sourceNodes) {
        this.sourceNodes = sourceNodes;
    }

    public List<Event> getTestEvents() {
        return testEvents;
    }

    public void setTestEvents(List<Event> testEvents) {
        this.testEvents = testEvents;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }
}
