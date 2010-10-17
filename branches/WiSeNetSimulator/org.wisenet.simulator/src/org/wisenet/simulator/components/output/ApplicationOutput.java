/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.output;

import java.util.HashSet;
import java.util.Set;
import org.wisenet.simulator.core.Application;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ApplicationOutput {

    private static ApplicationOutput instance;

    /**
     *
     * @return
     */
    public static ApplicationOutput getInstance() {
        if (instance == null) {
            instance = new ApplicationOutput();
        }
        return instance;
    }
    Set<IOutputDisplay> subscribers = new HashSet<IOutputDisplay>();

    /**
     *
     * @param app
     * @param message
     */
    public void output(Application app, String message) {
        displayOnSubscribers("<" + app.getClass().getSimpleName() + "> [" + app.getNode().getId() + "]\t" + message);
    }

    private synchronized void displayOnSubscribers(String output) {
        for (IOutputDisplay iApplicationOutputDisplay : subscribers) {
            iApplicationOutputDisplay.showOutput(output);
        }
    }

    /**
     *
     * @param display
     */
    public void subscribe(IOutputDisplay display) {
        subscribers.add(display);
    }

    /**
     *
     * @param display
     */
    public void unsubscribe(IOutputDisplay display) {
        subscribers.remove(display);
    }
}
