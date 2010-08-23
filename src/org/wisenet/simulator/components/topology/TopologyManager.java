package org.wisenet.simulator.components.topology;

import java.awt.Rectangle;
import java.util. Vector;
import org.wisenet.simulator.core.node.Node;

public abstract class TopologyManager {
    public abstract  Vector<Node>  apply(Rectangle rect, Vector<Node> nodes) ;
}
