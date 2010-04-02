/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.energy;

/**
 *
 * @author posilva
 */
public abstract class EnergyConsumption {

    protected double consumptionRate = 1.0;

    public EnergyConsumption(double rate) {
        this.consumptionRate = rate;

    }

    public EnergyConsumption() {
        consumptionRate = 1.0;
    }

    public double getConsumptionRate() {
        return consumptionRate;
    }

    public void setConsumptionRate(double consumptionRate) {
        this.consumptionRate = consumptionRate;
    }

    public abstract void execute();
}
