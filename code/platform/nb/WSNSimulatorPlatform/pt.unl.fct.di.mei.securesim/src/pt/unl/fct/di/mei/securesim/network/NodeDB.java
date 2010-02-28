package pt.unl.fct.di.mei.securesim.network;

import java.util.Collection;

import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.utils.RandomList;

public class NodeDB {
	RandomList<Node> nodes = new RandomList<Node>();

	public void store(Node n) {
		nodes.add(n);
	}

	public void dispose(Node n) {
		if (n != null) {
			// seeds.remove( n ) ;
			nodes.remove(n);
			n.dispose();
		}
	}

	public int size() {
		return nodes.size();
	}

	public Node randomNode() {
		return nodes.randomElement();
	}

	public Collection<Node> nodes() {
		return nodes;
	}

	// Returns a set of seeds that form connected graph in a transitive sense.
	// static RandomList<Node> seeds = new RandomList<Node>() ;
	// static Collection<EndPoint> randomEndPoints( Node caller, int total ) {
	//
	// Set<EndPoint> res = new HashSet<EndPoint>() ;
	//			
	// if( seeds.isEmpty() )
	// res.add( nodes.randomElement().endpoint ) ;
	// else
	// while( res.size() < Math.min(total, seeds.size() ) ) {
	// res.add( seeds.randomElement().endpoint ) ;
	// }
	//			
	// seeds.add( caller ) ;
	// return res ;
	// }
}
