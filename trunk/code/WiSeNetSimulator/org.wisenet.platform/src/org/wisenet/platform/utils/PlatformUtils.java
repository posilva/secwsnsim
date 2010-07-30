/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.platform.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.wisenet.platform.core.PlatformController;
import org.wisenet.platform.utils.PlatformUtils.SimulationFilter;
import org.wisenet.platform.utils.gui.GUI_Utils;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.simulation.SimulationConfiguration;

/**
 *
 * @author Pedro Marques da Silva
 */
public class PlatformUtils {

    private static String lastErrorMessage = null;

    /**
     * Executed when Save Simulation button is selected in platform
     * @param simulation
     * @return
     */
    public static boolean saveSimulation(Simulation simulation) {
        if (simulation == null) {
            lastErrorMessage = PlatformConstants.MSG_SIMULATION_IS_NULL;
            return false;
        }
        String simulationFile = null;
        if (PlatformController.getInstance().isChanged()) {
            // start to save the simulation

            if (PlatformController.getInstance().isNewSimulation()) {
                simulationFile = selectSimulationFileLocation();
            } else {
                simulationFile = PlatformController.getInstance().getActiveSimulationPath();
            }

            if (simulationFile != null) {
                if (!saveSimulationToFile(simulation, simulationFile)) {
                    String lastError = getLastErrorMessage();
                    if (lastError == null) {
                        lastError = PlatformConstants.MSG_UNKNOWNED_ERROR;
                    }
                    GUI_Utils.showMessage(lastError, JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        PlatformController.getInstance().setNewSimulation(false);
        PlatformController.getInstance().showSimulationName(simulationFile);
        PlatformController.getInstance().setActiveSimulationPath(simulationFile);
        return true;
    }

    public static boolean openSimulation() {
        return true;
    }

    private static String selectSimulationFileLocation() {
        String result = GUI_Utils.showSaveDialog(new FileFilter[]{new SimulationFilter()}, lastErrorMessage);
        if (result == null) {
            lastErrorMessage = PlatformConstants.MSG_SAVE_ERROR_OR_CANCEL;
        }
        result = result.endsWith(PlatformConstants.SIMULATION_FILE_EXTENSION) ? result : result + PlatformConstants.SIMULATION_FILE_EXTENSION;
        return result;

    }

    private static boolean saveSimulationToFile(Simulation simulation, String simulationFile) {
        FileWriter writer = null;

        File sf = new File(simulationFile);
        String backup = null;
        if (sf.exists()) {
            backup = createBackupFromFile(sf);
        }
        SimulationConfiguration sc = new SimulationConfiguration();
        if (sc.save(simulation, simulationFile)) {
            try {
                new File(backup).delete();

            } catch (Exception e) {
            }
        }
        return true;


    }

    private static void clearLastErrorMessage() {
        lastErrorMessage = null;
    }

    private static String getLastErrorMessage() {
        return lastErrorMessage;
    }

    private static String createBackupFromFile(File sf) {
        try {
            File tFile = File.createTempFile(".sim_", ".bkp");
            System.out.println("FALTA IMPLEMENTAR A COPIA DE BACKUP");
            return tFile.getAbsolutePath();
        } catch (IOException ex) {
            Logger.getLogger(PlatformUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static class SimulationFilter extends FileFilter {

        public boolean accept(File file) {
            String filename = file.getName();
            return filename.endsWith(PlatformConstants.SIMULATION_FILE_EXTENSION);
        }

        public String getDescription() {
            return PlatformConstants.SIMULATION_FILE_DESCRIPTION;
        }
    }
}
