/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.simulation;

import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Mica2SensorNode;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;
import org.wisenet.simulator.core.node.factories.CustomNodeFactory;
import org.wisenet.simulator.core.radio.GaussianRadioModel;
import org.wisenet.simulator.utilities.Utilities; 

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
 public class SimulationFactory {
     

    public static final int DEFAULT_NODE_RANGE = 30;
    Class radioModelClass = null;
    Class simulatorClass = null;
    Class nodeFactoryClass = null;
    /** 
     * This variables enable to load a class from a String name
     */
    String radioModelClassName = null;
    String simulatorClassName = null;
    String nodeFactoryClassName = null;
    protected EnergyModel energyModel;
    String simulationName = "none";
    String simulationdescription = "none";
    int initialNodeRange = DEFAULT_NODE_RANGE;

    public void setNodeFactoryClass(Class nodeFactoryClass) throws InstantiationException, IllegalAccessException {
        Object o = nodeFactoryClass.newInstance();
        if (o instanceof AbstractNodeFactory) {
            this.nodeFactoryClass = nodeFactoryClass;
        } else {
            throw new IllegalArgumentException("node factory class must be a sub-class of NodeFactory");
        }

    }

    /**
     * Sets the radio model class
     * @param radioModelClass
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void setRadioModelClass(Class radioModelClass) throws InstantiationException, IllegalAccessException {
        Object o = radioModelClass.newInstance();
        if (o instanceof RadioModel) {
            this.radioModelClass = radioModelClass;
        } else {
            throw new IllegalArgumentException("radio model class must be a sub-class of RadioModel");
        }
    }

    /**
     * Sets the simulator class 
     * @param simulatorClass
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void setSimulatorClass(Class simulatorClass) throws InstantiationException, IllegalAccessException {
        Object o = simulatorClass.newInstance();
        if (o instanceof Simulator) {
            this.simulatorClass = simulatorClass;
        } else {
            throw new IllegalArgumentException("simulator class must be a sub-class of Simulator");
        }

    }

    /**
     * Sets the Simulation given name
     * @param simulationName
     */
    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    /**
     * Sets the description 
     * @param description
     */
    public void setSimulationDescription(String description) {
        this.simulationdescription = description;
    }

    /**
     * Creates a simulation instance based on class objects of the simulator,
     * radio model and node factory
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public AbstractSimulation getNewInstanceFromClasses() throws InstantiationException, IllegalAccessException {
        Simulation s = new Simulation();
        buildSimulation(s,
                (Simulator) simulatorClass.newInstance(),
                (RadioModel) radioModelClass.newInstance(),
                (AbstractNodeFactory) nodeFactoryClass.newInstance(),
                energyModel, initialNodeRange, simulationName, simulationdescription);
        return s;
    }

    protected static Simulation buildSimulation(Simulation simulation, Simulator sim, RadioModel rm, AbstractNodeFactory nf, EnergyModel em, int nrange, String name, String desc) throws IllegalAccessException, InstantiationException {
        sim.addListener(simulation);
        simulation.setSimulator(sim);
        Simulator.randomGenerator.reset(); //TODO  Carregar com uma seed
        simulation.setRadioModel(rm);
        simulation.setNodeFactory(nf);
        simulation.getNodeFactory().setEnergyModel(em);
        simulation.getNodeFactory().setup();
        simulation.getNodeFactory().setSimulator(simulation.getSimulator());
        simulation.setInitialMaxNodeRange(nrange);
        simulation.setName(name);
        simulation.setDescription(desc);
        return simulation;
    }

    /**
     * Returns a simulation instance from classes names
     * @return
     * @throws Exception
     */
    public AbstractSimulation getNewInstanceFromNames() throws Exception {
        setSimulatorClass((Class) Utilities.loadClass(simulatorClassName));
        setNodeFactoryClass((Class) Utilities.loadClass(nodeFactoryClassName));
        setRadioModelClass((Class) Utilities.loadClass(radioModelClassName));
        return getNewInstanceFromClasses();
    }

    /**
     * Sets the energy model instance
     * @param energyModel
     */
    public void setEnergyModel(EnergyModel energyModel) {
        this.energyModel = energyModel;
    }

    /**
     * Sets the initial node range
     * @return
     */
    public int getNodeRange() {
        return initialNodeRange;
    }

    public void setNodeRange(int nodeRange) {
        this.initialNodeRange = nodeRange;
    }

    public String getNodeFactoryClassName() {
        return nodeFactoryClassName;
    }

    public void setNodeFactoryClassName(String nodeFactoryClassName) {
        this.nodeFactoryClassName = nodeFactoryClassName;
    }

    public String getRadioModelClassName() {
        return radioModelClassName;
    }

    public void setRadioModelClassName(String radioModelClassName) {
        this.radioModelClassName = radioModelClassName;
    }

    public String getSimulatorClassName() {
        return simulatorClassName;
    }

    public void setSimulatorClassName(String simulatorClassName) {
        this.simulatorClassName = simulatorClassName;
    }

    /**
     * 
     * @param routingClass
     * @param macClass
     * @param appClass
     * @return
     * @throws Exception
     */
    public static Simulation getNewDefaultInstanceByClasses(Class routingClass, Class macClass, Class appClass) throws Exception {
        Simulation simulation = new Simulation();
        Simulator s = new Simulator();
        RadioModel rm = new GaussianRadioModel(s);
        EnergyModel em = EnergyModel.getDefaultInstance();
        CustomNodeFactory cnf = new CustomNodeFactory(s);
        cnf.setApplicationClass(appClass);
        cnf.setMacLayer(macClass);
        cnf.setRoutingLayerClass(routingClass);
        cnf.setEnergyModel(em);
        cnf.setNodeClass(Mica2SensorNode.class);
        return buildSimulation(simulation, s, rm, cnf, em, DEFAULT_NODE_RANGE,
                "DefaultSimulation", "Simulation created by default factory");
    }

    /**
     * 
     * @param routingClassName
     * @param macClassName
     * @param appClassName
     * @return
     * @throws Exception
     */
    public static Simulation getNewDefaultInstanceByNames(String routingClassName, String macClassName, String appClassName) throws Exception {
        Simulation simulation = new Simulation();
        Simulator s = new Simulator();
        EnergyModel em = EnergyModel.getDefaultInstance();
        RadioModel rm = new GaussianRadioModel(s);
        CustomNodeFactory cnf = new CustomNodeFactory(s);
        cnf.setApplicationClass(Utilities.loadClass(appClassName));
        cnf.setMacLayer(Utilities.loadClass(macClassName));
        cnf.setRoutingLayerClass(Utilities.loadClass(routingClassName));
        cnf.setEnergyModel(em);
        cnf.setNodeClass(Mica2SensorNode.class);
        return buildSimulation(simulation, s, rm, cnf, em, DEFAULT_NODE_RANGE,
                "DefaultSimulation", "Simulation created by default factory");


    }
}
