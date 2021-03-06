/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.radio;

import java.util.ArrayList;
import java.util.HashSet;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author CIAdmin
 */
public class INSENSGaussianRadioModel extends GaussianRadioModel {
//org.mei.securesim.core.radio.INSENSGaussianRadioModel

    @Override
    public void updateNeighborhoods() {
        super.updateNeighborhoods();
//        settingTwoWayNeighborHood();
    }

    private void settingTwoWayNeighborHood() {
        for (Node srcNode : getSimulator().getNodes()) {
            srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMe.clear();
        }
        for (Node srcNode : getSimulator().getNodes()) {
            Neighborhood neighborhood = (Neighborhood) srcNode.getMacLayer().getNeighborhood();
            ArrayList<Node> n = new ArrayList<Node>();
            HashSet<Node> ns = new HashSet<Node>();
            
            for (Node node : srcNode.getMacLayer().getNeighborhood().neighbors) {
                if (srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMeSet.contains(node)) {
                    n.add(node);
                    node.getMacLayer().getNeighborhood().neighborsThatKnowMe.add(srcNode);
                }
            }
            // guardo novos vizinhos
            srcNode.getMacLayer().getNeighborhood().neighbors = n;
            neighborhood.getDynamicStrengths().clear();
            for (int i = 0; i < neighborhood.neighbors.size(); i++) {
                neighborhood.getDynamicStrengths().add(0.0);
            }
        }
        // actualiza
        for (Node srcNode : getSimulator().getNodes()) {
            srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMeSet.clear();
            srcNode.getMacLayer().getNeighborhood().neighborsSet.clear();
            srcNode.getMacLayer().getNeighborhood().neighborsSet.addAll(srcNode.getMacLayer().getNeighborhood().neighbors);
            srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMeSet.addAll(srcNode.getMacLayer().getNeighborhood().neighborsThatKnowMe);
        }

    }
    
}
