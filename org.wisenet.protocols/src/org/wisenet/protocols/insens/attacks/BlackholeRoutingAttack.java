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
public class BlackholeRoutingAttack extends AbstractRoutingAttack {

    /**
     *
     */
    public BlackholeRoutingAttack() {
        super();
    }

    /**
     *
     * @param routingLayer
     */
    public BlackholeRoutingAttack(RoutingLayer routingLayer) {
        super(routingLayer);
    }

    @Override
    public Object attackOnSend(Object message) {
        if (getRoutingLayer().isStable()) {
            log("Suppress Message");
            return null; // Supress message
        } else {

            return message;
        }
    }

    @Override
    public Object attackOnReceive(Object message) {
        return message;
    }

    @Override
    public Object attackOnDemand(Object message, Object command) {
        return message;
    }

    @Override
    public void prepare() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
