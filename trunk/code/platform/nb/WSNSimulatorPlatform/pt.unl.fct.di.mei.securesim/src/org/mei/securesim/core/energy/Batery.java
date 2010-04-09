package org.mei.securesim.core.energy;

import org.mei.securesim.core.energy.listeners.EnergyEvent;
import org.mei.securesim.core.energy.listeners.EnergyListener;
import org.mei.securesim.core.nodes.Node;

public class Batery {

    public static final double INFINIT = -1;
//    public static final double TRASMISSION_CONSUMPTION = 0.1;
//    public static final double RECEIVING_CONSUMPTION = 0.01;
//    public static double PROCESSING_CONSUMPTION = 0.005;
//    public static double IDLE_CONSUMPTION = 0.005;
//    public static double STATE_TRANSITION_ON_OFF_CONSUMPTION = 0.00001;
    EnergyModel energyModel;
    protected double averageConsumption=0;
    protected double totalconsumptions=0;
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

    public synchronized void consume(double value) {
        currentPower -= value;

        double tot = averageConsumption*totalconsumptions;
        tot+=value;
        totalconsumptions++;
        averageConsumption=tot/totalconsumptions;
        fireOnEnergyConsume(new EnergyEvent(this, value,System.nanoTime() - getHostNode().getSimulator().getSysNanoTimeStart()));
        lastConsume=value;
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
        consume(energyModel.getTransmissionEnergy() * length);
        log("Transmission", energyModel.getTransmissionEnergy() * length);
    }

    public void consumeReceiving(int length) {
        //System.out.println("consumeReceiving: " );
        consume(energyModel.getReceptionEnergy());
        log("Receiving", energyModel.getReceptionEnergy() * length);
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
        consume(energyModel.getCpuTransitionToActiveEnergy());
        log("CPUTransitionToON", energyModel.getCpuTransitionToActiveEnergy());
    }

    public void consumeProcessing(long rate) {
        consume(energyModel.processingEnergy * rate);
        log("Processing", energyModel.processingEnergy * rate);
    }

    public EnergyModel getEnergyModel() {
        return energyModel;
    }

    public void setEnergyModel(EnergyModel energyModel) {
        this.energyModel = energyModel;
    }

    public void consumeEncryption(int length) {
        consume(energyModel.getEncryptEnergy() * length);
        log("Encryption", energyModel.getEncryptEnergy() * length);
    }

    public void consumeMAC(int length) {
        consume(energyModel.getDigestEnergy() * length);
        log("MAC", energyModel.getDigestEnergy() * length);
    }

    public void consumeMACVerification(int length) {
        consume(energyModel.getVerifyDigestEnergy() * length);
        log("MACVerification", energyModel.getVerifyDigestEnergy() * length);
    }

    public void consumeDecryption(int length) {
        consume(energyModel.getDecryptEnergy() * length);
        log("Decryption", energyModel.getDecryptEnergy() * length);
    }

    private void init() {
        //globalEnergyController=new Object();
    }

    private synchronized void log(String string, double d) {
//        System.out.println(getHostNode().getId() + "\t" + string + "\t" + d);
    }

    public double getAverageConsumption() {
        return averageConsumption;
    }

    public void consumeTXTransitionToON() {
        consume(energyModel.getTxTransitionToActiveEnergy());
        log("TXTransitionToON", energyModel.getTxTransitionToActiveEnergy());
    }

    public void consumeIdle() {
        consume(energyModel.getIdleEnergy());
        log("Idle", energyModel.getIdleEnergy());

    }
}
