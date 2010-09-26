/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.console;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.simulator.components.output.IOutputDisplay;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.simulation.listeners.SimulationEvent;
import org.wisenet.simulator.components.simulation.listeners.SimulationListener;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.radio.GaussianRadioModel;
import org.wisenet.simulator.temp.insens.INSENSNodeFactory;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationConsole1version {

    private static SimulationSettings createSimulationSettings() {
        SimulationSettings settings = new SimulationSettings();
//        settings.setNumberOfNodes(100); //int numberOfNodes;
//        settings.setFieldHeight(300); //    int fieldHeight;
//        settings.setFieldWidth(300); //    int fieldWidth;
//        settings.setFieldMaxAltitude(0); //    int fieldMaxAltitude;
        settings.setMaxNodeRadioStrength(2300); //    int maxNodeRadioStrength;
        settings.setNodeFactoryClassName(INSENSNodeFactory.class.getName()); //    String  nodeFactoryClassName;
        settings.setSimulatorClassName(Simulator.class.getName()); //    String simulatorClassName;
        settings.setRadioModelClassName(GaussianRadioModel.class.getName()); //    String radioModelClassName;
        settings.setEnergyModelClassName(EnergyModel.class.getName()); //    String energyModelClassName;
//        settings.setTopologyManager(new RandomTopologyManager()); //    TopologyManager topologyManager;
        return settings;
    }

    private static void simulationHandlers(Simulation simulation) {
        simulation.setRoutingOutputDisplay(new IOutputDisplay() {

            public void showOutput(String text) {
                System.out.println("ROUTING OUTPUT: " + text);
            }
        });
        simulation.addSimulationListener(new SimulationListener() {

            public void onStartFailure(SimulationEvent event) {
            }

            public void beforeStart(SimulationEvent event) {
            }

            public void afterStart(SimulationEvent event) {
                System.out.println("Simulation started");
            }

            public void beforeStop(SimulationEvent event) {
            }

            public void onStopFailure(SimulationEvent event) {
            }

            public void afterStop(SimulationEvent event) {
            }

            public void beforeBuildNetwork(SimulationEvent event) {
            }

            public void afterBuildNetwork(SimulationEvent event) {
                System.out.println("Network was builded");
            }

            public void onBuildNetworkFailure(SimulationEvent event) {
            }

            public void onEmptyQueue(SimulationEvent event) {
                Simulation s = ((Simulation) event.getSimulation());
                int i = s.getNumberOfStableNodes();
                System.out.println("Simulation Queue is empty");
                System.out.println("Stable: " + i);
                System.out.println("Average Radio Neighbors Per Node: " + s.getAverageNeighborsPerNode());
                System.out.println("Total Energy Spent" + s.getEnergyController().getDatabase().getTotalEnergySpent());
            }

            public void onNewSimulatorRound(SimulationEvent event) {
            }
        });
    }

    public static void main(String[] args) {
        try {

            SimulationSettings settings = createSimulationSettings();
            SimulationFactory factory = new SimulationFactory(settings);
            Simulation simulation = factory.createNew();
            simulationHandlers(simulation);
            factory.setupSimulationInstance(simulation);
            simulation.saveNetworkTopology("sim1.topology.xml");
//            simulation.start();
//            simulation.stop();

        } catch (Exception ex) {
            Logger.getLogger(SimulationFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
