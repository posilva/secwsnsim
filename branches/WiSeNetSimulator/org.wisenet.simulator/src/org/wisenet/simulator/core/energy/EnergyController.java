package org.wisenet.simulator.core.energy;

import java.util.Hashtable;
import org.wisenet.simulator.components.instruments.logging.EnergyLogger;
import org.wisenet.simulator.components.instruments.logging.file.EnergyRawFileLogger;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.listeners.EnergyEvent;
import org.wisenet.simulator.core.energy.listeners.EnergyListener;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class EnergyController implements EnergyListener {

    /**
     *
     */
    protected GlobalEnergyDatabase database = new GlobalEnergyDatabase();
    /**
     *
     */
    protected Hashtable<String, GlobalEnergyDatabase> activeDatabases = new Hashtable<String, GlobalEnergyDatabase>();
    /**
     *
     */
    protected EnergyLogger energyLogger = null;
    private boolean logEnergyEnable = false;

    /**
     *
     */
    public EnergyController() {
    }

    private void createDefaultEnergyLogger() {
        energyLogger = new EnergyRawFileLogger();
        energyLogger.init();
    }

    /**
     *
     * @return
     */
    public GlobalEnergyDatabase getDatabase() {
        return database;
    }

    /**
     * Consumption event 
     * @param evt
     */
    public synchronized void onConsume(EnergyEvent evt) {
        String ev;
        final long simTime = Simulator.getSimulationTime(); //TODO Access the simulator time
        ev = evt.getEvent();
        if (isLogEnergyEnable()) {
            if (energyLogger != null) {
                energyLogger.update(evt.getNodeid(), ev, evt.getRealTime(), simTime, evt.getValue(), evt.getState());
            }
        }
        database.addConsumption(evt.getNodeid(), ev, evt.getRealTime(), simTime, evt.getValue(), evt.getState());
        updateActiveDatabases(evt, ev, simTime);
    }

    private void updateActiveDatabases(EnergyEvent evt, String ev, final long simTime) {
        for (GlobalEnergyDatabase globalEnergyDatabase : activeDatabases.values()) {
            globalEnergyDatabase.addConsumption(evt.getNodeid(), ev, evt.getRealTime(), simTime, evt.getValue(), evt.getState());
        }
    }

    /**
     *
     * @return
     */
    public EnergyLogger getEnergyLogger() {
        return energyLogger;
    }

    /**
     *
     * @param energyLogger
     */
    public void setEnergyLogger(EnergyLogger energyLogger) {
        this.energyLogger = energyLogger;
        this.energyLogger.init();
    }

    /**
     *
     */
    public void start() {
        if (isLogEnergyEnable()) {
            if (energyLogger != null) {
                energyLogger.open();
            }
        }
    }

    /**
     *
     */
    public void stop() {
        if (energyLogger != null) {
            energyLogger.close();
        }
    }

    /**
     *
     * @return
     */
    public boolean isLogEnergyEnable() {
        return logEnergyEnable;
    }

    /**
     *
     * @param logEnergyEnable
     */
    public void setLogEnergyEnable(boolean logEnergyEnable) {
        this.logEnergyEnable = logEnergyEnable;
        if (this.logEnergyEnable){
            if (energyLogger==null){
                createDefaultEnergyLogger();
            }
        }
    }

    /**
     *
     */
    public void reset() {
        getDatabase().reset();
        activeDatabases.clear();
        if (getEnergyLogger() != null && this.logEnergyEnable) {
            getEnergyLogger().reset();
        }
    }

    /**
     * Create a database of energy consumption
     * @param name
     * @param deleteIfExists
     * @return
     *      NULL if database exists
     */
    public GlobalEnergyDatabase createDatabase(String name, boolean deleteIfExists) {
        GlobalEnergyDatabase db = activeDatabases.get(name);
        if (deleteIfExists == true) {
            if (db != null) {
                activeDatabases.remove(db);
            }
            db = null;
        }
        if (db == null) {
            db = new GlobalEnergyDatabase();
            activeDatabases.put(name, new GlobalEnergyDatabase());
        }

        return db;
    }

    /**
     * 
     * @param name
     * @return
     */
    public GlobalEnergyDatabase createDatabase(String name) {

        return createDatabase(name, false);
    }

    /**
     * Gets the active database by name
     * @param name
     *          the name of the registered database
     * @return
     *          the active database 
     */
    public GlobalEnergyDatabase getDatabase(String name) {
        return activeDatabases.get(name);
    }

    /**
     * Deletes a active database by name
     * @param name
     *          name of the registered database
     */
    public void deleteDatabase(String name) {
        activeDatabases.remove(name);
    }
}
