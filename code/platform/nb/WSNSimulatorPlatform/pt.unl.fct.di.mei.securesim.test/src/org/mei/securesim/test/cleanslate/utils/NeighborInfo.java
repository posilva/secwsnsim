/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate.utils;

/**
 *
 * @author Pedro Marques da Silva
 */
public class NeighborInfo implements Comparable {

    long groupID;
    short size;
    boolean active;

    public NeighborInfo(long groupID, short size) {
        this.groupID = groupID;
        this.size = size;
    }

    public int compareTo(Object o) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == o) {
            return EQUAL;
        }

        NeighborInfo other = (NeighborInfo) o;

        if (other.size > this.size) {
            return BEFORE;
        }
        if (other.size < this.size) {
            return AFTER;
        }
        if (other.groupID > this.groupID) {
            return BEFORE;
        }
        if (other.groupID < this.groupID) {
            return AFTER;
        }



//        String thisID= new String(this.groupID);
//        String otherID= new String(other.groupID);
//
//        if (thisID.compareTo(otherID)<0 ) {
//            return BEFORE;
//        }
//        if (thisID.compareTo(otherID)>0 ) {
//            return AFTER;
//        }

        assert this.equals(other) : "compareTo inconsistent with equals.";
        return EQUAL;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(short groupID) {
        this.groupID = groupID;
    }

    public short getSize() {
        return size;
    }

    public void setSize(short size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "[ " + this.groupID + ",|"+size+"| ]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NeighborInfo){
            NeighborInfo n = (NeighborInfo) obj;
            return EqualsUtil.areEqual(groupID, n.groupID) &&
                   EqualsUtil.areEqual(size, n.size);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (int) (this.groupID ^ (this.groupID >>> 32));
        hash = 13 * hash + this.size;
        return hash;
    }

}
