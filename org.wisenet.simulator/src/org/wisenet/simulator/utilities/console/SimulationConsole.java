/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.console;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.topology.RandomTopologyManager;
import org.wisenet.simulator.components.topology.RandomTopologyParameters;
import org.wisenet.simulator.components.topology.TopologyManager;
import org.wisenet.simulator.components.topology.TopologyParameters;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.radio.GaussianRadioModel;
import org.wisenet.simulator.core.ui.NoDisplay;
import org.wisenet.simulator.temp.insens.INSENSNodeFactory;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationConsole {

    private static SimulationSettings createSimulationSettings() {
        SimulationSettings settings = new SimulationSettings();
        settings.setMaxNodeRadioStrength(20300); //    int maxNodeRadioStrength;
        settings.setNodeFactoryClassName(INSENSNodeFactory.class.getName()); //    String  nodeFactoryClassName;
        settings.setSimulatorClassName(Simulator.class.getName()); //    String simulatorClassName;
        settings.setRadioModelClassName(GaussianRadioModel.class.getName()); //    String radioModelClassName;
        settings.setEnergyModelClassName(EnergyModel.class.getName()); //    String energyModelClassName;
        settings.setSeed(1024);
        settings.setName("Console Simulation");
        settings.setEnvironAttenuation(0);
        settings.setStaticZ(true);
        settings.setMinZ(0);
        settings.setMaxZ(0);
        return settings;
    }
    
    /**
     * 
     * @return
     */
    private static RandomTopologyManager createTopologyManager() {
        TopologyParameters parameters = new RandomTopologyParameters();
        parameters.set("x", 0);
        parameters.set("y", 0);
        parameters.set("maxelevation", 0);
        parameters.set("width", 300);
        parameters.set("height", 300);
        parameters.set("nodes", 200);
        RandomTopologyManager topologyManager = new RandomTopologyManager();
        topologyManager.setParameters(parameters);
        return topologyManager;
    }

    public static void main(String[] args) {
        try {
            Simulation simulation = createSimulation();
            simulation.start();
        } catch (Exception ex) {
            Logger.getLogger(SimulationFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Simulation createSimulation() throws Exception {
        SimulationSettings settings = createSimulationSettings();
        TopologyManager manager = createTopologyManager();
        
        Simulation simulation = new Simulation();
        simulation.setDisplay(new NoDisplay());
        simulation.create(settings);
        simulation.appendNodes(manager);
        return simulation;
    }
}
