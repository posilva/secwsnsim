/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.core.node.components;

import org.wisenet.simulator.core.energy.EnergyConsumptionAction;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author posilva
 */
public class Transceiver extends NodeComponent {

    public Transceiver(Node node) {
        super(node);
    }

    protected void consumeTransitionToONEnergy() {
        getNode().getBateryEnergy().consumeTXTransitionToON();
    }

    public void executeTransmission(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        int rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeTransmission(rate);//end-start
        switchOFF();
    }

    public void executeReception(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        int rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeReceiving(rate);//end-start
        switchOFF();
    }
}
