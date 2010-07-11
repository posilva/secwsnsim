package org.mei.securesim.core.layers.routing;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.instruments.CoverageInstrument;
import org.mei.securesim.components.instruments.IInstrumentHandler;
import org.mei.securesim.components.instruments.IInstrumentMessage;
import org.mei.securesim.components.instruments.coverage.CoverageController;
import org.mei.securesim.components.instruments.latency.LatencyController;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.Message;
import org.mei.securesim.core.layers.Layer;

public abstract class RoutingLayer extends Layer {

    protected Application application = null;
    protected boolean autostarted;
    protected boolean stable = false;
    protected RoutingLayerController routingController;

    public boolean isStable() {
        return stable;
    }

    public RoutingLayerController getRoutingController() {
        return routingController;
    }

    public void setRoutingController(RoutingLayerController routingController) {
        this.routingController = routingController;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
        if (routingController != null) {
            if (stable) {
                routingController.registerAsStable(this);
            } else {
                routingController.unregisterAsStable(this);
            }
        }
    }

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
     * if a routing layer is marked as setup when the simulator start the
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
            Message m = (Message) ((Message) message).clone();
            instrumentationNotifyMessageReception(
                    m);
            receiveMessage(
                    m);



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


    }

    private void instrumentationNotifyMessageSent(Object message) {
        CoverageController.getInstance().notifyMessageSent(message, getNode());
        LatencyController.getInstance().notifyMessageSent(message, getNode());


    }

    public void routeMessage(Object message) {

        onRouteMessage(message);
    }

    public void receiveMessage(Object message) {
        if (this instanceof IInstrumentHandler) {
            if (message instanceof IInstrumentMessage) {
                CoverageInstrument.getInstance().notifyMessageReceived((IInstrumentMessage) message, (IInstrumentHandler) this);
            }
        }

        onReceiveMessage(message);
    }

    public boolean sendMessage(Object message, Application app) {
        if (this instanceof IInstrumentHandler) {
            if (message instanceof IInstrumentMessage) {
                CoverageInstrument.getInstance().notifyMessageSent((IInstrumentMessage) message, (IInstrumentHandler) this);
            }
        }
        boolean result = onSendMessage(message, app);
        return result;
    }

    protected abstract void onReceiveMessage(Object message);

    protected abstract boolean onSendMessage(Object message, Application app);

    public abstract void setup();

    protected abstract void onRouteMessage(Object message);

    public abstract void sendMessageDone();

    public static void main(String[] args) {
        Short s1 = (short) 1;
        Short s2 = (short) 1;
        Set set = new HashSet();
        set.add(s1);
        if (set.contains(s2)) {
            System.out.println("são ");
        }

    }
}
