/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.platform.utils;

import org.wisenet.platform.PlatformConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.utils.PlatformUtils.SimulationFilter;
import org.wisenet.simulator.components.simulation.AbstractSimulation;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.simulation.SimulationException;

/**
 *
 * @author Pedro Marques da Silva
 */
public class PlatformUtils {

    private static String lastErrorMessage = null;

    /**
     * Executed when Save AbstractSimulation button is selected in platform
     * @param simulation
     * @return
     */
    public static boolean saveSimulation(AbstractSimulation simulation) {
        if (simulation == null) {
            lastErrorMessage = PlatformConstants.MSG_SIMULATION_IS_NULL;
            return false;
        }
        String simulationFile = null;
        if (PlatformManager.getInstance().isChanged()) {
            // start to save the simulation

            if (PlatformManager.getInstance().isNewSimulation()) {
                simulationFile = selectSimulationSaveFileLocation();
            } else {
                simulationFile = PlatformManager.getInstance().getActiveSimulationPath();
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
            } else {
                GUI_Utils.showMessage(getLastErrorMessage(), JOptionPane.ERROR_MESSAGE);
            }

        }
        PlatformManager.getInstance().setNewSimulation(false);
        PlatformManager.getInstance().showSimulationName(simulationFile);
        PlatformManager.getInstance().setActiveSimulationPath(simulationFile);
        return true;
    }

    public static boolean openSimulation() {
        try {
            String file = PlatformUtils.selectSimulationOpenFileLocation();
            AbstractSimulation simulation = new Simulation();
            simulation.readFromFile(file);
            PlatformManager.getInstance().setActiveSimulation(simulation);
//            simulation.getSimulator().setDisplay(PlatformView.getInstance().getWorkbenchPanel().getSimulationPanel());
//            PlatformView.getInstance().getWorkbenchPanel().getSimulationPanel().setSimulation((Simulation) simulation);
//            PlatformView.getInstance().showWorkbench();

            return true;
        } catch (Exception ex) {
            GUI_Utils.showException(ex);
            return false;
        }
    }

    private static String selectSimulationOpenFileLocation() {
        String result = GUI_Utils.showOpenDialog(new FileFilter[]{new SimulationFilter()}, lastErrorMessage);
        if (result == null) {
            lastErrorMessage = PlatformConstants.MSG_OPEN_ERROR_OR_CANCEL;
            return null;
        }
        result = result.endsWith(PlatformConstants.SIMULATION_FILE_EXTENSION) ? result : result + PlatformConstants.SIMULATION_FILE_EXTENSION;
        return result;

    }

    private static String selectSimulationSaveFileLocation() {
        String result = GUI_Utils.showSaveDialog(new FileFilter[]{new SimulationFilter()}, lastErrorMessage);
        if (result == null) {
            lastErrorMessage = PlatformConstants.MSG_SAVE_ERROR_OR_CANCEL;
            return null;
        }
        result = result.endsWith(PlatformConstants.SIMULATION_FILE_EXTENSION) ? result : result + PlatformConstants.SIMULATION_FILE_EXTENSION;
        return result;

    }

    private static boolean saveSimulationToFile(AbstractSimulation simulation, String simulationFile) {
        try {
            FileWriter writer = null;
            File sf = new File(simulationFile);
            String backup = null;
            if (sf.exists()) {
                backup = createBackupFromFile(sf);
            }
            simulation.save(simulationFile);
            //        if (sc.save(simulation, simulationFile)) {
            //            try {
            //                new File(backup).delete();
            //
            //            } catch (Exception e) {
            //            }
            //        }
            return true;
        } catch (SimulationException ex) {
            Logger.getLogger(PlatformUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
    /**
     * Extension filter for simulation files
     */
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
