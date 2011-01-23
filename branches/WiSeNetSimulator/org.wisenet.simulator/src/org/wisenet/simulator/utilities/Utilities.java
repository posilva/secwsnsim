/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import org.apache.commons.configuration.ConfigurationException;
import org.wisenet.simulator.utilities.listeners.ExceptionEvent;
import org.wisenet.simulator.utilities.listeners.ExceptionListener;
import org.apache.commons.configuration.XMLConfiguration;

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

    /**
     *
     * @param listener
     */
    public static void addExceptionListener(ExceptionListener listener) {
        listeners.add(ExceptionListener.class, listener);
    }

    /**
     *
     * @param listener
     */
    public static void removeExceptionListener(ExceptionListener listener) {
        listeners.remove(ExceptionListener.class, listener);
    }

    /**
     *
     * @param ex
     */
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

    /**
     *
     * @param file
     * @return
     */
    public static String networkTopologyFileIsValid(String file) {
        try {
            File f = new File(file);
            if (!f.exists()) {
                return "File not found";
            }
            XMLConfiguration configuration = new XMLConfiguration(f);
            configuration.load();
            try {
                Double d = configuration.getDouble("simulation.topology.nodes.size");
                if (d != null) {
                    return null;
                } else {
                    return "Invalid Network Topology File";
                }

            } catch (Exception e) {
                return "Invalid Network Topology File";
            }
        } catch (ConfigurationException ex) {
            return ex.getMessage();
        }
    }

    public static String testTopologyFileIsValid(String file) {
        return null;
    }

}
