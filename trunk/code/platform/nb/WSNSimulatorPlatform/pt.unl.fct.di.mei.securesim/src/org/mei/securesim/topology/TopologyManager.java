package org.mei.securesim.topology;

import java.awt.Rectangle;
import java.util.ArrayList;
import org.mei.securesim.core.nodes.Node;

public abstract class TopologyManager {
    public abstract ArrayList<Node>  apply(Rectangle rect,ArrayList<Node> nodes) ;
}
