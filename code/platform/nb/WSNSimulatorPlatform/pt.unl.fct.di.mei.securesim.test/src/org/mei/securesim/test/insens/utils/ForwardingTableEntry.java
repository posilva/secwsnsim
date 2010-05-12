/*
 *
 */
package org.mei.securesim.test.insens.utils;

/**
 *
 * @author CIAdmin
 */
public class ForwardingTableEntry implements Cloneable {

    protected int destination;
    protected int source;
    protected int immediateSender;

    public ForwardingTableEntry() {
    }

    public ForwardingTableEntry(int destination, int source, int immediateSender) {
        this.destination = destination;
        this.source = source;
        this.immediateSender = immediateSender;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getImmediateSender() {
        return immediateSender;
    }

    public void setImmediateSender(int immediateSender) {
        this.immediateSender = immediateSender;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(destination);
        sb.append(source);
        sb.append(immediateSender);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ForwardingTableEntry other = (ForwardingTableEntry) obj;
        if (this.destination != other.destination) {
            return false;
        }
        if (this.source != other.source) {
            return false;
        }
        if (this.immediateSender != other.immediateSender) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
