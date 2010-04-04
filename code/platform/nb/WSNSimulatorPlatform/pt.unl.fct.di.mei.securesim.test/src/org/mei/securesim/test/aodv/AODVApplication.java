package org.mei.securesim.test.aodv;

import org.mei.securesim.core.application.Application;

/**
 * This extension of the {@link Application} baseclass does everything we expect
 * from the broadcast application, simply forwards the message once, and that is
 * it.
 */
public class AODVApplication extends Application {

	/**
	 * @param node
	 *            the Node on which this application runs.
	 */
	public AODVApplication() {
		super();
	}

	/**
	 * Stores the sender from which it first receives the message, and passes
	 * the message.
	 */
    @Override
	public void receiveMessage(Object message) {
		if (getBroadcastNode().getParent() == null) {
			getBroadcastNode().setParent(getNode().getParentNode());

			sendMessage(message);
		}
	}

	/**
	 * Sets the sent flag to true.
	 */
    @Override
	public void sendMessageDone() {
		getBroadcastNode().sentMenssage(true);
	}

	private AODVNode getBroadcastNode() {
		return (AODVNode) getNode();

	}

	public static AODVApplication createInstance() {
		return new AODVApplication();
	}

    @Override
    public void run() {
        sendMessage("1");
    }

}
