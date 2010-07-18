package org.mei.securesim.components.instruments.energy;

import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.components.logging.EnergyLogger;
import org.mei.securesim.core.energy.listeners.EnergyEvent;
import org.mei.securesim.core.energy.listeners.EnergyListener;

/**
 *
 * @author posilva
 */
public class EnergyInstrument implements EnergyListener {

    protected GlobalEnergyDatabase database = new GlobalEnergyDatabase();
    protected static EnergyInstrument instance = null;

    public static EnergyInstrument getInstance() {
        if (instance == null) {
            instance = new EnergyInstrument();
        }
        return instance;

    }
    protected EnergyLogger energyLogger;

    public EnergyInstrument() {
    }

    public GlobalEnergyDatabase getDatabase() {
        return database;
    }

    public synchronized void onConsume(EnergyEvent evt) {
        String ev;
        final long simTime = SimulationController.getInstance().getSimulation().getTime();
        ev = evt.getEvent();
        if (SimulationController.getInstance().isLogEnergyEnable()) {
            if (energyLogger != null) {
                energyLogger.update(evt.getNodeid(), ev, evt.getRealTime(), simTime, evt.getValue(), evt.getState());
            }
        }
        database.addConsumption(evt.getNodeid(), ev, evt.getRealTime(), simTime, evt.getValue(), evt.getState());
    }

    public EnergyLogger getEnergyLogger() {
        return energyLogger;
    }

    public void setEnergyLogger(EnergyLogger energyLogger) {
        this.energyLogger = energyLogger;
        this.energyLogger.init();
    }

    public void start() {
        if (energyLogger != null) {
            energyLogger.open();
        }

    }

    public void stop() {
        if (energyLogger != null) {
            energyLogger.close();
        }

    }
}
