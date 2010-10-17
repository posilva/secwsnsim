/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.configuration;

import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimualtionConfiguration {

    /**
     * Configuration file name 
     */
    String filename;
    /**
     * Configuration file instance for the simulation
     */
    XMLConfiguration configurationFile = new XMLConfiguration();
    /**
     * Simulation instance
     */
    Simulation simulation;

    /**
     * Load configuration from file
     * @param filename
     */
    public void load(String filename) {
    }

    /**
     * Save configuration for filename
     * @param filename
     */
    public void save(String filename) {
        writeSimualtionInfo();
        writeSimulationNodesInfo();
    }

    /**
     * Save current configuration to file
     */
    public void save() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    private void writeSimualtionInfo() {
        configurationFile.addProperty("simulation.name", simulation.getName());
        configurationFile.addProperty("simulation.description", simulation.getDescription());
        configurationFile.addProperty("simulation.seed", simulation.getSeed());
        configurationFile.addProperty("simulation.radiomodel", simulation.getRadioModel().getClass().getName());
        configurationFile.addProperty("simulation.simulator", simulation.getSimulator().getClass().getName());
        configurationFile.addProperty("simulation.nodefactory", simulation.getNodeFactory().getClass().getName());
        configurationFile.addProperty("simulation.radiostrength", 0);
        writeEnergyModelInfo();
        writeSimulationNodesInfo();

    }

    private void writeEnergyModelInfo() {
        EnergyModel em = simulation.getNodeFactory().getEnergyModel();
        configurationFile.addProperty("simulation.energymodel.class", em.getClass().getName());
        configurationFile.addProperty("simulation.energymodel.TotalEnergy", em.getTotalEnergy());
        configurationFile.addProperty("simulation.energymodel.CpuTransitionToActiveEnergy", em.getCpuTransitionToActiveEnergy());
        configurationFile.addProperty("simulation.energymodel.DecryptEnergy", em.getDecryptEnergy());
        configurationFile.addProperty("simulation.energymodel.DigestEnergy", em.getDigestEnergy());
        configurationFile.addProperty("simulation.energymodel.EncryptEnergy", em.getEncryptEnergy());
        configurationFile.addProperty("simulation.energymodel.IdleEnergy", em.getIdleEnergy());
        configurationFile.addProperty("simulation.energymodel.ProcessingEnergy", em.getProcessingEnergy());
        configurationFile.addProperty("simulation.energymodel.ReceptionEnergy", em.getReceptionEnergy());
        configurationFile.addProperty("simulation.energymodel.SignatureEnergy", em.getSignatureEnergy());
        configurationFile.addProperty("simulation.energymodel.SleepEnergy", em.getSleepEnergy());
        configurationFile.addProperty("simulation.energymodel.TransmissionEnergy", em.getTransmissionEnergy());
        configurationFile.addProperty("simulation.energymodel.TxTransitionToActiveEnergy", em.getTxTransitionToActiveEnergy());
        configurationFile.addProperty("simulation.energymodel.VerifyDigestEnergy", em.getVerifyDigestEnergy());
        configurationFile.addProperty("simulation.energymodel.VerifySignatureEnergy", em.getVerifySignatureEnergy());
    }

    private void writeSimulationNodesInfo() {
        Simulator s = simulation.getSimulator();
        configurationFile.addProperty("simulation.nodes.count", s.getNodes().size());

    }

    /**
     *
     * @param node
     */
    protected void writeNodeInfo(Node node) {
        configurationFile.addProperty("simulation.nodes.node(-1).id", node.getId());
        configurationFile.addProperty("simulation.nodes.node.x", node.getX());
        configurationFile.addProperty("simulation.nodes.node.y", node.getY());
        configurationFile.addProperty("simulation.nodes.node.z", node.getZ());
        configurationFile.addProperty("simulation.nodes.node.radius", node.getRadius());
        configurationFile.addProperty("simulation.nodes.node.routing(-1).class", node.getRoutingLayer().getClass().getName());
        configurationFile.addProperty("simulation.nodes.node.routing.underattack", node.getRoutingLayer().getClass().getName());
        configurationFile.addProperty("simulation.nodes.node.mac", node.getMacLayer().getClass().getName());
        configurationFile.addProperty("simulation.nodes.node.application", node.getApplication().getClass().getName());
        configurationFile.addProperty("simulation.nodes.node.maxradiostrength", node.getConfig().getMaximumRadioStrength());
        configurationFile.addProperty("simulation.nodes.node.sinknode", node.isSinkNode());
        configurationFile.addProperty("simulation.nodes.node.EnableFunctioningEnergyConsumption", node.isEnableFunctioningEnergyConsumption());


    }
}
