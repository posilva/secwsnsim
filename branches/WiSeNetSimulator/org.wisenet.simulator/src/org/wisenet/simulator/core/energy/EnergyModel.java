/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.energy;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.ObjectParameter;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.Parameterizable;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.Persistent;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class EnergyModel implements Parameterizable, Persistent {

    EnergyModelParameters parameters = new EnergyModelParameters();

    private static void getEnergyModelDefaultValues(EnergyModel energyModel) throws SecurityException {
        
    }

    /**
     *
     * @return
     */
    public double getProcessingEnergy() {
        return (Double) parameters.get("processingEnergy");
    }

    /**
     *
     * @param processingEnergy
     */
    public void setProcessingEnergy(double processingEnergy) {
        parameters.set("processingEnergy", processingEnergy);
    }

    /**
     *
     * @return
     */
    public double getCpuTransitionToActiveEnergy() {
        return (Double) parameters.get("cpuTransitionToActiveEnergy");
    }

    /**
     *
     * @param cpuTransitionToActiveEnergy
     */
    public void setCpuTransitionToActiveEnergy(double cpuTransitionToActiveEnergy) {
        parameters.set("cpuTransitionToActiveEnergy", cpuTransitionToActiveEnergy);
    }

    /**
     *
     * @return
     */
    public double getDecryptEnergy() {
        return (Double) parameters.get("decryptEnergy");
    }

    /**
     *
     * @param decryptEnergy
     */
    public void setDecryptEnergy(double decryptEnergy) {
        parameters.set("decryptEnergy", decryptEnergy);
    }

    /**
     *
     * @return
     */
    public double getDigestEnergy() {
        return (Double) parameters.get("digestEnergy");
    }

    /**
     *
     * @param digestEnergy
     */
    public void setDigestEnergy(double digestEnergy) {
        parameters.set("digestEnergy", digestEnergy);
    }

    /**
     *
     * @return
     */
    public double getEncryptEnergy() {
        return (Double) parameters.get("encryptEnergy");
    }

    /**
     *
     * @param encryptEnergy
     */
    public void setEncryptEnergy(double encryptEnergy) {
        parameters.set("encryptEnergy", encryptEnergy);
    }

    /**
     *
     * @return
     */
    public double getIdleEnergy() {
        return (Double) parameters.get("idleEnergy");
    }

    /**
     *
     * @param idleEnergy
     */
    public void setIdleEnergy(double idleEnergy) {
        parameters.set("idleEnergy", idleEnergy);
    }

    /**
     *
     * @return
     */
    public double getReceptionEnergy() {
        return (Double) parameters.get("receptionEnergy");
    }

    /**
     *
     * @param receptionEnergy
     */
    public void setReceptionEnergy(double receptionEnergy) {
        parameters.set("receptionEnergy", receptionEnergy);
    }

    /**
     *
     * @return
     */
    public double getSignatureEnergy() {
        return (Double) parameters.get("signatureEnergy");
    }

    /**
     *
     * @param signatureEnergy
     */
    public void setSignatureEnergy(double signatureEnergy) {
        parameters.set("signatureEnergy", signatureEnergy);
    }

    /**
     *
     * @return
     */
    public double getSleepEnergy() {
        return (Double) parameters.get("sleepEnergy");
    }

    /**
     *
     * @param sleepEnergy
     */
    public void setSleepEnergy(double sleepEnergy) {
        parameters.set("sleepEnergy", sleepEnergy);
    }

    /**
     *
     * @return
     */
    public double getTotalEnergy() {
        return (Double) parameters.get("totalEnergy");
    }

    /**
     *
     * @param totalEnergy
     */
    public void setTotalEnergy(double totalEnergy) {
        parameters.set("totalEnergy", totalEnergy);
    }

    /**
     *
     * @return
     */
    public double getTransmissionEnergy() {
        return (Double) parameters.get("transmissionEnergy");
    }

    /**
     *
     * @param transmissionEnergy
     */
    public void setTransmissionEnergy(double transmissionEnergy) {
        parameters.set("transmissionEnergy", transmissionEnergy);
    }

    /**
     *
     * @return
     */
    public double getTxTransitionToActiveEnergy() {
        return (Double) parameters.get("txTransitionToActiveEnergy");
    }

    /**
     *
     * @param txTransitionToActiveEnergy
     */
    public void setTxTransitionToActiveEnergy(double txTransitionToActiveEnergy) {
        parameters.set("txTransitionToActiveEnergy", txTransitionToActiveEnergy);
    }

    /**
     *
     * @return
     */
    public double getVerifyDigestEnergy() {
        return (Double) parameters.get("verifyDigestEnergy");
    }

    /**
     *
     * @param verifyDigestEnergy
     */
    public void setVerifyDigestEnergy(double verifyDigestEnergy) {
        parameters.set("verifyDigestEnergy", verifyDigestEnergy);
    }

    /**
     *
     * @return
     */
    public double getVerifySignatureEnergy() {
        return (Double) parameters.get("verifySignatureEnergy");
    }

    /**
     *
     * @param verifySignatureEnergy
     */
    public void setVerifySignatureEnergy(double verifySignatureEnergy) {
        parameters.set("verifySignatureEnergy", verifySignatureEnergy);
    }

    /**
     *
     * @return
     */
    public EnergyModel getInstanceWithDefaultValues() {
        getEnergyModelDefaultValues(this);
        return this;
    }

    /**
     *
     * @return
     */
    public static EnergyModel getDefaultInstance() {
        EnergyModel energyModel = new EnergyModel();
        getEnergyModelDefaultValues(energyModel);
        return energyModel;
    }

    /**
     *
     * @return
     */
    public ObjectParameters getParameters() {
        return parameters;
    }

    /**
     *
     * @param file
     * @throws PersistantException
     */
    public void saveToXML(String file) throws PersistantException {
        try {
            XMLConfiguration configuration = new XMLConfiguration();
            saveToXML(configuration);
            configuration.save(file);
        } catch (ConfigurationException ex) {
            throw new PersistantException(ex);
        }
    }

    /**
     *
     * @param file
     * @throws PersistantException
     */
    public void loadFromXML(String file) throws PersistantException {
        try {
            XMLConfiguration configuration = new XMLConfiguration(file);
            configuration.load();
            loadFromXML(configuration);

        } catch (ConfigurationException ex) {
            throw new PersistantException(ex);
        }
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        for (ObjectParameter param : this.parameters.getAllParameters()) {
            configuration.addProperty("energyModel." + param.getName() + ".label", param.getLabel());
            configuration.addProperty("energyModel." + param.getName() + ".value", param.getValue());
            configuration.addProperty("energyModel." + param.getName() + ".required", param.isRequired());
        }
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        for (ObjectParameter param : this.parameters.getAllParameters()) {
            String label = (String) configuration.getString("energyModel." + param.getName() + ".label");
            Object value = configuration.getDouble("energyModel." + param.getName() + ".value");
            Boolean required = (Boolean) configuration.getBoolean("energyModel." + param.getName() + ".required");
            parameters.set(new ObjectParameter(param.getName(), label, value, required));
        }
    }

    /**
     *
     * @param params
     */
    public void setParameters(ObjectParameters params) {
        this.parameters = (EnergyModelParameters) params;
    }
}
