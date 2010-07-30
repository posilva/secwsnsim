/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wisenet.simulator.core.layers.mac;

import java.util.Random;
import org.wisenet.simulator.core.engine.Event;

/**
 *
 * @author posilva
 */
public class BMACMica2MACLayer extends Mica2MACLayer {

    boolean sleep =false;
    Random randomSleep= new Random();
    DutyCycleEvent dutyCycleEvent;

    public BMACMica2MACLayer() {
        super();
    }



    class DutyCycleEvent extends Event{
        
        public DutyCycleEvent(long time) {
            super(time);

        }

        public DutyCycleEvent() {
        }

        @Override
        public void execute() {


        }


        
    }

}
