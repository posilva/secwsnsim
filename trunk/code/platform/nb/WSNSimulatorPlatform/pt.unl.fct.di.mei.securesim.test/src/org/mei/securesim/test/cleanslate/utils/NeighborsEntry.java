/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.cleanslate.utils;

/**
 *
 * @author Pedro Marques da Silva
 */
public class NeighborsEntry {
    short nodeID;
    short groupID;

    public NeighborsEntry(short nodeID, short groupID) {
        this.nodeID = nodeID;
        this.groupID = groupID;
    }

    public short getGroupID() {
        return groupID;
    }

    public void setGroupID(short groupID) {
        this.groupID = groupID;
    }

    public short getNodeID() {
        return nodeID;
    }

    public void setNodeID(short nodeID) {
        this.nodeID = nodeID;
    }
    
}
