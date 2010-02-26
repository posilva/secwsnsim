/**
 * 
 */
package pt.unl.fct.di.mei.securesim.topology;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;



import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.network.Network;
import pt.unl.fct.di.mei.securesim.network.nodes.SinkNode;

/**
 * @author posilva
 * 
 */
public class GridTopologyManager extends TopologyManager {
	/**
	 * 
	 */
	public GridTopologyManager() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pt.unl.fct.di.mei.securesim.configuration.ConfigurableObject#init()
	 */
	@Override
	protected void init() {

	}

	@Override
	public void apply(Network network) {
		//25*40=1000
		int h=network.getSimulationArea().getHeigth();
		int w=network.getSimulationArea().getWidth();
		int s=network.getNodeDB().size();
		int slices = (int) Math.sqrt(s) ;
		int parts = slices*slices;
		int colSize= w/slices;
		int rowSize= h/slices;
		int posx=0;
		int posy=0;
		Iterator<Node> it = network.getNodeDB().nodes().iterator();
		for (int r = 0; r < slices; r++) {
			posx=0;
			for (int c = 0; c < slices; c++) {
				Node n = it.next();
				n.setPosition(posx, posy, 0);
				if (n instanceof SinkNode){
					System.out.println("SinkNode: " + posx + ","+posy);
				}
				posx+=colSize;
			}
			posy+=rowSize;
		}
	}
	
	public int mdc( int x, int y ){
        if(x == y){
            return x;
        }
        else if( y > x ){
            return mdc( x, y-x );
        }
        else if( x > y ){
            return mdc( x - y, y );
        }
        return 0;
    }

    @Override
    public ArrayList<Node> apply(Rectangle rect, ArrayList<Node> nodes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
