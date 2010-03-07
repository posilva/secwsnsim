/**
 * 
 */
package org.mei.securesim.core.energy;

import java.util.EventListener;

/**
 * @author posilva
 *
 */
public interface EnergyListener extends EventListener {
    void onConsume(EnergyEvent evt);    
}
