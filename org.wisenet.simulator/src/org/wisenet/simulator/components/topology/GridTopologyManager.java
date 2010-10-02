/**
 * 
 */
package org.wisenet.simulator.components.topology;

import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.utilities.annotation.Annotated;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;



import org.wisenet.simulator.core.node.Node;

/**
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 * 
 */
public class GridTopologyManager extends TopologyManager implements Annotated {

    /**
     *
     */
    private int distance = 30;

    public GridTopologyManager() {
    }

    public Vector<Node> apply(Rectangle rect, Vector<Node> nodes) {
        double posX = rect.getX();
        double posY = rect.getY();
        int rows = (int) rect.getHeight() / distance;
        int cols = (int) (rect.getWidth() / distance);
        int total_nodes = 0;
        for (int i = 0; i < rows; i++) {
            posX = rect.getX();
            for (int j = 0; j < cols; j++) {

                nodes.get(total_nodes).setPosition(posX, posY, 0);
                posX += distance;
                total_nodes++;
            }
            posY += distance;
        }



        return nodes;
    }

    public void setDistance(int nDistance) {
        distance = nDistance;
    }

    @Override
    protected List<Node> createTopologyImpl() {
        int total_nodes = 0;
        List<Node> nodes = new ArrayList<Node>();
        try {

            int px = (Integer) parameters.get("x");
            int pxIni = px;
            int py = (Integer) parameters.get("y");
            int pw = (Integer) parameters.get("width");
            int ph = (Integer) parameters.get("height");

            int d = (Integer) parameters.get("distance");
            int r = (int) ph / d;
            int c = (int) pw / d;


            for (int i = 0; i <= r; i++) {
                px = pxIni;
                for (int j = 0; j <= c; j++) {
                    Node node = nodeFactory.createNode();
                    node.setPosition(px, py, applyZ());
                    nodes.add(node);
                    px += d;
                    total_nodes++;
                }
                py += d;
            }
            return nodes;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public Vector<Node> apply(Vector<Node> nodes, TopologyParameters parameters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private double applyZ() {
        Random random = Simulator.randomGenerator.random();
        if (nodeFactory.isStaticZ()) {
            return nodeFactory.getMinZ();
        } else {
            return nodeFactory.getMinZ() + (random.nextDouble() * nodeFactory.getMaxZ());
        }

    }
}
