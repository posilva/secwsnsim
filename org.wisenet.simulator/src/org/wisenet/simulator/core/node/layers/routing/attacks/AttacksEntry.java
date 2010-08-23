/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.routing.attacks;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class AttacksEntry {

    public boolean enable = false;
    public String label = "attack";
    public IRoutingAttack attack;

    public AttacksEntry(boolean enable, String label, IRoutingAttack attack) {
        this.enable = enable;
        this.label = label;
        this.attack = attack;
    }

    public IRoutingAttack getAttack() {
        return attack;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Attack " + attack.getClass().getName() + " - " + getLabel() + "[" + (enable ? "enable" : "disable") + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AttacksEntry other = (AttacksEntry) obj;
        if (this.attack.getClass() != other.attack.getClass() && (this.attack == null || !this.attack.getClass().equals(other.attack.getClass()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.attack.getClass() != null ? this.attack.getClass().hashCode() : 0);
        return hash;
    }
}
