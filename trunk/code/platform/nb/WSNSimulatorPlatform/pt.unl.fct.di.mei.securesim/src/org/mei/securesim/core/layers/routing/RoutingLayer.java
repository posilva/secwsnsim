package org.mei.securesim.core.layers.routing;

import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.layers.Layer;

public abstract class RoutingLayer extends Layer {

    protected Application application = null;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public RoutingLayer() {
        super();

    }

    public abstract void receiveMessage(Object message);

    public abstract void sendMessageDone();

    public abstract boolean sendMessage(Object message, Application app);

    
}
