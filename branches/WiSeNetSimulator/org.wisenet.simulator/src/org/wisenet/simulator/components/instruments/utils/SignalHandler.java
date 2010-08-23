package org.wisenet.simulator.components.instruments.utils;

import java.util.Hashtable;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva
 */
public class SignalHandler extends Hashtable<Node, Integer> {

    
    /**
     * Flag that controls when a signal or a unsignal was called
     * the calculations are repeated only if is dirty
     */
    protected boolean dirty = true;
    protected int totalOfNodes = 0;
    protected double lastResult;
    /**
     * Number of signals  that are considered 100% coverage for each node
     */
    protected int threshold = 1;

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void signal(Node n) {
        dirty=true;
        if (get(n) == null) {
            put(n, 0);
        }
        Integer i = get(n);
        i++;
        put(n, i);
    }

    public void unSignal(Node n) {
        dirty=true;
        if (get(n) == null) {
            put(n, 0);
        } else {
            Integer i = get(n);
            i--;
            put(n, i);
        }
    }

    /**
     * This method calculates the value that network have referent to the
     * defined threshold and the number of signals registered for each node
     * @return the value
     */
    public double calculateGlobalThreshold() {
        double total = 0;
        if (size() == 0) {
            return 0.0;
        }
        if (!dirty) return lastResult;
        for (Node node : keySet()) {
            double percent = getSignalPercent(node);
            if (percent == 100) {
                total++;
            }
        }
        // returns a average value for 
        lastResult = (total * 100 )/ totalOfNodes;
        dirty=false;
        return lastResult;
    }

    /**
     * Util function to calculate the value of signals per node
     * based on threshold
     * @param node
     * @return
     */
    private double getSignalPercent(Node node) {
        int value = get(node);
        if (value == 0) {
            return 0.0;
        }
        if (value >= threshold) {
            return 100.0;
        }
        return value * 100 / threshold;

    }

    public int getTotalOfNodes() {
        return totalOfNodes;
    }

    public void setTotalOfNodes(int totalOfNodes) {
        this.totalOfNodes = totalOfNodes;
    }
}
