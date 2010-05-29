/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.listeners;

import java.util.EventListener;

/**
 *
 * @author CIAdmin
 */
public interface CoverageListener extends EventListener {

    public void onSignalUpdate(SignalUpdateEvent event);
}
