/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.core.engine;

/**
 *
 * @author posilva
 */
public class PeriodicEvent extends Event {

    private int due;

    private int period;

    @Override
    public void execute() {
    }

    protected void reschedule(){
        
    }

}
