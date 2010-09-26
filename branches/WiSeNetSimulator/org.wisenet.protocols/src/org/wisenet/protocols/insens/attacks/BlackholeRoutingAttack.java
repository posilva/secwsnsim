/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.attacks;

import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.layers.routing.attacks.AbstractRoutingAttack;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class BlackholeRoutingAttack extends AbstractRoutingAttack implements IRuntimeEditable {

    public BlackholeRoutingAttack() {
        super();
    }

    public BlackholeRoutingAttack(RoutingLayer routingLayer) {
        super(routingLayer);
    }

    @Override
    public Object attack(Object message) {
        if (getRoutingLayer().isStable()) {
	System.out.println("Message suppressed at node CHANGED");	
            return null; // Supress message
        } else {
System.out.println("Message NOT suppressed at node CHANGED");	
            return message;
        }
    }
}
