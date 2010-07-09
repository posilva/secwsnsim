/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.protocols.common.ByteArrayDataOutputStream;

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

   public byte[] toByteArray() {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(type);
            bados.writeShort(sourceId);
            bados.writeLong(sourceGroupId);
            bados.writeShort(forwardId);
            bados.writeLong(forwardGroupId);
            bados.writeInt(sequenceNumber);
            bados.writeShort(sourceGroupSize);
            bados.writeShort(sourceNeighborsListSize);
            for (int i = 0; i < sourceNeighborsListSize; i++) {
                bados.writeLong(sourceNeighborsListGroupID[i]);
                bados.writeShort(sourceNeighborsListGroupSize[i]);

            }
            bados.writeLong(targetGroup);
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(MergeProposalData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
