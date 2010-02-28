package pt.unl.fct.di.mei.securesim.topology;

import java.awt.Rectangle;
import java.util.ArrayList;
import pt.unl.fct.di.mei.securesim.core.Controller;
import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.network.Network;

public abstract class TopologyManager extends Controller {
    public abstract ArrayList<Node>  apply(Rectangle rect,ArrayList<Node> nodes) ;
}
