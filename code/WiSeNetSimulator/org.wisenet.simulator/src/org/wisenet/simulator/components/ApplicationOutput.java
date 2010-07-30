/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components;

import java.util.HashSet;
import java.util.Set;
import org.wisenet.simulator.core.application.Application;

/**
 *
 * @author CIAdmin
 */
public class ApplicationOutput {

    private static ApplicationOutput instance;

    public static ApplicationOutput getInstance() {
        if (instance == null) {
            instance = new ApplicationOutput();
        }
        return instance;
    }
    Set<IOutputDisplay> subscribers = new HashSet<IOutputDisplay>();

    public void output(Application app, String message) {
        displayOnSubscribers("<" + app.getClass().getSimpleName() + "> [" + app.getNode().getId() + "]\t" + message);
    }

    private synchronized void displayOnSubscribers(String output) {
        for (IOutputDisplay iApplicationOutputDisplay : subscribers) {
            iApplicationOutputDisplay.showOutput(output);
        }
    }

    public void subscribe(IOutputDisplay display) {
        subscribers.add(display);
    }

    public void unsubscribe(IOutputDisplay display) {
        subscribers.remove(display);
    }
}
