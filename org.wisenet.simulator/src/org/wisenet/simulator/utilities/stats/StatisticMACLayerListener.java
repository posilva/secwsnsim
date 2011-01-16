/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */

package org.wisenet.simulator.utilities.stats;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface StatisticMACLayerListener {
    public void updateStats(double corruptedRate,double lostMessagesRate,double notReceivedRate);
}
