/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class TestTopology extends PersistantObject {

    public static String PREFIX_CFG = "attackTopology";
    Simulation simulation = null;

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        if (simulation == null) {
            throw new PersistantException("Simulation instance cannot be null");
        }
        int i = 0;
        for (Node node : simulation.getSimulator().getNodes()) {
            if (node.isSource() || node.isReceiver() || node.getRoutingLayer().isUnderAttack()) {
                configuration.addProperty(PREFIX_CFG + ".node(-1).id", node.getId());
                configuration.addProperty(PREFIX_CFG + ".node.source", node.isSource());
                configuration.addProperty(PREFIX_CFG + ".node.receiver", node.isReceiver());
                configuration.addProperty(PREFIX_CFG + ".node.attacked", node.getRoutingLayer().isUnderAttack());
                i++;
            }
        }
        configuration.addProperty(PREFIX_CFG + ".nodes.size", i);
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        if (simulation == null) {
            throw new PersistantException("Simulation instance cannot be null");
        }

        XMLConfiguration file = configuration;
        int numberOfNodes = file.getInt(PREFIX_CFG + ".nodes.size");

        for (int i = 0; i < numberOfNodes; i++) {
            short id = file.getShort(PREFIX_CFG + ".node(" + i + ").id");
            boolean source = file.getBoolean(PREFIX_CFG + ".node(" + i + ").source");
            boolean receiver = file.getBoolean(PREFIX_CFG + ".node(" + i + ").receiver");
            boolean attacked = file.getBoolean(PREFIX_CFG + ".node(" + i + ").attacked");
            Node n = simulation.getSimulator().findNode(id);
            if (n != null) {
                n.setSource(source);
                n.setReceiver(receiver);
                n.getRoutingLayer().setUnderAttack(attacked);
            }
        }
    }
}
