/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.layers.routing;

import org.mei.securesim.utils.annotation.Annotated;
import org.mei.securesim.utils.annotation.Parameter;

/**
 *
 * @author CIAdmin
 */
public class RoutingLayerProperties implements Annotated {

    @Parameter(className = "java.lang.Double", label = "Radio Strenght Threshold", value = "0.8")
    public static Double radioStrenghtThreshold = 0.8;
    @Parameter(className = "java.lang.Integer", label = "Message Queue Dispatch Rate (Second)", value = "3")
    public static Integer messageQueueDispatchRate = 3;
}
