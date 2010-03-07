/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.core;

import java.util.EventListener;
import org.mei.securesim.core.events.SimulatorEvent;

/**
 *
 * @author posilva
 */
public interface  SimulatorFinishListener extends EventListener{
    public void onFinish(SimulatorEvent evt);
}
