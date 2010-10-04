/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.console;

import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.topology.TopologyManager;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.ui.NoDisplay;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationFactory {

    protected ISimulationDisplay defaultDisplay = new NoDisplay();
    protected TopologyManager defaultTopologyManager;
    protected SimulationSettings settings;
    protected AbstractNodeFactory nodeFactoryClassInstance;
    protected Simulator simulatorClassInstance;
    protected RadioModel radioModelClassInstance;
    protected EnergyModel energyModelClassInstance;

    public SimulationFactory(SimulationSettings settings) {
        this.settings = settings;
    }
    /**
     * 
     * @return
     * @throws Exception
     */
    public Simulation createNew() throws Exception {
        createClassesInstances();
        Simulation simulation = new Simulation();
        prepareSimulation(simulation);
        return simulation;


    }
    /**
     * 
     * @param simulation
     * @throws Exception
     */
    public void setupSimulationInstance(Simulation simulation) throws Exception {
        simulation.initialSetup();
        createSimulationNodes(simulation);
    }

    private void createSimulationNodes(Simulation simulation) throws Exception {
        simulation.appendNodes(getDefaultTopologyManager());
    }

    private void prepareSimulation(Simulation simulation) {
        nodeFactoryClassInstance.setNodeMaxRadioRange(settings.getMaxNodeRadioRange());
        simulation.setSimulator(simulatorClassInstance);
        simulation.setRadioModel(radioModelClassInstance);
        simulation.setNodeFactory(nodeFactoryClassInstance);
        simulation.setEnergyModel(energyModelClassInstance.getInstanceWithDefaultValues());
        simulation.setDisplay(getDefaultDisplay());
        simulation.setMode(settings.isFastMode() ? Simulator.FAST : Simulator.REAL);
    }

    private void createClassesInstances() throws Exception {
        this.simulatorClassInstance = (Simulator) Utilities.loadClassInstance(settings.getSimulatorClassName());
        this.radioModelClassInstance = (RadioModel) Utilities.loadClassInstance(settings.getRadioModelClassName());
        this.nodeFactoryClassInstance = (AbstractNodeFactory) Utilities.loadClassInstance(settings.getNodeFactoryClassName());
        this.energyModelClassInstance = (EnergyModel) Utilities.loadClassInstance(settings.getEnergyModelClassName());
        this.nodeFactoryClassInstance.setStaticZ(settings.isStaticZ());
        this.nodeFactoryClassInstance.setMinZ(settings.getMinZ());
        this.nodeFactoryClassInstance.setMaxZ(settings.getMaxZ());
        this.nodeFactoryClassInstance.setEnvironmentAttenuation(settings.getEnvironAttenuation());
    }

    public TopologyManager getDefaultTopologyManager() {
        return defaultTopologyManager;
    }

    public void setDefaultTopologyManager(TopologyManager defaultTopologyManager) {
        this.defaultTopologyManager = defaultTopologyManager;
    }

    public ISimulationDisplay getDefaultDisplay() {
        return defaultDisplay;
    }

    public void setDefaultDisplay(ISimulationDisplay defaultDisplay) {
        this.defaultDisplay = defaultDisplay;
    }

    public Simulation createAndSetupSimulationInstance() throws Exception {
        Simulation simulation = createNew();
        setupSimulationInstance(simulation);
        return simulation;
    }

    /**
     *
     *
     *
     *
     *
     *
     *
     */
    public void createSimulation() {
//        loadSettings();
    }
}
