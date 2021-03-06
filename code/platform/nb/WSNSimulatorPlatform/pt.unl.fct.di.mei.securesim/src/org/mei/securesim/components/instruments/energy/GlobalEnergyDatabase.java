/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.energy;

import java.util.Hashtable;

/**
 *
 * @author CIAdmin
 */
public class GlobalEnergyDatabase {

    Hashtable<String, Double> eventsEnergy = new Hashtable<String, Double>();
    Hashtable<Short, Double> nodesEnergy = new Hashtable<Short, Double>();
    Hashtable<String, Double> statesEnergy = new Hashtable<String, Double>();
    double networkEnergyConsumption = 0;

    public void addConsumption(short nodeid, String event, long realTime, long simTime, double value, String state) {
        saveEventEnergy(event, value);
        saveNodeEnergy(nodeid, value);
        saveStateEnergy(state, value);
        updateNetworkEnergyConsumption(value);
    }

    public Hashtable getTopNodes(int topValue) {
        return new Hashtable();
    }

    public Hashtable<String, Double> getEventsEnergy() {
        return eventsEnergy;
    }

    public Hashtable<String, Double> getStatesEnergy() {
        return statesEnergy;
    }

    public void reset() {
        eventsEnergy.clear();
    }

    private void saveEventEnergy(String event, double value) {
        saveEnergy(event, value, eventsEnergy);
    }

    private void saveNodeEnergy(Short nodeId, double value) {
        saveEnergy(nodeId, value, nodesEnergy);
    }

    private void saveStateEnergy(String state, double value) {
        saveEnergy(state, value, statesEnergy);
    }

    private void saveEnergy(Object key, double value, Hashtable table) {
        Double globalValue = (Double) table.get(key);
        if (globalValue == null) {
            globalValue = 0.0;
        }
        globalValue += value;
        table.put(key, globalValue);
    }

    private void updateNetworkEnergyConsumption(double value) {
        networkEnergyConsumption += value;
    }
}
