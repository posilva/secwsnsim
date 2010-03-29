/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.energy;

import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
 */
public abstract class EnergyConsumerState {
    long time;
    long timeSpent;
    double consumptionValue;
    protected Node node;

    public EnergyConsumerState(Node node) {
        this.node = node;
    }

    public final void consume() {
        start();
        process();
        end();
        updateEnergy();
    }

    private void start() {
        time = System.nanoTime();
    }

    private void end() {
        timeSpent=System.nanoTime()-time;

    }

    protected abstract void process();

    private void updateEnergy() {
        node.getBateryEnergy().consume(timeSpent*consumptionValue);
    }
}
