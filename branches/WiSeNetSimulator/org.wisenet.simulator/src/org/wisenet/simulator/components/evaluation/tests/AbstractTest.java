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

    /**
     *
     */
    protected boolean prepared = false;
    /**
     *
     */
    protected boolean debugEnabled = true;
    /**
     *
     */
    protected static String PREFIX_CFG = "test";
    /**
     *
     */
    protected static int messageCounter = 0;
    /**
     *
     */
    protected String name;
    /**
     *
     */
    protected String description;
    /**
     *
     */
    protected TestTypeEnum type;
    /**
     *
     */
    protected TestInputParameters inputParameters;
    /**
     *
     */
    protected TestOutputParameters outputParameters;
    /**
     *
     */
    protected Simulation simulation;
    /**
     *
     */
    protected boolean executed = false;
    /**
     *
     */
    protected boolean executing = false;
    /**
     *
     */
    protected boolean enabled = true;
    /**
     *
     */
    protected ObjectParameters parameters = new TestParameters();
    /**
     *
     */
    protected Simulation activeSimulation;
    private EvaluationManager evaluationManager;
    /**
     *
     */
    protected long startSimulationTime = 0;
    /**
     *
     */
    protected long endSimulationTime = 0;
    /**
     *
     */
    protected boolean batchMode = true;
    /**
     *
     */
    protected List sourceNodes = new LinkedList();
    /**
     *
     */
    protected List receiverNodes = new LinkedList();
    /**
     *
     */
    protected List attackNodes = new LinkedList();
    /**
     *
     */
    protected List<Event> testEvents = new LinkedList<Event>();
    long testTime = 0;
    private int timesToRun;

    /**
     *
     */
    public AbstractTest() {
        inputParameters = new TestInputParameters();
        outputParameters = new TestOutputParameters(inputParameters);
    }

    /**
     *
     * @param inputParameters
     */
    public AbstractTest(TestInputParameters inputParameters) {
        this.inputParameters = inputParameters;
    }

    /**
     *
     * @return
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     *
     * @param simulation
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     *
     * @return
     */
    public TestInputParameters getInputParameters() {
        return inputParameters;
    }

    /**
     *
     * @param inputParameters
     */
    public void setInputParameters(TestInputParameters inputParameters) {
        this.inputParameters = inputParameters;
    }

    /**
     *
     * @return
     */
    public TestOutputParameters getOutputParameters() {
        return outputParameters;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public TestTypeEnum getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(TestTypeEnum type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @return
     */
    public boolean isExecuted() {
        return executed;
    }

    /**
     *
     * @return
     */
    public boolean isExecuting() {
        return executing;
    }

    /**
     *
     * @return
     */
    public ObjectParameters getParameters() {
        return parameters;
    }

    /**
     *
     * @param params
     */
    public void setParameters(ObjectParameters params) {
        this.parameters = params;
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        configuration.setProperty(PREFIX_CFG + ".name", name);
        configuration.setProperty(PREFIX_CFG + ".description", description);
        configuration.setProperty(PREFIX_CFG + ".debug", debugEnabled);
        configuration.setProperty(PREFIX_CFG + ".batch", batchMode);
        configuration.setProperty(PREFIX_CFG + ".timestorun", timesToRun);
        inputParameters.saveToXML(configuration);
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        setName(configuration.getString(PREFIX_CFG + ".name"));
        setDescription(configuration.getString(PREFIX_CFG + ".description"));
        setDebugEnabled(configuration.getBoolean(PREFIX_CFG + ".debug"));
        setBatchMode(configuration.getBoolean(PREFIX_CFG + ".batch"));
        setTimesToRun(configuration.getInt(PREFIX_CFG + ".timestorun"));
        inputParameters.loadFromXML(configuration);
    }

    /**
     *
     * @return
     */
    public abstract boolean verifyPreConditions();

    /**
     *
     */
    public abstract void beforeStart();

    /**
     *
     */
    public abstract void afterFinish();

    /**
     *
     */
    public abstract void execute();

    /**
     *
     */
    public abstract void prepare();

    /**
     *
     */
    public void run() {
        if (!prepared) {
            prepare();
        }
        if (prepared) {
            execute();
        }
    }

    /**
     *
     * @param evaluationManager
     */
    public void setEvaluationManager(EvaluationManager evaluationManager) {
        this.evaluationManager = evaluationManager;
    }

    /**
     *
     * @return
     */
    public EvaluationManager getEvaluationManager() {
        return evaluationManager;
    }

    /**
     *
     */
    public void beginTest() {
        startSimulationTime = Simulator.getSimulationTime();
        log("activating");
        setEvaluationManager(new EvaluationManager());
        getSimulation().getRoutingLayerController().setActiveTest(this);
        getEvaluationManager().startTest(this);
        getSimulation().notifyStartTest(this);
    }

    /**
     *
     */
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

    /**
     *
     * @return
     */
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    /**
     *
     * @param debugEnabled
     */
    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    /**
     *
     * @return
     */
    public long getElapsedTime() {
        return endSimulationTime - startSimulationTime;
    }

    /**
     *
     * @return
     */
    public boolean isBatchMode() {
        return batchMode;
    }

    /**
     *
     * @param batchMode
     */
    public void setBatchMode(boolean batchMode) {
        this.batchMode = batchMode;
    }

    /**
     *
     * @return
     */
    public List getAttackNodes() {
        return attackNodes;
    }

    /**
     *
     * @param attackNodes
     */
    public void setAttackNodes(List attackNodes) {
        this.attackNodes = attackNodes;
    }

    /**
     *
     * @return
     */
    public List getReceiverNodes() {
        return receiverNodes;
    }

    /**
     *
     * @param receiverNodes
     */
    public void setReceiverNodes(List receiverNodes) {
        this.receiverNodes = receiverNodes;
    }

    /**
     *
     * @return
     */
    public List getSourceNodes() {
        return sourceNodes;
    }

    /**
     *
     * @param sourceNodes
     */
    public void setSourceNodes(List sourceNodes) {
        this.sourceNodes = sourceNodes;
    }

    /**
     *
     * @return
     */
    public List<Event> getTestEvents() {
        return testEvents;
    }

    /**
     *
     * @param testEvents
     */
    public void setTestEvents(List<Event> testEvents) {
        this.testEvents = testEvents;
    }

    /**
     *
     * @return
     */
    public boolean isPrepared() {
        return prepared;
    }

    /**
     *
     * @param prepared
     */
    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }

    public void setTimesToRun(int i) {
        timesToRun = i;
    }

    public int getTimesToRun() {
        return timesToRun;
    }
}
