/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro Marques da Silva
 */
public class MergeProposalData extends BaseMessageData {

    public long targetGroup;
    public short sourceGroupSize;
    public short sourceNeighborsListSize = 0;
    public long[] sourceNeighborsListGroupID;
    public short[] sourceNeighborsListGroupSize;

    public MergeProposalData(byte[] payload) {
        super(payload);
        try {
            sourceGroupSize = badis.readShort();
            sourceNeighborsListSize = badis.readShort();
            if (sourceGroupSize > 0) {
                sourceNeighborsListGroupID = new long[sourceNeighborsListSize];
                sourceNeighborsListGroupSize = new short[sourceNeighborsListSize];
                for (int i = 0; i < sourceNeighborsListSize; i++) {
                    sourceNeighborsListGroupID[i] = badis.readLong();
                    sourceNeighborsListGroupSize[i] = badis.readShort();
                }
            }
            targetGroup = badis.readLong();
        } catch (IOException ex) {
            Logger.getLogger(MergeProposalData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
