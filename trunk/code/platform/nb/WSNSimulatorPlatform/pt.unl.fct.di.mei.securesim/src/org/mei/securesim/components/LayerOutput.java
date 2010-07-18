/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components;

import java.util.HashSet;
import java.util.Set;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.Layer;

/**
 *
 * @author CIAdmin
 */
public abstract class LayerOutput {

    protected static LayerOutput instance;

    public static LayerOutput getInstance() {
        throw new IllegalStateException("Must create a derived class");
    }
    Set<IOutputDisplay> subscribers = new HashSet<IOutputDisplay>();

    public void output(Layer layer, String message) {
        displayOnSubscribers("<" + layer.getClass().getSimpleName() + "> [" + layer.getNode().getId() + "]\t" + message);
    }

    protected synchronized void displayOnSubscribers(String output) {
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
