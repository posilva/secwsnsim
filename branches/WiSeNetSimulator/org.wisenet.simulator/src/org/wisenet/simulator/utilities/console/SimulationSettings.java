/**
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.console;

import java.io.Serializable;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationSettings extends PersistantObject implements Serializable {

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
        loadFromXML(filename);
    }

    public void save(String filename) throws Exception {
        saveToXML(filename);
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

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        configuration.addProperty("simulation.settings.name", getName());
        configuration.addProperty("simulation.settings.seed", getSeed());
        configuration.addProperty("simulation.settings.fastmode", isFastMode());
        configuration.addProperty("simulation.settings.nodeFactoryClassName", getNodeFactoryClassName());
        configuration.addProperty("simulation.settings.simulatorClassName", getSimulatorClassName());
        configuration.addProperty("simulation.settings.radioModelClassName", getRadioModelClassName());
        configuration.addProperty("simulation.settings.maxNodeRadioStrength", getMaxNodeRadioStrength());
        configuration.addProperty("simulation.settings.energyModelClassName", getEnergyModelClassName());
        configuration.addProperty("simulation.settings.staticz", isStaticZ());
        configuration.addProperty("simulation.settings.minz", getMinZ());
        configuration.addProperty("simulation.settings.maxz", getMaxZ());
        configuration.addProperty("simulation.settings.environAttenuation", getEnvironAttenuation());
    }

    public void loadFromXML(XMLConfiguration file) throws PersistantException {
        setName((String) file.getString("simulation.settings.name"));
        setSeed((Long) file.getLong("simulation.settings.seed"));
        setFastMode((Boolean) file.getBoolean("simulation.settings.fastmode"));
        setMaxNodeRadioStrength((Integer) file.getInt("simulation.settings.maxNodeRadioStrength"));
        setEnergyModelClassName((String) file.getString("simulation.settings.energyModelClassName"));
        setSimulatorClassName((String) file.getString("simulation.settings.simulatorClassName"));
        setNodeFactoryClassName((String) file.getString("simulation.settings.nodeFactoryClassName"));
        setRadioModelClassName((String) file.getString("simulation.settings.radioModelClassName"));
        setStaticZ((Boolean) file.getBoolean("simulation.settings.staticz"));
        setMinZ((Double) file.getDouble("simulation.settings.minz"));
        setMaxZ((Double) file.getDouble("simulation.settings.maxz"));
        setEnvironAttenuation((Double) file.getDouble("simulation.settings.environAttenuation"));
    }
}
