/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.routing;

import org.wisenet.simulator.utilities.annotation.Annotated;
import org.wisenet.simulator.utilities.annotation.Parameter;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class RoutingLayerProperties implements Annotated {

    @Parameter(className = "java.lang.Double", label = "Radio Strenght Threshold", value = "0.8")
    public static Double radioStrenghtThreshold = 0.8;
    @Parameter(className = "java.lang.Integer", label = "Message Queue Dispatch Rate (Second)", value = "3")
    public static Integer messageQueueDispatchRate = 3;
}
