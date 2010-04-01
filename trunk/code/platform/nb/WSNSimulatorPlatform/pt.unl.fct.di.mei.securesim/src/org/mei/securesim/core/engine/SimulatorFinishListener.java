/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.core.engine;

import java.util.EventListener;
import org.mei.securesim.core.engine.events.SimulatorEvent;

/**
 *
 * @author posilva
 */
public interface  SimulatorFinishListener extends EventListener{
    public void onFinish(SimulatorEvent evt);
}
