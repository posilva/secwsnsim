/**
 * 
 */
package pt.unl.fct.di.mei.securesim.test.pingpong;

import pt.unl.fct.di.mei.securesim.engine.Application;
import pt.unl.fct.di.mei.securesim.engine.layers.RoutingLayer;
import pt.unl.fct.di.mei.securesim.engine.nodes.Node;

/**
 * @author posilva
 *
 */
public class PingPongRoutingLayer extends RoutingLayer {

	private Node parent;
	public PingPongRoutingLayer() {
		super();
	}

	/**
	 * Stores the sender from which it first receives the message, and passes 
	 * the message.
	 */
	@Override
	public void receiveMessage(Object message, Application sendApplication ){
			if (parent == null){
				parent = getNode().getParentNode();
				//Application app = getNode().getApplication(sendApplication.getClass());
                Application app = getNode().getApplication();
				if(app!=null)	app.receiveMessage(message);
			}            
	}    
	@Override
	public boolean sendMessage(Object message, Application app){
		if (application!=null) return false;
		this.application = app;
		return getNode().sendMessage(message, this);
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
