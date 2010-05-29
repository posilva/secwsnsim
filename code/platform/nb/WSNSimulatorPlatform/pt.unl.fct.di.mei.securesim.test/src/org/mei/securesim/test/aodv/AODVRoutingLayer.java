/**
 * 
 */
package org.mei.securesim.test.aodv;

import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.core.nodes.Node;

/**
 * @author posilva
 *
 */
public class AODVRoutingLayer extends RoutingLayer {

    private Node parent;

    private RoutingTable routingTable;

    private long sequenceNumber;

    public AODVRoutingLayer() {
        super();
    }

    /**
     * Stores the sender from which it first receives the message, and passes
     * the message.
     */
    @Override
    public void receiveMessage(Object message) {
            Application app = getNode().getApplication();
            if (app != null) {
                app.receiveMessage(message);
            }
    }

    @Override
    public boolean sendMessage(Object message, Application app) {
        application=app;
        return getNode().getMacLayer().sendMessage(message, this);
    }

    /**
     * Sets the sent flag to true.
     */
    @Override
    public void sendMessageDone() {
        application.sendMessageDone();
        application = null;
    }

    @Override
    public void autostart() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
