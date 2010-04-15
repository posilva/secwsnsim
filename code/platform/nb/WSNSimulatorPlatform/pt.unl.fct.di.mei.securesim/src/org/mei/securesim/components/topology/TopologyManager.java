package org.mei.securesim.components.topology;

import java.awt.Rectangle;
import java.util. Vector;
import org.mei.securesim.core.nodes.Node;

public abstract class TopologyManager {
    public abstract  Vector<Node>  apply(Rectangle rect, Vector<Node> nodes) ;
}
