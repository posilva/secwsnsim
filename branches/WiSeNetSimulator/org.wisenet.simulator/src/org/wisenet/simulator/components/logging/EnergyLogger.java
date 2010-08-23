/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.logging;

/**
 *
 * @author posilva
 */
public abstract class EnergyLogger {
   
    /**
     *
     */
    public abstract void init();

    /**
     *
     */
    public abstract void update(short id, String event,long realTime, long simTime, double value, String state);

    public abstract void open();
    public abstract void close();

}
