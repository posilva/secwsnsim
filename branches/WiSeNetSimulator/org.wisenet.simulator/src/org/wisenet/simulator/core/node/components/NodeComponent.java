/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.components;

import java.util.Random;
import org.wisenet.simulator.core.node.Node;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class NodeComponent {

    /**
     *
     */
    protected static Random onOff = new Random();
    /**
     *
     */
    protected Node node;
    /**
     *
     */
    protected boolean on;

    /**
     *
     * @param node
     */
    public NodeComponent(Node node) {
        this.node = node;
    }

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
    public boolean isOn() {
        // implements some kind of random state in the component
        return onOff.nextBoolean();
    }

    /**
     *
     */
    protected void switchON() {
        if (!isOn()) {
            consumeTransitionToONEnergy();
            on = true;
        }
    }

    /**
     *
     */
    protected void switchOFF() {
        on = false;
    }

    /**
     *
     */
    protected abstract void consumeTransitionToONEnergy();
}
