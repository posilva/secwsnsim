package pt.unl.fct.di.mei.securesim.engine.energy;

import pt.unl.fct.di.mei.securesim.engine.nodes.Node;

public class Batery {

    public static final double INFINIT = -1;
    public static final double TRASMISSION_CONSUMPTION = 0.1;
    private double currentPower = INFINIT;
    private static final double RECEIVING_CONSUMPTION = 0.01;
    private int sampleCount = 0;
    private long startTime;
    private long stopTime;
    private double initialPower = 0;
    protected Node hostNode;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

    public void addEnergyListener(EnergyListener listener) {
        listenerList.add(EnergyListener.class, listener);
    }

    public void removeEnergyListener(EnergyListener listener) {
        listenerList.remove(EnergyListener.class, listener);
    }

    public Batery() {

        this.initialPower = INFINIT;
        this.currentPower = initialPower;
    }

    public Batery(double initialPower) {

        this.initialPower = initialPower;
        this.currentPower = initialPower;
    }

    /**
     * @return the currentPower
     */
    public double getCurrentPower() {
        return currentPower;
    }

    public boolean off() {
        if (currentPower <= 0) {
          //  System.out.println("Duration Time: " + (stopTime - startTime));
            return true;
        } else {

            return false;
        }

    }

    public synchronized void consume(double value) {
            currentPower -= value;
            //System.out.println("Consume Batery power");
            sampleCount++;
        fireOnEnergyConsume(new EnergyEvent(this));
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

    public void consumeTransmission(double time) {
        //System.out.println("consumeTransmission: " + time);
        consume(TRASMISSION_CONSUMPTION * time);

    }

    public void consumeReceiving() {
        //System.out.println("consumeReceiving: " );
        consume(RECEIVING_CONSUMPTION);
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

    public void start() {
        startTime = System.currentTimeMillis();
    }
}