/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.simulation;

import org.mei.securesim.core.energy.EnergyModel;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.factories.NodeFactory;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.core.network.Network;
import org.mei.securesim.components.simulation.basic.BasicSimulation;

/**
 *
 * @author posilva
 */
public class SimulationFactory {

    Class radioModelClass = null;
    Class simulatorClass = null;
    Class networkClass = null;
    Class nodeFactoryClass = null;
    String simulationName = "none";
    String simulationdescription = "none";
    private EnergyModel energyModel;

    public Class getNetworkClass() {
        return networkClass;
    }

    public void setNetworkClass(Class networkClass) throws InstantiationException, IllegalAccessException {
        Object o = networkClass.newInstance();
        if (o instanceof Network) {
            this.networkClass = networkClass;
        } else {
            throw new IllegalArgumentException("network class must be a sub-class of Network");
        }
    }

    public Class getNodeFactoryClass() {
        return nodeFactoryClass;
    }

    public void setNodeFactoryClass(Class nodeFactoryClass) throws InstantiationException, IllegalAccessException {
        Object o = nodeFactoryClass.newInstance();
        if (o instanceof NodeFactory) {
            this.nodeFactoryClass = nodeFactoryClass;
        } else {
            throw new IllegalArgumentException("node factory class must be a sub-class of NodeFactory");
        }

    }

    public Class getRadioModelClass() {
        return radioModelClass;
    }

    public void setRadioModelClass(Class radioModelClass) throws InstantiationException, IllegalAccessException {
        Object o = radioModelClass.newInstance();
        if (o instanceof RadioModel) {
            this.radioModelClass = radioModelClass;
        } else {
            throw new IllegalArgumentException("radio model class must be a sub-class of RadioModel");
        }
    }

    public Class getSimulatorClass() {
        return simulatorClass;
    }

    public void setSimulatorClass(Class simulatorClass) throws InstantiationException, IllegalAccessException {
        Object o = simulatorClass.newInstance();
        if (o instanceof Simulator) {
            this.simulatorClass = simulatorClass;
        } else {
            throw new IllegalArgumentException("simulator class must be a sub-class of Simulator");
        }

    }

    public String getSimulationName() {
        return simulationName;
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    public String getSimulationdescription() {
        return simulationdescription;
    }

    public void setSimulationdescription(String simulationdescription) {
        this.simulationdescription = simulationdescription;
    }

    public Simulation getNewInstance() throws InstantiationException, IllegalAccessException {
        BasicSimulation s = new BasicSimulation();
        s.setNetwork((Network) networkClass.newInstance());
        s.setSimulator((Simulator) (Simulator) simulatorClass.newInstance());
        Simulator.randomGenerator.reset(); //TODO  Carregar com uma seed
        s.setRadioModel((RadioModel) radioModelClass.newInstance());
        s.setNodeFactory((NodeFactory) nodeFactoryClass.newInstance());
        s.getNodeFactory().setEnergyModel(energyModel);
        s.getNodeFactory().setup();
        s.getNodeFactory().setSimulator(s.getSimulator());
        s.setName(simulationName);

        s.setDescription(simulationdescription);
        return s;
    }

    public void setEnergyModel(EnergyModel energyModel) {
        this.energyModel = energyModel;
    }
}
