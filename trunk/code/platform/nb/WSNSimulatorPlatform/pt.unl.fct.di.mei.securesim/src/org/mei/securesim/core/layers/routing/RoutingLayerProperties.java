/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.layers.routing;

import org.mei.securesim.utils.annotation.Parameter;

/**
 *
 * @author CIAdmin
 */
public class RoutingLayerProperties {

    @Parameter(className = "java.lang.Double", label = "Radio Strenght Threshold", value = "0.8")
    protected Double radioStrenghtThreshold;
    @Parameter(className = "java.lang.Long", label = "Message Dispatch Rate (Second)", value = "3")
    protected Double messageDispatchRate ;

    public static  void main(){
        
    }
}
