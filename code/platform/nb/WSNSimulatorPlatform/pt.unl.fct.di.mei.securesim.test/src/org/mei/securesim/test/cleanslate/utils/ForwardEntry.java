/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate.utils;

/**
 *
 * @author Pedro Marques da Silva
 */
public class ForwardEntry {

    short sourceNodeId;
    int messageNumber;
    byte messageType;

    public ForwardEntry(short sourceNodeId, int messageNumber, byte messageType) {
        this.sourceNodeId = sourceNodeId;
        this.messageNumber = messageNumber;
        this.messageType = messageType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ForwardEntry){
            ForwardEntry other = (ForwardEntry) obj;
            return EqualsUtil.areEqual(sourceNodeId, other.sourceNodeId) &&
                    EqualsUtil.areEqual(messageNumber, other.messageNumber) &&
                    EqualsUtil.areEqual(messageType, other.messageType) ;
        }else{return false;}

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.sourceNodeId;
        hash = 71 * hash + this.messageNumber;
        hash = 71 * hash + this.messageType;
        return hash;
    }


}
