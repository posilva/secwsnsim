/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.core.node.components;

import java.util.Random;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author posilva
 */
public abstract class NodeComponent {

    protected static Random onOff = new Random();
    protected Node node;
    protected boolean on;

    public NodeComponent(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public boolean isOn() {
        // implements some kind of random state in the component
        return onOff.nextBoolean();
    }

    protected void switchON() {
        if (!isOn()) {
            consumeTransitionToONEnergy();
            on = true;
        }
    }

    protected void switchOFF() {
        on = false;
    }

    protected abstract void consumeTransitionToONEnergy();
}
