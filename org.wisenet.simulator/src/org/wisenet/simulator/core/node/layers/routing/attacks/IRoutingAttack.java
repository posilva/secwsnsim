/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.routing.attacks;

import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface IRoutingAttack {
    /*
     * Gets if the attack is enabled
     */

    public boolean isEnable();

    /**
     * Set the attack enabled
     */
    public void enable();

    /**
     * Set the attack disabled
     */
    public void disable();

    /**
     * Gets routing layer 
     * @return
     */
    public RoutingLayer getRoutingLayer();

    /**
     * Perform attack under the message
     * @param message
     * @return
     */
    public Object attack(Object message);
}
