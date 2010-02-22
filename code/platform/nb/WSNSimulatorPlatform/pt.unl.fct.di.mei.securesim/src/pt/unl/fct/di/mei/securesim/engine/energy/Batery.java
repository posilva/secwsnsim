package pt.unl.fct.di.mei.securesim.engine.energy;

public class Batery {

    public static final double INFINIT = -1;
    public static final double TRASMISSION_CONSUMPTION = 0.005;
    private double currentPower = INFINIT;
    private static final double RECEIVING_CONSUMPTION = 0.001;
    private int sampleCount = 0;
    private long startTime;
    private long stopTime;

    public Batery() {
        startTime = System.currentTimeMillis();
    }

    /**
     * @param currentPower
     *            the currentPower to set
     */
    public void setCurrentPower(double currentPower) {
        this.currentPower = currentPower;
    }

    /**
     * @return the currentPower
     */
    public double getCurrentPower() {
        return currentPower;
    }

    public boolean off() {
        if (currentPower <= 0) {
            System.out.println("Duration Time: " + (stopTime - startTime));
            return true;
        } else {

            return false;
        }

    }

    public void consume(double value) {
        synchronized (this) {
            currentPower -= value;
            //System.out.println("Consume Batery power");
            sampleCount++;
        }
        fireOnEnergyConsume(new EnergyEvent(currentPower));
    }

    private void fireOnEnergyConsume(EnergyEvent energyEvent) {


        if (sampleCount > 10000) {
        //    System.out.println("Power: " + getCurrentPower());
            sampleCount = 0;

        }
        if (getCurrentPower() <= 0) {
            stopTime = System.currentTimeMillis();
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
}
