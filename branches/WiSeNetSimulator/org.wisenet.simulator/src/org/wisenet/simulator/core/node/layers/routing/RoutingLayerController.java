package org.wisenet.simulator.core.node.layers.routing;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class RoutingLayerController {

    RoutingLayerProperties properties = new RoutingLayerProperties();
    Set stableNodesSet = new HashSet();
    Hashtable<Short, Long> messageReceivedByTypeCounter = new Hashtable<Short, Long>();
    Hashtable<Short, Long> messageSentByTypeCounter = new Hashtable<Short, Long>();

    void registerAsStable(RoutingLayer nodeRL) {
        stableNodesSet.add(nodeRL);
    }

    void unregisterAsStable(RoutingLayer nodeRL) {
        stableNodesSet.remove(nodeRL);
    }

    public void addMessageReceivedCounter(short type) {
        Long counter = messageReceivedByTypeCounter.get(type);
        if (counter == null) {
            counter = 0L;
        }
        counter++;
        messageReceivedByTypeCounter.put(type, counter);
    }

    public void addMessageSentCounter(short type) {
        Long counter = messageSentByTypeCounter.get(type);
        if (counter == null) {
            counter = 0L;
        }
        counter++;
        messageSentByTypeCounter.put(type, counter);
    }

    public int getTotalStableNodes() {
        return stableNodesSet.size();
    }

    public long getTotalReceivedMessages() {
        Long total = 0L;
        for (Long value : messageReceivedByTypeCounter.values()) {
            total += value;
        }
        return total;
    }

    public long getTotalSentMessages() {
        Long total = 0L;
        for (Long value : messageSentByTypeCounter.values()) {
            total += value;
        }
        return total;
    }

    public void reset() {
        messageReceivedByTypeCounter.clear();
        messageSentByTypeCounter.clear();
        stableNodesSet.clear();
    }

    public RoutingLayerProperties getProperties() {
        return properties;
    }

    public void setProperties(RoutingLayerProperties properties) {
        this.properties = properties;
    }
}
