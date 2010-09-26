/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.output;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class RoutingOutput extends LayerOutput {

    public static RoutingOutput getInstance() {
        if (instance == null) {
            instance = new RoutingOutput();
        }
        return (RoutingOutput) instance;
    }
}
