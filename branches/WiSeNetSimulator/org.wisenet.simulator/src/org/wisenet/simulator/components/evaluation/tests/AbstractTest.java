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
import org.wisenet.simulator.components.simulation.Simulation;

/**
 * This class represents a test to be executed by the simulation
 * A test intends to be a procedure that receives input parameters and
 * generates a output result.
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class AbstractTest extends PersistantObject implements Persistent, Parameterizable {

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

    public AbstractTest() {
        inputParameters= new TestInputParameters();
        outputParameters= new TestOutputParameters(inputParameters);
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

    public Simulation getActiveSimulation() {
        return activeSimulation;
    }

    public void setActiveSimulation(Simulation activeSimulation) {
        this.activeSimulation = activeSimulation;
    }

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        inputParameters.saveToXML(configuration);
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        inputParameters.loadFromXML(configuration);
    }

    public abstract boolean verifyPreConditions();

    public abstract void beforeStart();

    public abstract void afterFinish();

    public abstract void execute();
}
