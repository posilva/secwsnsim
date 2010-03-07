/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.utils;

import javax.swing.event.EventListenerList;

/**
 *
 * @author POSilva
 */
public final class SimulationUtils {

    protected static EventListenerList listenerList = new EventListenerList();

    private static SimulationUtils instance = null;

    public SimulationUtils getInstance() {
        if (instance == null) {
            return new SimulationUtils();
        }
        return instance;
    }

    public static void debug(Object o) {
        System.out.println(o);
        fireOnDebug(new DebugEvent(o));
    }

    public static void addSimulationOnDebugListener(SimulationListener listener) {
        listenerList.add(SimulationListener.class, listener);
    }

    public static void removeSimulationOnDebugListener(SimulationListener listener) {
        listenerList.remove(SimulationListener.class, listener);
    }

    protected static synchronized void fireOnDebug(DebugEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                ((SimulationListener) listeners[i + 1]).onDebug(evt);
            }
        }
    }
}
