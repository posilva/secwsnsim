/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.instruments.coverage;

import java.util.EventListener;
import org.wisenet.simulator.components.instruments.coverage.listeners.SignalUpdateEvent;

/**
 *
 * @author CIAdmin
 */
public interface CoverageListener extends EventListener {

    public void onSignalUpdate(SignalUpdateEvent event);
}
