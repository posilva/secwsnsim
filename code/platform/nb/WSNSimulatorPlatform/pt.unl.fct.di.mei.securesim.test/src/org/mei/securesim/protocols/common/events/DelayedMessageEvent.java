/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.common.events;

import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.events.DelayedEvent;
import org.mei.securesim.core.nodes.Node;

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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Object getMessage() {
        return message;
    }

    public boolean isReliable() {
        return reliable;
    }

    public void setReliable(boolean reliable) {
        this.reliable = reliable;
    }

    public DelayedMessageEvent(long time, long delay, Object message, Node node) {
        super(time, delay);
        this.message = message;
        this.node = node;
    }

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
