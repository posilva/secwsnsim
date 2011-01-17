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

    /**
     *
     */
    protected static boolean debugEnabled = true;
    /**
     *
     */
    protected boolean enable = false;
    /**
     *
     */
    protected RoutingLayer routingLayer = null;

    /**
     *
     */
    public AbstractRoutingAttack() {
    }

    /**
     *
     * @param routingLayer
     */
    public AbstractRoutingAttack(RoutingLayer routingLayer) {
        this.routingLayer = routingLayer;
    }

    /**
     *
     * @return
     */
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

    public abstract Object attackOnSend(Object message);
    public abstract Object attackOnReceive(Object message);
    public abstract Object attackOnDemand(Object message,Object command);

    /**
     *
     * @return
     */
    public static boolean isDebugEnabed() {
        return debugEnabled;
    }

    /**
     *
     * @param debugEnabled
     */
    public static void setDebugEnabed(boolean debugEnabed) {
        AbstractRoutingAttack.debugEnabled = debugEnabed;
    }

    /**
     *
     * @param msg
     */
    public void log(String msg) {
        if (debugEnabled) {
            System.out.println(getClass().getSimpleName() + "-" + msg);
        }
    }

    public void setRoutingLayer(RoutingLayer routingLayer) {
        this.routingLayer=routingLayer;
    }

}
