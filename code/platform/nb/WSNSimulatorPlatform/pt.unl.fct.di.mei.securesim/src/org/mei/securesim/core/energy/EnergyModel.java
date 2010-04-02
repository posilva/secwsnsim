/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.core.energy;

import org.mei.securesim.utils.annotation.Annotated;
import org.mei.securesim.utils.annotation.EnergyModelParameter;

/**
 *
 * @author posilva
 */
public class EnergyModel implements Annotated{
    /**
     * Valores Baseados em
     */
    @EnergyModelParameter(label="Total energy (Joules)",value=9360.0)
    double totalEnergy;
    // baseado no paper 3 - Evaluation of security Mechanisms  in WSN // skipjack
    @EnergyModelParameter(label="Encrypt energy (Joules/Byte)",value=0.000001788)
    double encryptEnergy;
    @EnergyModelParameter(label="Decrypt energy (Joules/Byte)",value=0.000001788)
    double decryptEnergy;
    //Energy Analysis of public key cryptography for WSN PAPER 2
    @EnergyModelParameter(label="Digest energy (Joules/Byte)",value=0.0000059) // SHA1
    double digestEnergy;
    @EnergyModelParameter(label="Sign energy (Joules/Byte)",value=0.0000059)
    double signatureEnergy;
    @EnergyModelParameter(label="Verify Digest energy (Joules/Byte)",value=0.0000059)
    double verifyDigestEnergy;
    @EnergyModelParameter(label="Verify signature energy (Joules/Byte)",value=0.0000059)
    double verifySignatureEnergy;
    @EnergyModelParameter(label="CPU Transition to ON energy (Joules)",value=0.000000001)
    double cpuTransitionToActiveEnergy;
    @EnergyModelParameter(label="Transciver Transition to ON energy (Joules)",value=0.000000002)
    double txTransitionToActiveEnergy;
    @EnergyModelParameter(label="Transmission energy (Joules/Byte)",value=0.00000592)
    double transmissionEnergy;
    @EnergyModelParameter(label="Reception energy (Joules/Byte)",value=0.00000286)
    double receptionEnergy;
    @EnergyModelParameter(label="Idle State energy (Joules)",value=0.0000059)
    double idleEnergy;
    @EnergyModelParameter(label="Sleep State energy (Joules)",value=0.0000075)
    double sleepEnergy;
    @EnergyModelParameter(label="Simple processing energy (Joules)",value=0.0138)
    double processingEnergy;

    public double getProcessingEnergy() {
        return processingEnergy;
    }

    public void setProcessingEnergy(double processingEnergy) {
        this.processingEnergy = processingEnergy;
    }

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
