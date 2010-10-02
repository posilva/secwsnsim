/**
 * 
 */
package org.wisenet.simulator.components.topology;

import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;
import java.util.Random;

import org.wisenet.simulator.core.node.Node;

/**
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 *
 */
public class RandomTopologyManager extends TopologyManager {

    protected Random random = null;

    /**
     *
     */
    public RandomTopologyManager() {
        this.parameters = new RandomTopologyParameters();
    }

    /**
     * @param random the random to set
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * @return the random
     */
    public Random getRandom() {
        return random;
    }

    public Vector<Node> apply(Rectangle rect, Vector<Node> nodes) {
        int px = rect.x;
        int py = rect.y;
        if (random == null) {
            random = new Random();
        }

        for (Node node : nodes) {
            final double x = px + (random.nextDouble() * rect.width);
            final double y = py + (random.nextDouble() * rect.height);
            final double z = applyZ();
            node.setPosition(x, y, z);
        }
        return nodes;
    }

    @Override
    protected List<Node> createTopologyImpl() {
        try {
            if (random == null) {
                random = new Random();
            }
            int px = (Integer) parameters.get("x");
            int py = (Integer) parameters.get("y");
            int pw = (Integer) parameters.get("width");
            int ph = (Integer) parameters.get("height");
            int nNodes = (Integer) parameters.get("nodes");

            List<Node> nodes = nodeFactory.createNodes((int) nNodes);
            for (Node node : nodes) {
                final double x = px + (random.nextDouble() * pw);
                final double y = py + (random.nextDouble() * ph);
                final double z = applyZ();

                node.setPosition(x, y, z);
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
        if (nodeFactory.isStaticZ()) {
            return nodeFactory.getMinZ();
        } else {
            return nodeFactory.getMinZ() + (random.nextDouble() * nodeFactory.getMaxZ());
        }
    }

}
