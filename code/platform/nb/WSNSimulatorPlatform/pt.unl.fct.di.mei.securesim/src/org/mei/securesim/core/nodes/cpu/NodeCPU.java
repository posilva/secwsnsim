/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.nodes.cpu;

import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author POSilva
 */
public class NodeCPU {

    private Node node;
    private boolean on = false;

    public NodeCPU(Node n) {
        this.node = n;
    }

    public boolean isOn() {
        return on;
    }

    public void execute(CPUProcess process) {
        switchON();
        long start = System.nanoTime();
        process.run();
        long end = System.nanoTime();
        getNode().getBateryEnergy().consumeProcessing(end-start);
        switchOFF();
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    private void switchON() {
        if(!isOn()){
            getNode().getBateryEnergy().consumeCPUTransitionToON();
            on=true;
        }
    }

    private void switchOFF() {
        on=false;
    }
}
