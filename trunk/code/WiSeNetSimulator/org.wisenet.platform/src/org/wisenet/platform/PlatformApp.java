/*
 * PlatformApp.java
 */

package org.wisenet.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.wisenet.platform.utils.ClassFinder;

/**
 * The main class of the application.
 */
public class PlatformApp extends SingleFrameApplication {

    public static List routingLayerClasses=new ArrayList();
    public static List macLayerClasses=new ArrayList();
    public static List applicationLayerClasses=new ArrayList();
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new PlatformView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
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
        launch(PlatformApp.class, args);
    }



    protected static List findClasses(String classes){
        List listRL= new ArrayList();
        ClassFinder finder = new ClassFinder();
        Vector<Class<?>> v = finder.findSubclasses (classes);
		List<Throwable> 	errors = finder.getErrors ();
		if (v != null && v.size () > 0)
		{
			for (Class<?> cls : v)
			{
                            listRL.add(cls+"#"+cls.getSimpleName());
			}
		}
		else
		{
			
		}
        return listRL;
    }


    public static void loadClasses(){
//        routingLayerClasses = findClasses("org.wisenet.core.layers.routing.RoutingLayer");
//        macLayerClasses = findClasses("org.wisenet.core.layers.mac.MACLayer");
//        applicationLayerClasses = findClasses("org.wisenet.core.application.Application");
    }
}
