/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.common.events;

import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author Pedro Marques da Silva
 */
 /**
     * Class que possibilita o envio de uma mensagens usando
     * um temporizador
     */
    public class DelayedMessageEvent extends Event {

        Object message;
        Node node;

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        public Object getMessage() {
            return message;
        }

        public DelayedMessageEvent(long time, Object message, Node node) {
            super(time);
            this.message = message;
            this.node = node;
        }

        public void execute() {
            if (!getNode().getMacLayer().sendMessage(getMessage(), getNode().getRoutingLayer())) {

            } else {
            }
        }
    }