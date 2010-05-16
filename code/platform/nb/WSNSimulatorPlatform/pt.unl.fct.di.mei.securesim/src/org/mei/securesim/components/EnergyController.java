package org.mei.securesim.components;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    protected Stack energyPhases = new Stack();
    protected EnergyLogger energyLogger;
    protected String filename;

    public EnergyController() {
        try {
            filename = "energy" + System.currentTimeMillis() + ".xml";
            this.energyLogger = new EnergyLogger(filename);
            startPhase("EnergyConsumption");
        } catch (IOException ex) {
            Logger.getLogger(EnergyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void onConsume(EnergyEvent evt) {
        if (SimulationController.getInstance().isLogEnergyEnable()) {
            energyLogger.openTag("energy");
                energyLogger.writeTag("node", "" + evt.getNodeid());
                energyLogger.writeTag("event", "" + evt.getEvent());
                energyLogger.writeTag("time", "" + evt.getTime());
                energyLogger.writeTag("value", "" + evt.getValue());
            energyLogger.closeTag("energy");
        }
    }

    public synchronized void startPhase(String name) {
        String tag = name.replaceAll(" ", "_");
        this.energyLogger.openTag(tag);
        this.energyPhases.push(tag);
    }

    public synchronized void endPhase() {
        String tag = (String) this.energyPhases.pop();
        this.energyLogger.closeTag(tag);
    }

    public synchronized void endPhase(String name) {
        String tag = (String) this.energyPhases.pop();
        this.energyLogger.closeTag(tag);
    }

    public void close() {
        endPhase("EnergyConsumption");
        energyLogger.close();
    }
}
