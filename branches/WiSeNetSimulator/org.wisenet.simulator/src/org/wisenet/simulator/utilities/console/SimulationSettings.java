/**
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.console;

import java.io.Serializable;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationSettings implements Serializable {

    String name;
    boolean fastMode = true;
    long seed = System.currentTimeMillis();
    int maxNodeRadioStrength;
    String nodeFactoryClassName;
    String simulatorClassName;
    String radioModelClassName;
    String energyModelClassName;
    boolean staticZ = true;
    double minZ = 0;
    double maxZ = 0;
    double environAttenuation = 0;

    public String getEnergyModelClassName() {
        return energyModelClassName;
    }

    public void setEnergyModelClassName(String energyModelClassName) {
        this.energyModelClassName = energyModelClassName;
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

    public boolean isFastMode() {
        return fastMode;
    }

    public void setFastMode(boolean fastMode) {
        this.fastMode = fastMode;
    }

    public void load(String filename) throws Exception {
        XMLConfiguration file = new XMLConfiguration();
        file.load(filename);
        setName((String) file.getProperty("simulation.settings.name"));
        setSeed((Long) file.getProperty("simulation.settings.seed"));
        setFastMode((Boolean) file.getProperty("simulation.settings.fastmode"));
        setMaxNodeRadioStrength((Integer) file.getProperty("simulation.settings.maxNodeRadioStrength"));
        setEnergyModelClassName((String) file.getProperty("simulation.settings.energyModelClassName"));
        setSimulatorClassName((String) file.getProperty("simulation.settings.simulatorClassName"));
        setNodeFactoryClassName((String) file.getProperty("simulation.settings.nodeFactoryClassName"));
        setRadioModelClassName((String) file.getProperty("simulation.settings.radioModelClassName"));
        setStaticZ((Boolean) file.getProperty("simulation.settings.staticz"));
        setMinZ((Double) file.getProperty("simulation.settings.minz"));
        setMaxZ((Double) file.getProperty("simulation.settings.maxz"));
        setEnvironAttenuation((Double) file.getProperty("simulation.settings.environAttenuation"));
    }

    public void save(String filename) throws Exception {
        XMLConfiguration file = new XMLConfiguration();
        file.addProperty("simulation.settings.name", getName());
        file.addProperty("simulation.settings.seed", getSeed());
        file.addProperty("simulation.settings.fastmode", isFastMode());
        file.addProperty("simulation.settings.nodeFactoryClassName", getNodeFactoryClassName());
        file.addProperty("simulation.settings.simulatorClassName", getSimulatorClassName());
        file.addProperty("simulation.settings.radioModelClassName", getRadioModelClassName());
        file.addProperty("simulation.settings.maxNodeRadioStrength", getMaxNodeRadioStrength());
        file.addProperty("simulation.settings.energyModelClassName", getEnergyModelClassName());
        file.addProperty("simulation.settings.staticz", isStaticZ());
        file.addProperty("simulation.settings.minz", getMinZ());
        file.addProperty("simulation.settings.maxz", getMaxZ());
        file.addProperty("simulation.settings.environAttenuation", getEnvironAttenuation());
        file.save(filename);
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEnvironAttenuation() {
        return environAttenuation;
    }

    public void setEnvironAttenuation(double environAttenuation) {
        this.environAttenuation = environAttenuation;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public boolean isStaticZ() {
        return staticZ;
    }

    public void setStaticZ(boolean staticZ) {
        this.staticZ = staticZ;
    }
}
