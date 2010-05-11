/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components;

import java.io.FileNotFoundException;
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
    protected EnergyLogger energyLogger;
    protected String filename;

    public EnergyController() {
        try {
            filename = "energy" + System.currentTimeMillis() + ".xml";
            this.energyLogger = new EnergyLogger(filename);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EnergyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onConsume(EnergyEvent evt) {
    }

    public void log(String logText) {
        this.energyLogger.log(logText);
    }
}
