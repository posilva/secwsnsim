/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.unl.fct.di.mei.securesim.core;

import java.util.EventListener;
import pt.unl.fct.di.mei.securesim.core.events.SimulatorEvent;

/**
 *
 * @author posilva
 */
public interface  SimulatorFinishListener extends EventListener{
    public void onFinish(SimulatorEvent evt);
}
