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

    /**
     *
     * @return
     */
    public boolean isEnable();

    /**
     * Set the attackOnSend enabled
     */
    public void enable();

    /**
     * Set the attackOnSend disabled
     */
    public void disable();

    /**
     * Gets routing layer 
     * @return
     */
    public RoutingLayer getRoutingLayer();

    /**
     * Perform attackOnSend under the message
     * @param message
     * @return the attacked message 
     */
    public Object attackOnSend(Object message);

    /**
     * Perform attackOnReceive under the message
     * @param message
     * @return the attacked message
     */
    public Object attackOnReceive(Object message);

    /**
     * Perform attackOnDemand under the message Based on command action
     * @param message
     * @param command 
     * @return the attacked message
     */
    public Object attackOnDemand(Object message, Object command);

    /**
     * 
     * @return
     */
    public void setRoutingLayer(RoutingLayer routingLayer);

    public void prepare();

    public void reset();
}
