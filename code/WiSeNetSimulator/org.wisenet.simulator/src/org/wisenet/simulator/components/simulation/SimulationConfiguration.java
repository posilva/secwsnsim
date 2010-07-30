package org.wisenet.simulator.components.simulation;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import org.wisenet.simulator.components.configuration.Configuration;
import org.wisenet.simulator.core.nodes.Node;

public class SimulationConfiguration extends Configuration {

    public final static Logger LOGGER = Logger.getLogger(SimulationConfiguration.class.toString());

    @Override
    protected void init() {

    }

    protected void saveNodes(XMLConfiguration c, Simulation simulation) {
        c.addProperty("simulation.network.class", simulation.getNetwork().getClass().getName());
        c.addProperty("simulation.network.nodes", "");
        for (Node node : simulation.getNetwork().getNodeDB().nodes()) {
            c.addProperty("simulation.network.nodes.node(-1).class", node.getClass().getName());
            c.addProperty("simulation.network.nodes.node.sink", node.isSinkNode());
            c.addProperty("simulation.network.nodes.node.id", node.getId());
            c.addProperty("simulation.network.nodes.node.turnedOn", node.getId());
            c.addProperty("simulation.network.nodes.node.posx", node.getX());
            c.addProperty("simulation.network.nodes.node.posy", node.getY());
            c.addProperty("simulation.network.nodes.node.posz", node.getZ());
            c.addProperty("simulation.network.nodes.node.application.class", node.getRoutingLayer().getClass().getName());
            c.addProperty("simulation.network.nodes.node.routing.class", node.getRoutingLayer().getClass().getName());
            c.addProperty("simulation.network.nodes.node.mac.class", node.getRoutingLayer().getClass().getName());
            c.addProperty("simulation.network.nodes.node.application.class", node.getRoutingLayer().getClass().getName());
            c.addProperty("simulation.network.nodes.node.battery.class", node.getRoutingLayer().getClass().getName());
        }
    }

    public boolean save(Simulation simulation, String simulationFile) {
        try {

            XMLConfiguration c;

            File configFile = new File(simulationFile);
            // if the file exists deleted
            configFile.delete();

            c = new XMLConfiguration();

            saveSimulationInfo(simulation, c);

            saveNodes(c, simulation);

            c.save(configFile);

        } catch (ConfigurationException ex) {
            Logger.getLogger(SimulationConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * Save the simulation relative information
     * @param simulation
     * @param c
     */
    private void saveSimulationInfo(Simulation simulation, XMLConfiguration c) {
        c.addProperty("simulation.name", simulation.getName());
        c.addProperty("simulation.seed", simulation.getSeed());
        c.addProperty("simulation.description", simulation.getDescription());
        c.addProperty("simulation.simulator.class", simulation.getSimulator().getClass().getName());
        c.addProperty("simulation.nodefactory.class", "");
        c.addProperty("simulation.radiomodel.class", "");
        c.addProperty("simulation.network", "");
        c.addProperty("simulation.nodeRange", simulation.getNodeRange());
    }
}
