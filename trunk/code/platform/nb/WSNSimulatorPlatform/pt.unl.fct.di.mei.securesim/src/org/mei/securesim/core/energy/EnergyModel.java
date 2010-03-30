/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.core.energy;

import org.mei.securesim.annotation.Annotated;
import org.mei.securesim.annotation.EnergyModelParameter;

/**
 *
 * @author posilva
 */
public class EnergyModel implements Annotated{
    @EnergyModelParameter(label="Total energy")
    double totalEnergy;
    @EnergyModelParameter(label="Encrypt energy")
    double encryptEnergy;
    @EnergyModelParameter(label="Decrypt energy")
    double decryptEnergy;
    @EnergyModelParameter(label="Digest energy")
    double digestEnergy;
    @EnergyModelParameter(label="Sign energy")
    double signatureEnergy;
    @EnergyModelParameter(label="Verify Digest energy")
    double verifyDigestEnergy;
    @EnergyModelParameter(label="Verify signature energy")
    double verifySignatureEnergy;
    @EnergyModelParameter(label="CPU Transition to ON energy")
    double cpuTransitionToActiveEnergy;
    @EnergyModelParameter(label="Transciver Transition to ON energy")
    double txTransitionToActiveEnergy;
    @EnergyModelParameter(label="Transmission energy")
    double transmissionEnergy;
    @EnergyModelParameter(label="Reception energy")
    double receptionEnergy;
    @EnergyModelParameter(label="Idle State energy")
    double idleEnergy;
    @EnergyModelParameter(label="Sleep State energy")
    double sleepEnergy;

    public double getCpuTransitionToActiveEnergy() {
        return cpuTransitionToActiveEnergy;
    }

    public void setCpuTransitionToActiveEnergy(double cpuTransitionToActiveEnergy) {
        this.cpuTransitionToActiveEnergy = cpuTransitionToActiveEnergy;
    }

    public double getDecryptEnergy() {
        return decryptEnergy;
    }

    public void setDecryptEnergy(double decryptEnergy) {
        this.decryptEnergy = decryptEnergy;
    }

    public double getDigestEnergy() {
        return digestEnergy;
    }

    public void setDigestEnergy(double digestEnergy) {
        this.digestEnergy = digestEnergy;
    }

    public double getEncryptEnergy() {
        return encryptEnergy;
    }

    public void setEncryptEnergy(double encryptEnergy) {
        this.encryptEnergy = encryptEnergy;
    }

    public double getIdleEnergy() {
        return idleEnergy;
    }

    public void setIdleEnergy(double idleEnergy) {
        this.idleEnergy = idleEnergy;
    }

    public double getReceptionEnergy() {
        return receptionEnergy;
    }

    public void setReceptionEnergy(double receptionEnergy) {
        this.receptionEnergy = receptionEnergy;
    }

    public double getSignatureEnergy() {
        return signatureEnergy;
    }

    public void setSignatureEnergy(double signatureEnergy) {
        this.signatureEnergy = signatureEnergy;
    }

    public double getSleepEnergy() {
        return sleepEnergy;
    }

    public void setSleepEnergy(double sleepEnergy) {
        this.sleepEnergy = sleepEnergy;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public double getTransmissionEnergy() {
        return transmissionEnergy;
    }

    public void setTransmissionEnergy(double transmissionEnergy) {
        this.transmissionEnergy = transmissionEnergy;
    }

    public double getTxTransitionToActiveEnergy() {
        return txTransitionToActiveEnergy;
    }

    public void setTxTransitionToActiveEnergy(double txTransitionToActiveEnergy) {
        this.txTransitionToActiveEnergy = txTransitionToActiveEnergy;
    }

    public double getVerifyDigestEnergy() {
        return verifyDigestEnergy;
    }

    public void setVerifyDigestEnergy(double verifyDigestEnergy) {
        this.verifyDigestEnergy = verifyDigestEnergy;
    }

    public double getVerifySignatureEnergy() {
        return verifySignatureEnergy;
    }

    public void setVerifySignatureEnergy(double verifySignatureEnergy) {
        this.verifySignatureEnergy = verifySignatureEnergy;
    }

}
