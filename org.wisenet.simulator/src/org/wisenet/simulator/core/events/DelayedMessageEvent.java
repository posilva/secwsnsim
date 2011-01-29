/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.events;

import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva
 */
/**
 * Class que possibilita o envio de uma mensagens usando
 * um temporizador
 */
public class DelayedMessageEvent extends DelayedEvent {

    Object message;
    Node node;
    boolean reliable = false;

    /**
     *
     * @return
     */
    public Node getNode() {
        return node;
    }

    /**
     *
     * @param node
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     *
     * @return
     */
    public Object getMessage() {
        return message;
    }

    /**
     *
     * @return
     */
    public boolean isReliable() {
        return reliable;
    }

    /**
     *
     * @param reliable
     */
    public void setReliable(boolean reliable) {
        this.reliable = reliable;
    }

    /**
     *
     * @param time
     * @param delay
     * @param message
     * @param node
     */
    public DelayedMessageEvent(long time, long delay, Object message, Node node) {
        super(time, delay);
        this.message = message;
        this.node = node;
    }

    /**
     *
     * @param time
     * @param delay
     * @param message
     * @param node
     * @param reliable
     */
    public DelayedMessageEvent(long time, long delay, Object message, Node node, boolean reliable) {
        super(time, delay);
        this.message = message;
        this.node = node;
        this.reliable = reliable;
    }

    public void execute() {
        if (reliable) {
            if (!getNode().getMacLayer().sendMessage(getMessage(), getNode().getRoutingLayer())) {
                this.setTime(getTime() + Simulator.ONE_SECOND);
                System.out.println("Retrie send reliable message");
                getNode().getSimulator().addEvent(this);
            }

        } else {
            // just sends the messages
            getNode().getMacLayer().sendMessage(getMessage(), getNode().getRoutingLayer());
        }
    }
}
