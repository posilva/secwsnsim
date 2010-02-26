/**
 * 
 */
package pt.unl.fct.di.mei.securesim.topology;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.network.Network;

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

    /* (non-Javadoc)
     * @see pt.unl.fct.di.mei.securesim.configuration.ConfigurableObject#init()
     */
    @Override
    protected void init() {
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
    public void apply(Network network) {
        if (random == null) {
            random = new Random();
        }
        for (Node n : network.getNodeDB().nodes()) {
            final double x = network.getSimulationArea().getWidth() * random.nextDouble();
            final double y = network.getSimulationArea().getHeigth() * random.nextDouble();
            final double z = network.getSimulationArea().getMaxElevation() * random.nextDouble();
            n.setPosition(x, y, z);
        }
    }

    @Override
    public ArrayList<Node> apply(Rectangle rect, ArrayList<Node> nodes) {
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
