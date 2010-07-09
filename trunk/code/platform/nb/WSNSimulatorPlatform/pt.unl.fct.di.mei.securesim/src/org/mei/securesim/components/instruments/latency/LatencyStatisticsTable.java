/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.latency;

import java.util.Hashtable;
import org.mei.securesim.components.instruments.SimulationController;

/**
 *
 * @author CIAdmin
 */
public class LatencyStatisticsTable extends Hashtable<LatencyStatisticsTableKey, LatencyStatisticsTableEntry> {

    /**
     * 
     * @param message
     */
    void messageSent(ILatencyMessage message) {
        registerMessageSent(message);
    }

    /**
     * Handler to process the received message
     * @param message
     * @param node
     */
    void messageReceivedBy(ILatencyMessage message, ILatencyHandler node) {
        if (isDestinationNode(message, node)) {
            finalizeEntry(message);
        } else {
            incrementHop(message);
        }
    }

    /**
     * Verify if is the end of the line
     * @param message
     * @param node
     * @return
     */
    private boolean isDestinationNode(ILatencyMessage message, ILatencyHandler node) {
        return message.getDestinationId().equals(node.getLatencyUniqueId());
    }

    /**
     * Finalize the message entry
     * at this point we could make same statistic calculus
     * @param message
     */
    private void finalizeEntry(ILatencyMessage message) {
        LatencyStatisticsTableKey key = new LatencyStatisticsTableKey(message);
        LatencyStatisticsTableEntry entry = get(key);
        if (entry!=null){
            int numberOfHops = message.getNumberOfHops();
            entry.setMaxHops(Math.max(numberOfHops, entry.getMaxHops()));
            entry.setMinHops(Math.min(numberOfHops, entry.getMinHops()));
            entry.setAverageHops(numberOfHops);

            //TODO:ACRESCENTAR O MIN MAX E AVG DO TEMPO

        }
        put(key, entry);
    }

    /**
     * Add one more HOP to the hop counter
     * @param message
     */
    private void incrementHop(ILatencyMessage message) {
        message.incrementHop();
    }

    /**
     * Add information relative with message that was sent
     * @param message
     */
    private void registerMessageSent(ILatencyMessage message) {
        LatencyStatisticsTableKey key = new LatencyStatisticsTableKey(message);
        LatencyStatisticsTableEntry entry = new LatencyStatisticsTableEntry();
        entry.setStartRealTime(System.currentTimeMillis());
        entry.setStartSimulationTime(SimulationController.getInstance().getSimulation().getTime());
        entry.setValid(true);
        put(key, entry);
    }
}
