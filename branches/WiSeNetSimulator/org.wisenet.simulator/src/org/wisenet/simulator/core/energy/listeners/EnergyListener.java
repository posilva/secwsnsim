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
    /**
     *
     * @param evt
     */
    void onConsume(EnergyEvent evt);
}
