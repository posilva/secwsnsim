/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.cleanslate.utils;

/**
 *
 * @author Pedro Marques da Silva
 */
public class MergeTableEntry {
    long otherGroupId;
    int otherGroupSize;

    public MergeTableEntry(long otherGroupId, int otherGroupSize) {
        this.otherGroupId = otherGroupId;
        this.otherGroupSize = otherGroupSize;
    }

    public long getOtherGroupId() {
        return otherGroupId;
    }

    public void setOtherGroupId(long otherGroupId) {
        this.otherGroupId = otherGroupId;
    }

    public int getOtherGroupSize() {
        return otherGroupSize;
    }

    public void setOtherGroupSize(int otherGroupSize) {
        this.otherGroupSize = otherGroupSize;
    }
    
}
