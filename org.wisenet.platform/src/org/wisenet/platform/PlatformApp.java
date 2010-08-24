/*
 * PlatformApp.java
 */
package org.wisenet.platform;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.wisenet.simulator.utilities.Utilities;

/**
 * The main class of the application.
 */
public class PlatformApp extends SingleFrameApplication {

    public static List routingLayerClasses = new ArrayList();
    public static List macLayerClasses = new ArrayList();
    public static List applicationLayerClasses = new ArrayList();

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        try {
            show(new PlatformView(this));
        } catch (Exception e) {
            Utilities.handleException(e);
        }

    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of PlatformApp
     */
    public static PlatformApp getApplication() {
        return Application.getInstance(PlatformApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        try {
            PlatformConfiguration.getInstance().init();
            launch(PlatformApp.class, args);
        } catch (Exception e) {
            Utilities.handleException(e);
        }

    }
}
