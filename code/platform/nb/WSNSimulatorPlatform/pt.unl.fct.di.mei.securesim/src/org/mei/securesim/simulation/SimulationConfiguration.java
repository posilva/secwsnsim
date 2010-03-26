package org.mei.securesim.simulation;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import org.mei.securesim.configuration.Configuration;
import org.mei.securesim.core.nodes.Node;

public class SimulationConfiguration extends Configuration {

   
    public final static Logger LOGGER = Logger.getLogger(SimulationConfiguration.class.toString());
   
    @Override
    protected void init() {
   
    }

    public void saveNodes(XMLConfiguration c, Simulation simulation) {
        c.addProperty("simulation.network.class", simulation.getNetwork().getClass().getName());
        c.addProperty("simulation.network.nodes", "");
        for (Node node : simulation.getNetwork().getNodeDB().nodes()) {
            c.addProperty("simulation.network.nodes.node(-1).class", node.getClass().getName());
            c.addProperty("simulation.network.nodes.node.id", node.getId());
            c.addProperty("simulation.network.nodes.node.posx", node.getX());
            c.addProperty("simulation.network.nodes.node.posy", node.getY());
            c.addProperty("simulation.network.nodes.node.posz", node.getZ());
            c.addProperty("simulation.network.nodes.node.application.class", node.getRoutingLayer().getClass().getName());
            c.addProperty("simulation.network.nodes.node.routing.class", node.getRoutingLayer().getClass().getName());
        }
    }

    public void save(Simulation simulation) {


        XMLConfiguration c;
        try {

            File configFile = new File("Config.xml");
            configFile.delete();
            c = new XMLConfiguration();
            c.addProperty("simulation.name", simulation.getName());
            c.addProperty("simulation.simulator.class", simulation.getSimulator().getClass().getName());
            c.addProperty("simulation.topology.class", "");
            c.addProperty("simulation.radiomodel.class", "");
            c.addProperty("simulation.nodefactory.class", "");
            c.addProperty("simulation.area", "");
            c.addProperty("simulation.area.width", 0);
            c.addProperty("simulation.area.heigth", 0);
            c.addProperty("simulation.area.maxelevation", 0);

            c.addProperty("simulation.network", "");
            saveNodes(c, simulation);




            c.save(configFile);


        } catch (ConfigurationException ex) {
            Logger.getLogger(SimulationConfiguration.class.getName()).log(Level.SEVERE, null, ex);



//            XMLWriter writer=new XMLWriter("");
//
//
//
//
//
//        writer.openTag(SIMULATION_TAG);
//        writer.openTag(CONFIGURATION_TAG);
//        writer.writeTag(NAME_TAG, this.name);
//        writer.openTag(AREA_TAG);
//        writer.writeTag(WIDTH_TAG, ""+this.simulationAreaWidth);
//
//        writer.closeTag(AREA_TAG);
//        writer.openTag(NETWORK_TAG);
//
//        writer.closeTag(NETWORK_TAG);
//        writer.closeTag(CONFIGURATION_TAG);
//        writer.closeTag(SIMULATION_TAG);
//






        }
    }
}
