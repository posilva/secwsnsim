package org.wisenet.simulator.core.energy;

import org.wisenet.simulator.components.instruments.logging.EnergyLogger;
import org.wisenet.simulator.core.energy.listeners.EnergyEvent;
import org.wisenet.simulator.core.energy.listeners.EnergyListener;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class EnergyController implements EnergyListener {

    protected GlobalEnergyDatabase database = new GlobalEnergyDatabase();
    protected EnergyLogger energyLogger;
    private boolean logEnergyEnable = false;

    public EnergyController() {
        System.err.println("Energy Controller - TODO: Access the simulator time");
    }

    public GlobalEnergyDatabase getDatabase() {
        return database;
    }

    /**
     * Consumption event 
     * @param evt
     */
    public synchronized void onConsume(EnergyEvent evt) {
        String ev;
        final long simTime = 0;//Simulator.getSimulationTime(); //TODO Access the simulator time
        ev = evt.getEvent();
        if (isLogEnergyEnable()) {
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
        if (isLogEnergyEnable()) {
            if (energyLogger != null) {
                energyLogger.open();
            }
        }
    }

    public void stop() {
        if (energyLogger != null) {
            energyLogger.close();
        }
    }

    public boolean isLogEnergyEnable() {
        return logEnergyEnable;
    }

    public void setLogEnergyEnable(boolean logEnergyEnable) {
        this.logEnergyEnable = logEnergyEnable;
    }
}
