/**
 * 
 */
package org.wisenet.simulator.core.energy.listeners;

import java.util.EventListener;

/**
 * @author posilva
 *
 */
public interface EnergyListener extends EventListener {
    void onConsume(EnergyEvent evt);    
}
