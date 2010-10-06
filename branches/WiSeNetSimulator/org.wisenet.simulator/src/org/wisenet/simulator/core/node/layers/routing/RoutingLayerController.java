package org.wisenet.simulator.core.node.layers.routing;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.Parameterizable;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;
import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksEntry;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class RoutingLayerController extends PersistantObject implements Parameterizable {

    Set stableNodesSet = new HashSet();
    Hashtable<Short, Long> messageReceivedByTypeCounter = new Hashtable<Short, Long>();
    Hashtable<Short, Long> messageSentByTypeCounter = new Hashtable<Short, Long>();
    RoutingLayerParameters parameters = new RoutingLayerParameters();
    Set registeredAttacks = new HashSet();
    protected static boolean controllerUpdated = false;

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

    public ObjectParameters getParameters() {
        return parameters;
    }

    public void setParameters(ObjectParameters params) {
        this.parameters = (RoutingLayerParameters) params;
    }

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        parameters.saveToXML(configuration);
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        parameters.loadFromXML(configuration);

    }

    public void registerAttack(AttacksEntry entry) {
        registeredAttacks.add(entry);
    }

    public void unregisterAttack(AttacksEntry entry) {
        registeredAttacks.remove(entry);
    }

    public Set getRegisteredAttacks() {
        return registeredAttacks;
    }

    public void log(Exception ex) {
        System.err.println(ex.getMessage());
    }
}
