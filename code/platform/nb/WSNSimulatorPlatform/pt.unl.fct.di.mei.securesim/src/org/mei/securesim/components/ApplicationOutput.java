/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components;

import java.util.HashSet;
import java.util.Set;
import org.mei.securesim.core.application.Application;

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
    Set<IApplicationOutputDisplay> subscribers = new HashSet<IApplicationOutputDisplay>();

    public void output(Application app, String message) {
        displayOnSubscribers("<" + app.getClass().getSimpleName() + "> [" + app.getNode().getId() + "]\t" + message);
    }

    private synchronized void displayOnSubscribers(String output) {
        for (IApplicationOutputDisplay iApplicationOutputDisplay : subscribers) {
            iApplicationOutputDisplay.showOutput(output);
        }
    }

    public void subscribe(IApplicationOutputDisplay display) {
        subscribers.add(display);
    }
}
