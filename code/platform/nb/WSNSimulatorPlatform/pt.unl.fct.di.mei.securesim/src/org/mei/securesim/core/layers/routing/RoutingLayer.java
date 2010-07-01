package org.mei.securesim.core.layers.routing;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.instruments.coverage.CoverageController;
import org.mei.securesim.components.instruments.latency.LatencyController;
import org.mei.securesim.components.instruments.ReliabilityController;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.BaseMessage;
import org.mei.securesim.core.layers.Layer;

public abstract class RoutingLayer extends Layer {

    protected Application application = null;
    protected boolean autostarted;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public RoutingLayer() {
        super();

    }

    public void init() {
    }

    /**
     * if a routing layer is marked as autostart when the simulator start the
     * routing layer begins to operate
     * @return
     */
    public boolean isAutostarted() {
        return autostarted;
    }

    public void setAutostarted(boolean autostarted) {
        this.autostarted = autostarted;
    }

    /**
     * This method enables the instrumentation to perform the
     * controllers notification
     * @param object
     */
    public void receiveMessageHandler(Object message) {
        try {
            BaseMessage m = (BaseMessage) ((BaseMessage) message).clone();
            instrumentationNotifyMessageReception(m);
            receiveMessage(m);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(RoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method enables the instrumentation to perform the
     * controllers notification
     * @param object
     */
    public boolean sendMessageHandler(Object message, Application app) {
        instrumentationNotifyMessageSent(message);
        return sendMessage(message, app);
    }

    protected void instrumentationNotifyMessageReception(Object message) {
        CoverageController.getInstance().notifyMessageReception(message, getNode());
        LatencyController.getInstance().notifyMessageReception(message, getNode());
        ReliabilityController.getInstance().notifyMessageReception(message, getNode());
    }

    private void instrumentationNotifyMessageSent(Object message) {
        CoverageController.getInstance().notifyMessageSent(message, getNode());
        LatencyController.getInstance().notifyMessageSent(message, getNode());
        ReliabilityController.getInstance().notifyMessageSent(message, getNode());
    }

    public abstract void receiveMessage(Object message);

    public abstract void sendMessageDone();

    public abstract boolean sendMessage(Object message, Application app);

    public abstract void autostart();

    public abstract void routeMessage(Object message);
}
