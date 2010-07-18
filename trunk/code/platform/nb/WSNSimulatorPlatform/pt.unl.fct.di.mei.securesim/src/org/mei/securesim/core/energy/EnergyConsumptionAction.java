/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.energy;

/**
 *
 * @author posilva
 */
public abstract class EnergyConsumptionAction {

    Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public abstract void execute();

    public abstract int getNumberOfUnits();
}
