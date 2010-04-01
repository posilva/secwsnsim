/**
 * 
 */
package org.mei.securesim.test.broadcast;

import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.nodes.Node;

/**
 * @author posilva
 *
 */
public class BroadcastNoRepetitionRoutingLayer extends RoutingLayer {

	private Node parent;
	public BroadcastNoRepetitionRoutingLayer() {
		super();
	}

	/**
	 * Stores the sender from which it first receives the message, and passes 
	 * the message.
	 */
	@Override
	public void receiveMessage(Object message ){
			if (parent == null){
				parent = getNode().getParentNode();				
                Application app = getNode().getApplication();
				if(app!=null)	app.receiveMessage(message);
			}            
	}    
	@Override
	public boolean sendMessage(Object message, Application app){
		if (application!=null) return false;
		this.application = app;
		return getNode().getMacLayer().sendMessage(message, this);
	}
	
	/**
	 * Sets the sent flag to true. 
	 */
	@Override
	public void sendMessageDone(){
		application.sendMessageDone();
		application=null;
	}
	
}
