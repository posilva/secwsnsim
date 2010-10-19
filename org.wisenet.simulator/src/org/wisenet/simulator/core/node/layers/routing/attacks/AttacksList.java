/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.routing.attacks;

import java.util.LinkedList;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class AttacksList {

    RoutingLayer routingLayer;
    LinkedList<AttacksEntry> attacksList = new LinkedList<AttacksEntry>();

    /**
     *
     * @param routingLayer
     */
    public AttacksList(RoutingLayer routingLayer) {
        this.routingLayer = routingLayer;
    }

    /**
     *
     * @return
     */
    public LinkedList<AttacksEntry> getAttacksList() {
        return attacksList;
    }

    /**
     *
     * @return
     */
    public RoutingLayer getRoutingLayer() {
        return routingLayer;
    }

    /**
     *
     * @param routingLayer
     */
    public void setRoutingLayer(RoutingLayer routingLayer) {
        this.routingLayer = routingLayer;
    }

    /**
     *
     * @param entry
     */
    public void addEntry(AttacksEntry entry) {
        if (!attacksList.contains(entry)) {
            attacksList.add(entry);
        } else {
            throw new IllegalStateException("Entry already exists");
        }
    }

    /**
     *
     * @param entry
     */
    public void removeEntry(AttacksEntry entry) {
        attacksList.remove(entry);
    }

    /**
     *
     * @param newAttack
     */
    public void updateAttack(Object newAttack) {
        for (AttacksEntry attacksEntry : attacksList) {
            if (attacksEntry.getAttack().getClass().getName().equals(newAttack.getClass().getName())) {
                
                ((IRoutingAttack) newAttack).setRoutingLayer(getRoutingLayer());
                attacksEntry.setAttack((IRoutingAttack) newAttack);

            }
        }
    }
}
