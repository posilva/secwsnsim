/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.security;

import java.util.List;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author CIAdmin
 */
public class AttacksInstrument {

    public void selectRandomAttackNodes(int numNodes, String attack) {
        List nodes = SimulationController.getInstance().selectRandomNodes(numNodes);
        for (Object object : nodes) {
            Node n = (Node) object;
            n.getRoutingLayer().setInAttackMode(true);
            n.getRoutingLayer().getAttacksStatus().set(numNodes, Boolean.TRUE);
        }
    }
}
