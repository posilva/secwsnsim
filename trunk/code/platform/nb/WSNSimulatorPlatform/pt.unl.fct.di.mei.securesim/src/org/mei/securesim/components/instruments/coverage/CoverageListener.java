/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.coverage;

import java.util.EventListener;
import org.mei.securesim.components.instruments.listeners.SignalUpdateEvent;

/**
 *
 * @author CIAdmin
 */
public interface CoverageListener extends EventListener {

    public void onSignalUpdate(SignalUpdateEvent event);
}
