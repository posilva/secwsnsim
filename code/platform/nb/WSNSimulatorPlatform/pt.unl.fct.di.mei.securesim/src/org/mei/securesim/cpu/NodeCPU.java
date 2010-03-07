/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.cpu;

import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author POSilva
 */
public class NodeCPU {
    private Node node;

    public NodeCPU(Node n) {
        this.node = n;
    }

    public void execute(CPUProcess process){
        process.run();
        getNode().getBateryEnergy().consumeProcessing();
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
