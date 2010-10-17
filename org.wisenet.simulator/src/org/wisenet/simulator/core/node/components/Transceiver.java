/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.components;

import org.wisenet.simulator.core.energy.EnergyConsumptionAction;
import org.wisenet.simulator.core.node.Node;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class Transceiver extends NodeComponent {

    /**
     *
     * @param node
     */
    public Transceiver(Node node) {
        super(node);
    }

    /**
     *
     */
    protected void consumeTransitionToONEnergy() {
        getNode().getBateryEnergy().consumeTXTransitionToON();
    }

    /**
     *
     * @param action
     */
    public void executeTransmission(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        int rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeTransmission(rate);//end-start
        switchOFF();
    }

    /**
     *
     * @param action
     */
    public void executeReception(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        int rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeReceiving(rate);//end-start
        switchOFF();
    }
}
