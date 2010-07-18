package org.mei.securesim.core.nodes.components;

import org.mei.securesim.core.energy.EnergyConsumptionAction;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author POSilva
 */
public class CPU extends NodeComponent {

    public CPU(Node node) {
        super(node);
    }

    public void execute(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        long rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeProcessing(rate);
        switchOFF();
    }

    @Override
    protected void consumeTransitionToONEnergy() {
        getNode().getBateryEnergy().consumeCPUTransitionToON();

    }

    public void executeEncryption(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        int rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeEncryption(rate);
        switchOFF();
    }

    public void executeDecryption(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        int rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeDecryption(rate);
        switchOFF();
    }

    public void executeSignature(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        long rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeSignature(rate);
        switchOFF();
    }

    public void executeVerifySignature(EnergyConsumptionAction action) {
        switchON();
        action.execute();
        long rate = action.getNumberOfUnits();
        getNode().getBateryEnergy().consumeSignatureVerify(rate);
        switchOFF();
    }
}
