package org.mei.securesim.components;

import org.mei.securesim.components.logging.EnergyLogger;
import org.mei.securesim.core.energy.listeners.EnergyEvent;
import org.mei.securesim.core.energy.listeners.EnergyListener;

/**
 *
 * @author posilva
 */
public class EnergyController implements EnergyListener {

    protected static EnergyController instance = null;

    public static EnergyController getInstance() {
        if (instance == null) {
            instance = new EnergyController();
        }
        return instance;

    }
    protected EnergyLogger energyLogger;

    public EnergyController() {
    }

    public synchronized void onConsume(EnergyEvent evt) {
        String ev;
        if (SimulationController.getInstance().isLogEnergyEnable()) {
            if (energyLogger != null) {
                ev=evt.getEvent();
                energyLogger.update(evt.getNodeid(), ev, evt.getTime(), 0, evt.getValue(), "nostate");
            }
        }
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
