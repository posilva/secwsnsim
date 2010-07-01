/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.latency;

/**
 *
 * @author CIAdmin
 */
public class LatencyStatisticsTableEntry {
    int minHops=Integer.MAX_VALUE;
    int maxHops=Integer.MIN_VALUE;
    double averageHops=0;
    // TODO: Calcular os dados estatisticos face aos tempos 
    int countSamples=0;
    int numberOfHops = 0;
    boolean valid = true;
    long startSimulationTime;
    long endSimulationTime;
    long startRealTime;
    long endRealTime;

    public void setNumberOfHops(int numberOfHops) {
        this.numberOfHops = numberOfHops;
    }

    public int getNumberOfHops() {
        return numberOfHops;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public long getEndRealTime() {
        return endRealTime;
    }

    public void setEndRealTime(long endRealTime) {
        this.endRealTime = endRealTime;
    }

    public long getEndSimulationTime() {
        return endSimulationTime;
    }

    public void setEndSimulationTime(long endSimulationTime) {
        this.endSimulationTime = endSimulationTime;
    }

    public long getStartRealTime() {
        return startRealTime;
    }

    public void setStartRealTime(long startRealTime) {
        this.startRealTime = startRealTime;
    }

    public long getStartSimulationTime() {
        return startSimulationTime;
    }

    public void setStartSimulationTime(long startSimulationTime) {
        this.startSimulationTime = startSimulationTime;
    }

    public double getAverageHops() {
        return averageHops;
    }

    public void setAverageHops(double averageHops) {
        this.averageHops = ((this.averageHops * countSamples) +averageHops);
        countSamples++;
        this.averageHops=this.averageHops/countSamples;
    }

    public int getMaxHops() {
        return maxHops;
    }

    public void setMaxHops(int maxHops) {
        this.maxHops = maxHops;
    }

    public int getMinHops() {
        return minHops;
    }

    public void setMinHops(int minHops) {
        this.minHops = minHops;
    }

}
