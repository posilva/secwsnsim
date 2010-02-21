package pt.unl.fct.di.mei.securesim.test.broadcast;

import java.awt.Color;
import java.awt.Graphics;

import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;
import pt.unl.fct.di.mei.securesim.ui.IDisplayable;
import pt.unl.fct.di.mei.securesim.network.nodes.SimpleNode;

public class BroadcastNode extends SimpleNode implements IDisplayable{

	public BroadcastNode(Simulator sim, RadioModel radioModel) {
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
    @Override
	public void displayOn(ISimulationDisplay disp){
		Graphics g = disp.getGraphics();
		super.displayOn(disp);
		int      _x = disp.x2ScreenX(this.getX());
		int      _y = disp.y2ScreenY(this.getY());
        Color c;
        if(getId()==1)
        	c=Color.yellow;
        	else
		if( sending ){
			c=Color.blue ;
		}else if( receiving) {
			if( corrupted )
				c=Color.red ;
			else
				c= Color.green;
		}else{
			if( sent )
				c= Color.pink ;
			else
				c=Color.black ;
		}
//		g.fillOval( _x-radius, _y-radius, radius*2, radius*2);
//
		if( parent != null ){
			g.setColor( Color.black );
			int x1 = disp.x2ScreenX(parent.getX());
			int y1 = disp.y2ScreenY(parent.getY());
			g.drawLine((int)_x,(int)_y,x1,y1);
		}
//		if(getId()==1){
//		for (Node n : getNeighborhood().neighbors) {
//			g.setColor( Color.black );
//			int x1 = disp.x2ScreenX(n.getX());
//			int y1 = disp.y2ScreenY(n.getY());
//			g.drawLine(_x,_y,x1,y1);
//		}
//
//		}
//		g.drawOval( _x-radius, _y-radius, radius*2, radius*2);
        getGraphicNode().setBackcolor(c);
        getGraphicNode().paint(disp);
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
