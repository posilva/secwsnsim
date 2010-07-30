/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.platform.core.instruments.energy;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.wisenet.platform.core.instruments.energy.ui.EnergyWatcherDialog;
import org.wisenet.simulator.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class EnergyHook extends Thread {

    protected Node node;
    private boolean hooked = false;
    protected boolean end = false;
    protected long period = 1000;
    protected EnergyWatcherDialog watcher;

    public EnergyHook() {
    }

    public EnergyHook(long period) {
        this.period = period;
    }

    public void hook(Node n) {
        if (n == null) {
            throw new IllegalStateException("Node cannot be null");
        }
        this.node = n;
        hooked = true;
    }

    @Override
    public void run() {
        super.run();
        if (!hooked) {
            throw new IllegalStateException("You must hook to a node");
        }
        watcher = new EnergyWatcherDialog(null, false);
        watcher.setSize(800, 600);
        watcher.pack();
        watcher.setVisible(true);
        long startTime = System.currentTimeMillis();
        while (!end) {
            try {
                //node.getSimulator().getSimulationTimeInMillisec()
                watcher.updateChart((System.currentTimeMillis() - startTime) / 10000, node.getBateryEnergy().getAverageConsumption());
                sleep(period);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnergyHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static EnergyHook hookToNode(Node n, long period) {
        EnergyHook eh = new EnergyHook(period);
        eh.hook(n);
        return eh;
    }

    public static EnergyHook hookToNode(Node n, long period, boolean start) {
        EnergyHook eh = hookToNode(n, period);
        if (start) {
            eh.start();
        }
        return eh;
    }
}
