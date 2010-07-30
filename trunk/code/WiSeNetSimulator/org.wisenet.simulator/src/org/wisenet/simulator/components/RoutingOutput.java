/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components;

/**
 *
 * @author CIAdmin
 */
public class RoutingOutput extends LayerOutput {

    public static RoutingOutput getInstance() {
        if (instance == null) {
            instance = new RoutingOutput();
        }
        return (RoutingOutput) instance;
    }
}
