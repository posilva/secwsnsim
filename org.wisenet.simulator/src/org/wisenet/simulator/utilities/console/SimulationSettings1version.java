/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.console;

import java.io.Serializable;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.components.topology.TopologyManager;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.ui.NoDisplay;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationSettings1version implements Serializable {

    boolean fastMode = true;
    int numberOfNodes;
    int fieldHeight;
    int fieldWidth;
    int fieldMaxAltitude;
    int maxNodeRadioStrength;
    String nodeFactoryClassName;
    String simulatorClassName;
    String radioModelClassName;
    String energyModelClassName;
    TopologyManager topologyManager;
    ISimulationDisplay display = new NoDisplay();

    public ISimulationDisplay getDisplay() {
        return display;
    }

    public void setDisplay(ISimulationDisplay display) {
        this.display = display;
    }

    public String getEnergyModelClassName() {
        return energyModelClassName;
    }

    public void setEnergyModelClassName(String energyModelClassName) {
        this.energyModelClassName = energyModelClassName;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public int getFieldMaxAltitude() {
        return fieldMaxAltitude;
    }

    public void setFieldMaxAltitude(int fieldMaxAltitude) {
        this.fieldMaxAltitude = fieldMaxAltitude;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public int getMaxNodeRadioStrength() {
        return maxNodeRadioStrength;
    }

    public void setMaxNodeRadioStrength(int maxNodeRadioStrength) {
        this.maxNodeRadioStrength = maxNodeRadioStrength;
    }

    public String getNodeFactoryClassName() {
        return nodeFactoryClassName;
    }

    public void setNodeFactoryClassName(String nodeFactoryClassName) {
        this.nodeFactoryClassName = nodeFactoryClassName;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
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

    public TopologyManager getTopologyManager() {
        return topologyManager;
    }

    public void setTopologyManager(TopologyManager topologyManager) {
        this.topologyManager = topologyManager;
    }

    public boolean isFastMode() {
        return fastMode;
    }

    public void setFastMode(boolean fastMode) {
        this.fastMode = fastMode;
    }

    public void save(String filename) throws Exception {
        XMLConfiguration file = new XMLConfiguration();
        file.addProperty("simulation.settings.name", "");
        file.addProperty("simulation.settings.seed", "");
        file.addProperty("simulation.settings.fastmode", "");
        file.addProperty("simulation.settings.numNodes", "");
        file.addProperty("simulation.settings.nodeFactoryClassName", "");
        file.addProperty("simulation.settings.simulatorClassName", "");
        file.addProperty("simulation.settings.radioModelClassName", "");
        file.addProperty("simulation.settings.maxNodeRadioStrength", "");
        file.addProperty("simulation.settings.energyModelClassName", "");
        file.save(filename);
    }
    public void load(String filename) throws Exception {

    }
}
