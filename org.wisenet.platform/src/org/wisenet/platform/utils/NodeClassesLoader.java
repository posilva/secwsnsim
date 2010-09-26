/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.platform.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.simulator.core.node.layers.mac.MACLayer;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.node.Node;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class NodeClassesLoader {

    /**
     * Loading a Routing Layer class in runtime
     * @param routingLayer
     * @param node
     * @return
     */
    public static boolean loadRoutingLayerIntoNode(String routingLayer, Node node) {
        try {
            Class c = Class.forName(routingLayer);
            Object routingLayerInstance = c.newInstance();
            node.setRoutingLayer((RoutingLayer) routingLayerInstance);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(NodeClassesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Load a MAC Layer Class in runtime
     * @param macLayer
     * @param node
     * @return
     */
    public static boolean loadMACLayerIntoNode(String macLayer, Node node) {
        try {
            Class c = Class.forName(macLayer);
            Object macLayerInstance = c.newInstance();
            node.setMacLayer((MACLayer) macLayerInstance);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(NodeClassesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
