/**
 * 
 */
package org.mei.securesim.core.energy.listeners;

import java.util.EventObject;
import org.mei.securesim.core.energy.Batery;

/**
 * @author posilva
 *
 */
public class EnergyEvent extends EventObject {
    private double value=0;

    public double getValue() {
        return value;
    }

	public EnergyEvent(Object arg0) {
		super(arg0);
	}

    public EnergyEvent(Batery aThis, double value) {
        super(aThis);
        this.value=value;
    }

}
