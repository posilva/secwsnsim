package org.wisenet.simulator.core.node.layers.routing;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.Parameterizable;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
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
    /**
     *
     */
    protected static boolean controllerUpdated = false;
    /**
     *
     */
    protected boolean testing = false;
    private AbstractTest activeTest;
    private long attackedNodes;

    void registerAsStable(RoutingLayer nodeRL) {
        stableNodesSet.add(nodeRL);
    }

    void unregisterAsStable(RoutingLayer nodeRL) {
        stableNodesSet.remove(nodeRL);
    }

    /**
     *
     * @param type
     */
    public void addMessageReceivedCounter(short type) {
        Long counter = messageReceivedByTypeCounter.get(type);
        if (counter == null) {
            counter = 0L;
        }
        counter++;
        messageReceivedByTypeCounter.put(type, counter);
    }

    /**
     *
     * @param type
     */
    public void addMessageSentCounter(short type) {
        Long counter = messageSentByTypeCounter.get(type);
        if (counter == null) {
            counter = 0L;
        }
        counter++;
        messageSentByTypeCounter.put(type, counter);
    }

    /**
     *
     * @return
     */
    public int getTotalStableNodes() {
        return stableNodesSet.size();
    }

    /**
     *
     * @return
     */
    public long getTotalReceivedMessages() {
        Long total = 0L;
        for (Long value : messageReceivedByTypeCounter.values()) {
            total += value;
        }
        return total;
    }

    /**
     *
     * @return
     */
    public long getTotalSentMessages() {
        Long total = 0L;
        for (Long value : messageSentByTypeCounter.values()) {
            total += value;
        }
        return total;
    }

    /**
     *
     */
    public void reset() {
        messageReceivedByTypeCounter.clear();
        messageSentByTypeCounter.clear();
        attackedNodes = 0;
        stableNodesSet.clear();
    }

    /**
     *
     * @return
     */
    public ObjectParameters getParameters() {
        return parameters;
    }

    /**
     *
     * @param params
     */
    public void setParameters(ObjectParameters params) {
        this.parameters = (RoutingLayerParameters) params;
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        parameters.saveToXML(configuration);
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        parameters.loadFromXML(configuration);

    }

    /**
     *
     * @param entry
     */
    public void registerAttack(AttacksEntry entry) {
        registeredAttacks.add(entry);
    }

    /**
     *
     * @param entry
     */
    public void unregisterAttack(AttacksEntry entry) {
        registeredAttacks.remove(entry);
    }

    /**
     *
     * @return
     */
    public Set getRegisteredAttacks() {
        return registeredAttacks;
    }

    /**
     *
     * @param ex
     */
    public void log(Exception ex) {
        System.err.println(ex.getMessage());
    }

    /**
     *
     * @return
     */
    public boolean isTesting() {
        return this.activeTest != null;
    }

    AbstractTest getActiveTest() {
        return activeTest;
    }

    /**
     *
     * @param activeTest
     */
    public void setActiveTest(AbstractTest activeTest) {
        this.activeTest = activeTest;
    }

    public void incrementAttackedMessages() {
        attackedNodes++;
    }

    public long getTotalAttackedMessages() {
        return attackedNodes;
    }
}
