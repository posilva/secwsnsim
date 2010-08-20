/**
 * 
 */
package org.wisenet.simulator.components.topology;

import org.wisenet.simulator.utilities.annotation.Annotated;
import java.awt.Rectangle;
import java.util.Vector;



import org.wisenet.simulator.core.node.Node;

/**
 * @author posilva
 * 
 */
public class GridTopologyManager extends TopologyManager implements Annotated {

    /**
     *
     */
    private int distance = 30;
    private Node node;

    public GridTopologyManager() {
    }

    @Override
    public Vector<Node> apply(Rectangle rect, Vector<Node> nodes) {
        double posX = rect.getX();
        double posY = rect.getY();
        int rows = (int) rect.getHeight() / distance;
        int cols = (int) (rect.getWidth() / distance);
        int total_nodes=0;
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
}
