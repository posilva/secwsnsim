package org.mei.securesim.core.layers.routing;

import org.mei.securesim.core.application.Application;
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
    public void init(){
        
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
    
    public abstract void receiveMessage(Object message);

    public abstract void sendMessageDone();

    public abstract boolean sendMessage(Object message, Application app);

    public abstract void autostart();



    
}
