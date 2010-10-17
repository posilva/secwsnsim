/**
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.console;

import java.io.Serializable;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;
import org.wisenet.simulator.core.energy.EnergyModelParameters;

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
    ObjectParameters energyModelParameters=new EnergyModelParameters();
    
    boolean staticZ = true;
    double minZ = 0;
    double maxZ = 0;
    int environAttenuation = 0;

    /**
     *
     * @return
     */
    public String getEnergyModelClassName() {
        return energyModelClassName;
    }

    /**
     *
     * @param energyModelClassName
     */
    public void setEnergyModelClassName(String energyModelClassName) {
        this.energyModelClassName = energyModelClassName;
    }

    /**
     *
     * @return
     */
    public int getMaxNodeRadioRange() {
        return maxNodeRadioStrength;
    }

    /**
     *
     * @param maxNodeRadioStrength
     */
    public void setMaxNodeRadioRange(int maxNodeRadioStrength) {
        this.maxNodeRadioStrength = maxNodeRadioStrength;
    }

    /**
     *
     * @return
     */
    public String getNodeFactoryClassName() {
        return nodeFactoryClassName;
    }

    /**
     *
     * @param nodeFactoryClassName
     */
    public void setNodeFactoryClassName(String nodeFactoryClassName) {
        this.nodeFactoryClassName = nodeFactoryClassName;
    }

    /**
     *
     * @return
     */
    public String getRadioModelClassName() {
        return radioModelClassName;
    }

    /**
     *
     * @param radioModelClassName
     */
    public void setRadioModelClassName(String radioModelClassName) {
        this.radioModelClassName = radioModelClassName;
    }

    /**
     *
     * @return
     */
    public String getSimulatorClassName() {
        return simulatorClassName;
    }

    /**
     *
     * @param simulatorClassName
     */
    public void setSimulatorClassName(String simulatorClassName) {
        this.simulatorClassName = simulatorClassName;
    }

    /**
     *
     * @return
     */
    public boolean isFastMode() {
        return fastMode;
    }

    /**
     *
     * @param fastMode
     */
    public void setFastMode(boolean fastMode) {
        this.fastMode = fastMode;
    }

    /**
     *
     * @param filename
     * @throws Exception
     */
    public void load(String filename) throws Exception {
        loadFromXML(filename);
    }

    /**
     *
     * @param filename
     * @throws Exception
     */
    public void save(String filename) throws Exception {
        saveToXML(filename);
    }

    /**
     *
     * @return
     */
    public long getSeed() {
        return seed;
    }

    /**
     *
     * @param seed
     */
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public int getEnvironAttenuation() {
        return environAttenuation;
    }

    /**
     *
     * @param environAttenuation
     */
    public void setEnvironAttenuation(int environAttenuation) {
        this.environAttenuation = environAttenuation;
    }

    /**
     *
     * @return
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     *
     * @param maxZ
     */
    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    /**
     *
     * @return
     */
    public double getMinZ() {
        return minZ;
    }

    /**
     *
     * @param minZ
     */
    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    /**
     *
     * @return
     */
    public boolean isStaticZ() {
        return staticZ;
    }

    /**
     *
     * @param staticZ
     */
    public void setStaticZ(boolean staticZ) {
        this.staticZ = staticZ;
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        configuration.addProperty("simulation.settings.name", getName());
        configuration.addProperty("simulation.settings.seed", getSeed());
        configuration.addProperty("simulation.settings.fastmode", isFastMode());
        configuration.addProperty("simulation.settings.nodeFactoryClassName", getNodeFactoryClassName());
        configuration.addProperty("simulation.settings.simulatorClassName", getSimulatorClassName());
        configuration.addProperty("simulation.settings.radioModelClassName", getRadioModelClassName());
        configuration.addProperty("simulation.settings.maxNodeRadioRange", getMaxNodeRadioRange());
        configuration.addProperty("simulation.settings.energyModelClassName", getEnergyModelClassName());
        configuration.addProperty("simulation.settings.staticz", isStaticZ());
        configuration.addProperty("simulation.settings.minz", getMinZ());
        configuration.addProperty("simulation.settings.maxz", getMaxZ());
        configuration.addProperty("simulation.settings.environAttenuation", getEnvironAttenuation());
        energyModelParameters.saveToXML(configuration);

    }

    /**
     *
     * @param file
     * @throws PersistantException
     */
    public void loadFromXML(XMLConfiguration file) throws PersistantException {
        setName((String) file.getString("simulation.settings.name"));
        setSeed((Long) file.getLong("simulation.settings.seed"));
        setFastMode((Boolean) file.getBoolean("simulation.settings.fastmode"));
        setMaxNodeRadioRange((Integer) file.getInt("simulation.settings.maxNodeRadioRange"));
        setEnergyModelClassName((String) file.getString("simulation.settings.energyModelClassName"));
        setSimulatorClassName((String) file.getString("simulation.settings.simulatorClassName"));
        setNodeFactoryClassName((String) file.getString("simulation.settings.nodeFactoryClassName"));
        setRadioModelClassName((String) file.getString("simulation.settings.radioModelClassName"));
        setStaticZ((Boolean) file.getBoolean("simulation.settings.staticz"));
        setMinZ((Double) file.getDouble("simulation.settings.minz"));
        setMaxZ((Double) file.getDouble("simulation.settings.maxz"));
        setEnvironAttenuation((Integer) file.getInt("simulation.settings.environAttenuation"));
        energyModelParameters.loadFromXML(file);
    }

    /**
     *
     * @return
     */
    public ObjectParameters getEnergyModelParameters() {
        return energyModelParameters;
    }

    /**
     *
     * @param energyModelParameters
     */
    public void setEnergyModelParameters(ObjectParameters energyModelParameters) {
        this.energyModelParameters = energyModelParameters;
    }

}
