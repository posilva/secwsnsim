/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import org.wisenet.simulator.utilities.listeners.ExceptionEvent;
import org.wisenet.simulator.utilities.listeners.ExceptionListener;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class Utilities {

    private static EventListenerList listeners = new EventListenerList();

    /**
     * Utility method for loading classes
     * @param className
     * @return
     * @throws Exception
     */
    public static Class loadClass(String className) throws Exception {
        return Class.forName(className);
    }

    /**
     * Utility method for loading classes instances
     * @param className
     * @return
     * @throws Exception
     */
    public static Object loadClassInstance(String className) throws Exception {
        Class c = Class.forName(className);
        if (c != null) {
            return c.newInstance();
        } else {
            throw new Exception("Cannot instantiate class: " + className);
        }
    }

    public static void addExceptionListener(ExceptionListener listener) {
        listeners.add(ExceptionListener.class, listener);
    }

    public static void removeExceptionListener(ExceptionListener listener) {
        listeners.remove(ExceptionListener.class, listener);
    }

    public static void handleException(Exception ex) {
        logException(ex);
        fireException(ex);
    }

    private static void fireException(Exception ex) {
        Object[] ls = listeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = ls.length;
        for (int i = 0; i < numListeners; i += 2) {
            if (ls[i] == ExceptionListener.class) {
                // pass the event to the simulatorListeners event dispatch method
                ((ExceptionListener) ls[i + 1]).onError(new ExceptionEvent(ex));
            }
        }
    }

    private static void logException(Exception ex) {
        Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
    }
    /**
     * Returns a string with full exception stack trace
     * @param ex
     * @return
     */
    public static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
