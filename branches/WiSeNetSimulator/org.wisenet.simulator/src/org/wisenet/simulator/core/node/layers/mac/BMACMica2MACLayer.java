/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */

package org.wisenet.simulator.core.node.layers.mac;

import java.util.Random;
import org.wisenet.simulator.core.Event;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class BMACMica2MACLayer extends Mica2MACLayer {

    boolean sleep =false;
    Random randomSleep= new Random();
    DutyCycleEvent dutyCycleEvent;

    /**
     *
     */
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
