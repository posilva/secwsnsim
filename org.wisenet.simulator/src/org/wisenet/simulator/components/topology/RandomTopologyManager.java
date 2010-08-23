/**
 * 
 */
package org.wisenet.simulator.components.topology;

import java.awt.Rectangle;
import java.util. Vector;
import java.util.Random;

import org.wisenet.simulator.core.node.Node;

/**
 * @author posilva
 *
 */
public class RandomTopologyManager extends TopologyManager {

    protected Random random = null;

    /**
     *
     */
    public RandomTopologyManager() {
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

    @Override
    public  Vector<Node> apply(Rectangle rect,  Vector<Node> nodes) {
        int px = rect.x;
        int py = rect.y;
        if (random == null) {
            random = new Random();
        }

        for (Node node : nodes) {
            final double x = px + (random.nextDouble() * rect.width);
            final double y = py + (random.nextDouble() * rect.height);
            final double z = 0;
            node.setPosition(x, y, z);
        }
        return nodes;
    }
}
