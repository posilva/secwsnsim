/**
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.Parameterizable;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;
import org.wisenet.simulator.common.Persistent;
import org.wisenet.simulator.components.evaluation.EvaluationManager;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.core.Simulator;

/**
 * This class represents a test to be executed by the simulation
 * A test intends to be a procedure that receives input parameters and
 * generates a output result.
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class AbstractTest extends PersistantObject implements Persistent, Parameterizable {

    protected boolean debugEnabled = true;
    protected static String PREFIX_CFG = "test";
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
        inputParameters.saveToXML(configuration);
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        setName(configuration.getString(PREFIX_CFG + ".name"));
        setDescription(configuration.getString(PREFIX_CFG + ".description"));
        inputParameters.loadFromXML(configuration);
    }

    public abstract boolean verifyPreConditions();

    public abstract void beforeStart();

    public abstract void afterFinish();

    public abstract void execute();

    public void setEvaluationManager(EvaluationManager evaluationManager) {
        this.evaluationManager = evaluationManager;
    }

    public EvaluationManager getEvaluationManager() {
        return evaluationManager;
    }

    public void activate() {
        log("activating");
        setEvaluationManager(new EvaluationManager());
        getSimulation().getRoutingLayerController().setActiveTest(this);
        getEvaluationManager().startTest(this);
    }

    public void deactivate() {
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
}
