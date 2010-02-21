package pt.unl.fct.di.mei.securesim.topology;

import pt.unl.fct.di.mei.securesim.core.Controller;
import pt.unl.fct.di.mei.securesim.network.Network;

public abstract class TopologyManager extends Controller {
	public  abstract void apply(Network network);
}
