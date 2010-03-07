package org.mei.securesim.test.broadcast;

import org.mei.securesim.core.Application;

/**
 * This extension of the {@link Application} baseclass does everything we expect
 * from the broadcast application, simply forwards the message once, and that is
 * it.
 */
public class BroadcastApplication extends Application {

	/**
	 * @param node
	 *            the Node on which this application runs.
	 */
	public BroadcastApplication() {
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

	private BroadcastNode getBroadcastNode() {
		return (BroadcastNode) getNode();

	}

	public static BroadcastApplication createInstance() {
		return new BroadcastApplication();
	}

    @Override
    public void generateEvent() {
        sendMessage("1");
    }

}
