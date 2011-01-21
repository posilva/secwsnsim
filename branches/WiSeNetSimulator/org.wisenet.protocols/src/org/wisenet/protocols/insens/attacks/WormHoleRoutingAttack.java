/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.attacks;

import java.util.Random;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.layers.routing.attacks.AbstractRoutingAttack;
import org.wisenet.simulator.utilities.RandomList;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class WormHoleRoutingAttack extends AbstractRoutingAttack {

    static RandomList nodeSet = new RandomList();
    static Random random = new Random();

    public synchronized void receiveFromTunnel(Object message) {
        getRoutingLayer().receiveMessage(message);
    }

    @Override
    public void setRoutingLayer(RoutingLayer routingLayer) {
        super.setRoutingLayer(routingLayer);
        register(routingLayer.getNode());
    }

    public WormHoleRoutingAttack(RoutingLayer routingLayer) {
        super(routingLayer);
        countMessagesAttacked++;
        register(routingLayer.getNode());
    }

    @Override
    public Object attackOnSend(Object message) {
        return message;
    }

    @Override
    public Object attackOnReceive(Object message) {
        countMessagesAttacked++;
        selectNodeToSend().getRoutingLayer().receiveMessage(message);
        return null;
    }

    @Override
    public Object attackOnDemand(Object message, Object command) {
        return message;

    }

    private void register(Node node) {
        if (!nodeSet.contains(node)) {
            nodeSet.add(node);
        }
    }

    protected Node selectNodeToSend() {
        return (Node) nodeSet.randomElement();
    }
}
