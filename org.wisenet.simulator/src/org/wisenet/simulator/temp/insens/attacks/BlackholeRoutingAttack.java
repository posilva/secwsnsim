/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.temp.insens.attacks;

import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.layers.routing.attacks.AbstractRoutingAttack;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class BlackholeRoutingAttack extends AbstractRoutingAttack implements IRuntimeEditable {

    public BlackholeRoutingAttack(RoutingLayer routingLayer) {
        super(routingLayer);
    }

    @Override
    public Object attack(Object message) {
        if (getRoutingLayer().isStable()) {
            return null; // Supress message
        } else {
            return message;
        }
    }

    
}
