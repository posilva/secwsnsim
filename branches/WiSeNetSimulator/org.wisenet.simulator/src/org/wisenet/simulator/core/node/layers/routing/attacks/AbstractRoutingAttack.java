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
public abstract class AbstractRoutingAttack implements IRoutingAttack {

    protected static boolean debugEnabed = true;
    protected boolean enable = false;
    protected RoutingLayer routingLayer = null;

    public AbstractRoutingAttack() {
    }

    public AbstractRoutingAttack(RoutingLayer routingLayer) {
        this.routingLayer = routingLayer;
    }

    public boolean isEnable() {
        return enable;
    }

    public void enable() {
        enable = true;
    }

    public void disable() {
        enable = false;
    }

    public RoutingLayer getRoutingLayer() {
        return routingLayer;
    }

    public abstract Object attack(Object message);

    public static boolean isDebugEnabed() {
        return debugEnabed;
    }

    public static void setDebugEnabed(boolean debugEnabed) {
        AbstractRoutingAttack.debugEnabed = debugEnabed;
    }

    public void log(String msg) {
        if (debugEnabed) {
            System.out.println(getClass().getSimpleName() + "-" + msg);
        }
    }
}
