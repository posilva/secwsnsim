package org.mei.securesim.core.energy;

import org.mei.securesim.components.instruments.energy.EnergyController;
import org.mei.securesim.core.energy.listeners.EnergyEvent;
import org.mei.securesim.core.energy.listeners.EnergyListener;
import org.mei.securesim.core.nodes.Node;

public class Batery {

    public static final String CPUTRANSITIONTOON_EVENT = "CPUTransitionToON";
    public static final String DECRYPTION_EVENT = "Decryption";
    public static final String ENCRYPTION_EVENT = "Encryption";
    public static final String IDLE_EVENT = "Idle";
    public static final String MACVERIFICATION_EVENT = "MACVerification";
    public static final String MAC_EVENT = "MAC";
    public static final String PROCESSING_EVENT = "Processing";
    public static final String RECEIVING_EVENT = "Receiving";
    public static final String SIGNATUREVERIFY_EVENT = "SignatureVerify";
    public static final String SIGNATURE_EVENT = "Signature";
    public static final String TRANSMISSION_EVENT = "Transmission";
    public static final String TXTRANSITIONTOON_EVENT = "TXTransitionToON";
    public static final String UNKNOWNED_EVENT = "unknowned";
    /**
     * 
     */
    public boolean enable = true;
    public static final double INFINIT = -1;
    EnergyModel energyModel;
    protected double averageConsumption = 0;
    protected double totalconsumptions = 0;
    /**
     * 
     */
    private double currentPower = INFINIT;
    /**
     * 
     */
    private double initialPower = 0;
    /**
     * 
     */
    protected Node hostNode;
    /**
     * 
     */
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    private double lastConsume;

    public double getLastConsume() {
        return lastConsume;
    }

    public void setLastConsume(double lastConsume) {
        this.lastConsume = lastConsume;
    }

    /**
     * 
     * @param listener
     */
    public void addEnergyListener(EnergyListener listener) {
        listenerList.add(EnergyListener.class, listener);
    }

    /**
     * 
     * @param listener
     */
    public void removeEnergyListener(EnergyListener listener) {
        listenerList.remove(EnergyListener.class, listener);
    }

    public Batery() {
        this.initialPower = INFINIT;
        this.currentPower = INFINIT;
        init();
    }

    public Batery(EnergyModel energyModel) {
        this.energyModel = energyModel;
        this.initialPower = this.energyModel.getTotalEnergy();
        this.currentPower = this.energyModel.getTotalEnergy();
        init();
    }

    /**
     * @return the currentPower
     */
    public double getCurrentPower() {
        return currentPower;
    }

    public boolean off() {
        if (currentPower <= 0) {
            return true;
        } else {
            return false;
        }
    }

    protected synchronized void consume(double value, String event) {
        if (!enable) {
            return;
        }

        currentPower -= value;

        double tot = averageConsumption * totalconsumptions;
        tot += value;
        totalconsumptions++;
        averageConsumption = tot / totalconsumptions;

        fireOnEnergyConsume(new EnergyEvent(this, value, System.currentTimeMillis(), getHostNode().getSimulator().getSimulationTime(), event, getHostNode().getId()));
        if (currentPower<=0) getHostNode().shutdown();
        lastConsume = value;
    }

    protected synchronized void consume(double value) {
        if (!enable) {
            return;
        }
        currentPower -= value;

        double tot = averageConsumption * totalconsumptions;
        tot += value;
        totalconsumptions++;
        averageConsumption = tot / totalconsumptions;
        fireOnEnergyConsume(new EnergyEvent(this, value, System.currentTimeMillis(), getHostNode().getSimulator().getSimulationTime(), UNKNOWNED_EVENT, getHostNode().getId()));
        lastConsume = value;
        if (currentPower<=0) getHostNode().shutdown();
    }

    private void fireOnEnergyConsume(EnergyEvent energyEvent) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == EnergyListener.class) {
                ((EnergyListener) listeners[i + 1]).onConsume(energyEvent);
            }
        }
    }

    public void consumeTransmission(double length) {
        consume(energyModel.getTransmissionEnergy() * length, TRANSMISSION_EVENT);
    }

    public void consumeReceiving(int length) {
        consume(energyModel.getReceptionEnergy(), RECEIVING_EVENT);
    }

    public Node getHostNode() {
        return hostNode;
    }

    public void setHostNode(Node hostNode) {
        this.hostNode = hostNode;
    }

    public double getInitialPower() {
        return initialPower;
    }

    public void consumeCPUTransitionToON() {
        consume(energyModel.getCpuTransitionToActiveEnergy(), CPUTRANSITIONTOON_EVENT);
    }

    public void consumeProcessing(long rate) {
        consume(energyModel.processingEnergy * rate, PROCESSING_EVENT);
    }

    public EnergyModel getEnergyModel() {
        return energyModel;
    }

    public void setEnergyModel(EnergyModel energyModel) {
        this.energyModel = energyModel;
    }

    public void consumeEncryption(int length) {
        consume(energyModel.getEncryptEnergy() * length, ENCRYPTION_EVENT);
    }

    public void consumeMAC(int length) {
        consume(energyModel.getDigestEnergy() * length, MAC_EVENT);
    }

    public void consumeMACVerification(int length) {
        consume(energyModel.getVerifyDigestEnergy() * length, MACVERIFICATION_EVENT);
    }

    public void consumeDecryption(int length) {
        consume(energyModel.getDecryptEnergy() * length, DECRYPTION_EVENT);
    }

    private void init() {
        addEnergyListener(EnergyController.getInstance());
    }

    public double getAverageConsumption() {
        return averageConsumption;
    }

    public void consumeTXTransitionToON() {
        consume(energyModel.getTxTransitionToActiveEnergy(), TXTRANSITIONTOON_EVENT);

    }

    public void consumeIdle() {
        consume(energyModel.getIdleEnergy(), IDLE_EVENT);

    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void consumeSignature(long rate) {
        consume(energyModel.getSignatureEnergy(), SIGNATURE_EVENT);
    }

    public void consumeSignatureVerify(long rate) {
        consume(energyModel.getVerifySignatureEnergy(), SIGNATUREVERIFY_EVENT);
    }
}
