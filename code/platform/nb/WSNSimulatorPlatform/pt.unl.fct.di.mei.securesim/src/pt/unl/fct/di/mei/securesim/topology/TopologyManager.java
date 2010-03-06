package pt.unl.fct.di.mei.securesim.topology;

import java.awt.Rectangle;
import java.util.ArrayList;
import pt.unl.fct.di.mei.securesim.core.nodes.Node;

public abstract class TopologyManager {
    public abstract ArrayList<Node>  apply(Rectangle rect,ArrayList<Node> nodes) ;
}
