/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.energy;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class EnergyConsumptionAction {

    Object result;

    /**
     *
     * @return
     */
    public Object getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     *
     */
    public abstract void execute();

    /**
     *
     * @return
     */
    public abstract int getNumberOfUnits();
}
