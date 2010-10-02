/**
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import org.wisenet.simulator.components.simulation.Simulation;

/**
 * This class represents a test to be executed by the simulation
 * A test intends to be a procedure that receives input parameters and
 * generates a output result.
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class AbstractTest {

    protected String name;
    protected TestTypeEnum type;
    protected String description;
    protected TestInputParameters inputParameters;
    protected TestOutputParameters outputParameters;
    protected Simulation simulation;
    protected boolean executed = false;
    protected boolean executing = false;

    protected boolean enabled=true;

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

    public abstract void load(String filename);

    public abstract void save(String filename);

    public abstract boolean verifyPreConditions();

    public abstract void beforeStart();

    public abstract void afterFinish();

    public abstract void execute();
}
