package pt.unl.fct.di.mei.securesim.test.broadcast;

import java.awt.Color;
import java.awt.Graphics;

import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;
import pt.unl.fct.di.mei.securesim.ui.IDisplayable;
import pt.unl.fct.di.mei.securesim.network.nodes.SinkNode;

public class BroadcastSinkNode extends SinkNode implements IDisplayable{

	public BroadcastSinkNode(Simulator sim, RadioModel radioModel) {
		super(sim, radioModel);
		setRoutingLayer(new BroadcastRoutingLayer());
	}


	/** This field is true if this mote rebroadcasted the message already. */
	boolean sent = false;

	/** This field stores the mote from which the message was first received. */
	private Node parent = null; 



    
	/**
	 * Draws a filled circle, which is: <br>
	 *  - blue if the node is sending a message <br>
	 *  - red if the node received a corrupted message <br>
	 *  - green if the node received a message without problems <br>
	 *  - pink if the node sent a message <br>
	 *  - and black as a default<br>
	 * It also draws a line between mote and its parent, which is another mote
	 * who sent the message first to this.
	 */ 
	public void displayOn(ISimulationDisplay disp){
		Graphics g = disp.getGraphics();
		super.displayOn(disp);
		int      x = disp.x2ScreenX(this.getX());
		int      y = disp.y2ScreenY(this.getY());
        if(getId()==1)
        	g.setColor( Color.yellow);
        	else       
		if( sending ){                    
			g.setColor( Color.blue );
		}else if( receiving ){
			if( corrupted )
				g.setColor( Color.red );
			else
				g.setColor( Color.green );                            
		}else{
			if( sent )
				g.setColor( Color.pink );
			else
				g.setColor( Color.black );
		}
		g.fillOval( x-5, y-5, 10, 10 );
		
//		if( parent != null ){
//			g.setColor( Color.black );
//			int x1 = disp.x2ScreenX(parent.getX());
//			int y1 = disp.y2ScreenY(parent.getY());
//			g.drawLine(x,y,x1,y1);
//		}
		if(getId()==1){
		for (Node n : getNeighborhood().neighbors) {
			g.setColor( Color.black );
			int x1 = disp.x2ScreenX(n.getX());
			int y1 = disp.y2ScreenY(n.getY());
			g.drawLine(x,y,x1,y1);
		}
		
		}
		g.drawOval(x-5, y-5, 10, 10 );
	}




	public void sentMenssage(boolean b) {
				sent=true;
	}




	/**
	 * @param parent the parent to set
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}




	/**
	 * @return the parent
	 */
	public Node getParent() {
		return parent;
	}




}
