package org.wisenet.simulator.core.energy;

import org.wisenet.simulator.core.energy.listeners.EnergyEvent;
import org.wisenet.simulator.core.energy.listeners.EnergyListener;
import org.wisenet.simulator.core.node.Node;

public class Batery {

    public static final String CPUTRANSITIONTOON_EVENT = "CPUTransitionToON";
    public static final String DECRYPTION_EVENT = "Decryption";
    public static final String ENCRYPTION_EVENT = "Encryption";
    public static final String IDLE_EVENT = "Idle";
    public static final int INFINIT_POWER = -1;
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
    protected static EnergyController controller = new EnergyController();
    /**
     *
     */
    public boolean enable = true;
    /*
     *
     */
    public boolean infinit = false;
    /**
     * 
     */
    EnergyModel energyModel;
    /**
     *
     */
    protected double averageConsumption = 0;
    /**
     *
     */
    protected double totalconsumptions = 0;
    /**
     * 
     */
    private double currentPower = INFINIT_POWER;
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
        this.initialPower = INFINIT_POWER;
        this.currentPower = INFINIT_POWER;
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

    /**
     * 
     * @return
     */
    public boolean off() {
        if (currentPower <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @param value
     * @param event
     */
    protected synchronized void consume(double value, String event) {
        if (!enable) {
            return;
        }
        if (infinit) {
            return;
        }
        consumeEvent(value, event);
    }

    /**
     * 
     * @param value
     * @param event
     */
    private void consumeEvent(double value, String event) {
        reducePower(value);
        updateInternalCounters(value);
        EnergyEvent ev = new EnergyEvent(this, value, System.currentTimeMillis(), getHostNode().getSimulator().getSimulationTime(), event, getHostNode().getId(), getHostNode().getRoutingLayer().getCurrentPhase());
        fireOnEnergyConsume(ev);
        if (currentPower <= 0) {
            getHostNode().shutdown();
        }
    }

    /**
     * 
     * @param value
     */
    private void reducePower(double value) {
        currentPower -= value;
    }

    /**
     * 
     * @param value
     */
    private void updateInternalCounters(double value) {
        double tot = averageConsumption * totalconsumptions;
        tot += value;
        totalconsumptions++;
        averageConsumption = tot / totalconsumptions;
    }

    /**
     * 
     * @param value
     */
    protected synchronized void consume(double value) {
        if (!enable) {
            return;
        }
        if (infinit) {
            return;
        }
        consumeEvent(value, UNKNOWNED_EVENT);
    }

    /**
     *
     * @param energyEvent
     */
    private void fireOnEnergyConsume(EnergyEvent energyEvent) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == EnergyListener.class) {
                ((EnergyListener) listeners[i + 1]).onConsume(energyEvent);
            }
        }
    }

    /**
     * 
     * @param length
     */
    public void consumeTransmission(double length) {
        consume(energyModel.getTransmissionEnergy() * length, TRANSMISSION_EVENT);
    }

    /**
     *
     * @param length
     */
    public void consumeReceiving(int length) {
        consume(energyModel.getReceptionEnergy(), RECEIVING_EVENT);
    }

    /**
     *
     * @return
     */
    public Node getHostNode() {
        return hostNode;
    }

    /**
     *
     * @param hostNode
     */
    public void setHostNode(Node hostNode) {
        this.hostNode = hostNode;
    }

    /**
     *
     * @return
     */
    public double getInitialPower() {
        return initialPower;
    }

    /**
     *
     */
    public void consumeCPUTransitionToON() {
        consume(energyModel.getCpuTransitionToActiveEnergy(), CPUTRANSITIONTOON_EVENT);
    }

    /**
     *
     * @param rate
     */
    public void consumeProcessing(long rate) {
        consume(energyModel.processingEnergy * rate, PROCESSING_EVENT);
    }

    /**
     *
     * @return
     */
    public EnergyModel getEnergyModel() {
        return energyModel;
    }

    /**
     *
     * @param energyModel
     */
    public void setEnergyModel(EnergyModel energyModel) {
        this.energyModel = energyModel;
    }

    /**
     *
     * @param length
     */
    public void consumeEncryption(int length) {
        consume(energyModel.getEncryptEnergy() * length, ENCRYPTION_EVENT);
    }

    /**
     *
     * @param length
     */
    public void consumeMAC(int length) {
        consume(energyModel.getDigestEnergy() * length, MAC_EVENT);
    }

    /**
     *
     * @param length
     */
    public void consumeMACVerification(int length) {
        consume(energyModel.getVerifyDigestEnergy() * length, MACVERIFICATION_EVENT);
    }

    /**
     *
     * @param length
     */
    public void consumeDecryption(int length) {
        consume(energyModel.getDecryptEnergy() * length, DECRYPTION_EVENT);
    }

    /**
     *
     */
    private void init() {
        addEnergyListener(controller);
    }

    /**
     *
     * @return
     */
    public double getAverageConsumption() {
        return averageConsumption;
    }

    /**
     *
     */
    public void consumeTXTransitionToON() {
        consume(energyModel.getTxTransitionToActiveEnergy(), TXTRANSITIONTOON_EVENT);

    }

    /**
     *
     */
    public void consumeIdle() {
        consume(energyModel.getIdleEnergy(), IDLE_EVENT);
    }

    /**
     *
     * @return
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     *
     * @param enable
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     *
     * @param rate
     */
    public void consumeSignature(long rate) {
        consume(energyModel.getSignatureEnergy(), SIGNATURE_EVENT);
    }

    /**
     *
     * @param rate
     */
    public void consumeSignatureVerify(long rate) {
        consume(energyModel.getVerifySignatureEnergy(), SIGNATUREVERIFY_EVENT);
    }

    /**
     *
     * @return
     */
    public static EnergyController getController() {
        return controller;
    }

    public void reset() {
        if (this.energyModel != null) {
            this.initialPower = this.energyModel.getTotalEnergy();
            this.currentPower = this.energyModel.getTotalEnergy();
        } else {
            this.initialPower = currentPower;
        }
    }
}
