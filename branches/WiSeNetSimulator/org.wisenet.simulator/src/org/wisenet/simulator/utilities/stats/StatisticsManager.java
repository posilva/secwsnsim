/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.stats;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.simulator.core.node.layers.mac.MACLayer;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class StatisticsManager {

    public static final int DEFAULT_MACSTAT_PROBE_INTERVAL = 1000;
    protected static StatisticsManager instance = new StatisticsManager();
    protected MACLayerStaticsThread macThread = null;
    protected Set<StatisticMACLayerListener> macLayerListeners = new HashSet<StatisticMACLayerListener>();
    protected boolean stopMacStatistic = false;
    private boolean macThreadRunning = false;
    private int macStatProbeInterval = DEFAULT_MACSTAT_PROBE_INTERVAL;

    public static StatisticsManager getInstance() {
        return instance;
    }

    public int getMacStatsProbeInterval() {
        return macStatProbeInterval;
    }

    public void startMACStatisticsProbe() {
        stopMacStatistic = false;
        if (macThreadRunning && macThread != null) {
            throw new IllegalStateException("MAC Statistics already running");
        }
        macThread = new MACLayerStaticsThread();
        macThread.start();
    }

    public void startMACStatisticsProbe(int interval) {
        setMACStatisticsProbeInterval(interval);
        startMACStatisticsProbe();
    }

    public void stopMACStatisticsProbe() {
        stopMacStatistic = true;

    }

    private void setMACStatisticsProbeInterval(int interval) {
        this.macStatProbeInterval = interval;
    }

    public synchronized void removeStatisticMacListener(StatisticMACLayerListener listener) {
        macLayerListeners.remove(listener);
    }

    public synchronized void addStatisticMacListener(StatisticMACLayerListener listener) {
        macLayerListeners.add(listener);
    }

    /**
     * This class enables outside components to query MACLayer Information related
     * with message delivering
     */
    public class MACLayerStaticsThread extends Thread {

        @Override
        public void run() {
            macThreadRunning = true;
            while (!stopMacStatistic) {
                try {
                    watchMacStatistics();
                    sleep(getMacStatsProbeInterval());
                } catch (InterruptedException ex) {
                    Logger.getLogger(StatisticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            macThreadRunning = false;
        }
    }

    /**
     * Notify listeners 
     */
    private void watchMacStatistics() {
        double lostMessagesRate = MACLayer.getController().getMessageNotSentRate();
        double corruptedRate = MACLayer.getController().getMessageCorruptedRate();
        double notReceivedRate= MACLayer.getController().getNotReceivedMessagesRate();
        if (macLayerListeners.size() > 0) {

            for (StatisticMACLayerListener listener : macLayerListeners) {
                listener.updateStats(corruptedRate, lostMessagesRate,notReceivedRate);
            }
        }
    }
}
