package pt.unl.fct.di.mei.securesim.engine.layers;

import pt.unl.fct.di.mei.securesim.engine.Application;

public abstract class RoutingLayer extends Layer {
	protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

	protected Application senderApplication;

	public RoutingLayer() {
		super();

	}

	public abstract void receiveMessage(Object message, Application application);

	public abstract void sendMessageDone();

	public abstract boolean sendMessage(Object message, Application app);

	public Application getApplication() {
		// TODO Auto-generated method stub
		return senderApplication;
	}

	public void addSendMessageDoneListener(SendMessageDoneListener listener) {
		listenerList.add(SendMessageDoneListener.class, listener);
	}
	public void removeSendMessageDoneListener(SendMessageDoneListener listener) {
        listenerList.remove(SendMessageDoneListener.class, listener);
    }
	
	protected void fireOnMessageDoneEvent(MessageEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==SendMessageDoneListener.class) {
                ((SendMessageDoneListener)listeners[i+1]).onMessageDone(evt);
            }
        }
    }
}
