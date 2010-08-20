/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments.attack;

import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class AttacksFactory {

    /**
     * Do a network attack based on the routingLayer specs
     * @param routingLayer
     * @param message
     */
    public abstract void doNetworkAttack(RoutingLayer routingLayer, Object message);
}
