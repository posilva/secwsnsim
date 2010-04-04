/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.nodes.components;

import org.mei.securesim.core.energy.EnergyConsumptionAction;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class Transceiver extends NodeComponent{

    public Transceiver(Node node) {
        super(node);
    }

    protected void consumeTransitionToONEnergy() {
        getNode().getBateryEnergy().consumeTXTransitionToON();
    }

    public void executeTransmission(EnergyConsumptionAction action  ){
        switchON();
        action.execute();
        int rate= action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeTransmission(rate);//end-start
        switchOFF();
    }
    public void executeReception(EnergyConsumptionAction action  ){
        switchON();
        action.execute();
        int rate= action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeTransmission(rate);//end-start
        switchOFF();
    }
}
