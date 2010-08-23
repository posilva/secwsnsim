package org.wisenet.simulator.components.instruments.energy;

import org.wisenet.simulator.components.logging.EnergyLogger;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.core.energy.listeners.EnergyEvent;
import org.wisenet.simulator.core.energy.listeners.EnergyListener;

/**
 *
 * @author posilva
 */
public class EnergyController implements EnergyListener {

    protected GlobalEnergyDatabase database = new GlobalEnergyDatabase();
    protected EnergyLogger energyLogger;
    private Simulation simulation = null;

    public EnergyController() {
    }

    public EnergyController(Simulation simulationManager) {
        this.simulation = simulationManager;
    }

    public GlobalEnergyDatabase getDatabase() {
        return database;
    }

    public synchronized void onConsume(EnergyEvent evt) {
        String ev;
        final long simTime = getSimulation().getTime();
        ev = evt.getEvent();
        if (getSimulation().isLogEnergyEnable()) {
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

    public void setSimulation(Simulation simualtion) {
        this.simulation = simualtion;
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
