/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.utils;

import java.util.EventListener;

/**
 *
 * @author POSilva
 */
public interface SimulationListener extends EventListener {

    public void onDebug (DebugEvent evt);
}
