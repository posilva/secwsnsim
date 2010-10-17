package org.wisenet.simulator.core.node.components;

import org.wisenet.simulator.core.energy.EnergyConsumptionAction;
import org.wisenet.simulator.core.node.Node;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class CPU extends NodeComponent {

    /**
     *
     * @param node
     */
    public CPU(Node node) {
        super(node);
    }

    /**
     *
     * @param action
     */
    public void execute(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        long rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeProcessing(rate);
        switchOFF();
    }

    /**
     *
     */
    @Override
    protected void consumeTransitionToONEnergy() {
        getNode().getBateryEnergy().consumeCPUTransitionToON();

    }

    /**
     *
     * @param action
     */
    public void executeEncryption(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        int rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeEncryption(rate);
        switchOFF();
    }

    /**
     *
     * @param action
     */
    public void executeDecryption(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        int rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeDecryption(rate);
        switchOFF();
    }

    /**
     *
     * @param action
     */
    public void executeSignature(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        long rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeSignature(rate);
        switchOFF();
    }

    /**
     *
     * @param action
     */
    public void executeVerifySignature(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        long rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeSignatureVerify(rate);
        switchOFF();
    }
}
