/**
 * 
 */
package org.wisenet.simulator.core.energy.listeners;

import java.util.EventListener;

/**
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 *
 */
public interface EnergyListener extends EventListener {
    void onConsume(EnergyEvent evt);    
}
