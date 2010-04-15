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

    protected EnergyLogger energyLogger;
    protected String filename;

    public EnergyController() {
        try {
            filename = "energy" + System.currentTimeMillis() + ".log";
            this.energyLogger = new EnergyLogger(filename);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EnergyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onConsume(EnergyEvent evt) {
    }
}
