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
//    Set<AttacksEntry> attacksList = new HashSet<AttacksEntry>();

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

    public boolean enableAttack(Class c) {
        boolean value = true;
        boolean result = false;
        if (c == null) {
            disableAttack(c);
        } else {
            for (AttacksEntry attacksEntry : attacksList) {
                if (attacksEntry.getClass().getName().equals(c.getName())) {
                    attacksEntry.setEnable(value);
                    result = true;
                } else {
                    attacksEntry.setEnable(!value);
                }
            }
        }
        return result;
    }

    public boolean disableAttack(Class c) {
        boolean value = false;
        boolean result = false;
        if (c == null) {
            for (AttacksEntry attacksEntry : attacksList) {
                attacksEntry.setEnable(value);
            }
            result = true;

        } else {
            for (AttacksEntry attacksEntry : attacksList) {
                if (attacksEntry.getClass().getName().equals(c.getName())) {
                    attacksEntry.setEnable(value);
                    result = true;
                } else {
                    attacksEntry.setEnable(!value);
                }
            }
        }
        return result;
    }
}
