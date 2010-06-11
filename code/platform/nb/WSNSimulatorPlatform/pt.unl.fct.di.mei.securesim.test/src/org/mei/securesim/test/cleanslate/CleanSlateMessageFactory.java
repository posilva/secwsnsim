/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.test.cleanslate.messages.data.MergeProposalData;
import org.mei.securesim.test.cleanslate.utils.NeighborInfo;
import org.mei.securesim.test.common.ByteArrayDataOutputStream;

/**
 *
 * @author Pedro Marques da Silva
 */
public class CleanSlateMessageFactory {

    public static byte[] createHELLOMessagePayload(CleanSlateRoutingLayer routingLayer) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_HELLO);
            bados.writeShort(routingLayer.getNode().getId()); // ID do vizinho
            bados.writeLong(routingLayer.myGroupId); // grupo a q pertence
            bados.write(CleanSlateFunctions.signData(CleanSlateFunctions.shortToByteArray(routingLayer.getNode().getId()), routingLayer.getCSNode().getNAKey()));
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateMessageFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static  byte[] createGroupInfoBroadcastMessagePayload(CleanSlateRoutingLayer routingLayer, long gid, short size, byte type) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(type);
            bados.writeShort(routingLayer.getNode().getId()); // source node ID
            bados.writeLong(gid); // source group ID
            bados.writeShort(size); // source group Size
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static byte[] createBroadcastMergeRefuseMessageToGroup(CleanSlateRoutingLayer routingLayer, long sourceGroupID, byte type, int seqNr) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(type);
            bados.writeShort(routingLayer.getNode().getId()); // source node ID
            bados.writeLong(routingLayer.myGroupId); // source group ID
            bados.writeLong(sourceGroupID); // group that refuses the merge
            bados.writeInt(seqNr); // numero sequencia
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
       /**
     *
     * @param gid
     * @param type
     * @return
     */
    public static byte[] createMergeProposalMessagePayload(CleanSlateRoutingLayer routingLayer, long gid, byte type) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(type);
            bados.writeShort(routingLayer.getNode().getId()); // source node ID
            bados.writeLong(routingLayer.myGroupId); // source group ID
            bados.writeShort(routingLayer.myGroupSize); // source group Size
            bados.writeShort(routingLayer.listNeighboringGroups.size()); // size of neighbors list

            for (Iterator it = routingLayer.listNeighboringGroups.values().iterator(); it.hasNext();) { // acrescentei o values
                NeighborInfo ni = (NeighborInfo) it.next();
                bados.writeLong(ni.getGroupID());
                bados.writeShort(ni.getSize());
            } // write list of neighbors

            bados.writeLong(gid); // update group ID
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static byte[] createPostMergeMessagePayload(CleanSlateRoutingLayer routingLayer, long gid) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_POST_MERGE);
            bados.writeShort(routingLayer.getNode().getId()); // source node ID
            bados.writeLong(routingLayer.myGroupId); // source group ID
            //-----------------------------
            bados.writeShort(routingLayer.myGroupSize); // source group Size
            bados.writeShort(routingLayer.listNeighboringGroups.size()); // size of neighbors list

            for (Iterator it = routingLayer.listNeighboringGroups.values().iterator(); it.hasNext();) { // acrescentei o values
                NeighborInfo ni = (NeighborInfo) it.next();
                bados.writeLong(ni.getGroupID());
                bados.writeShort(ni.getSize());
            } // write list of neighbors

            bados.writeLong(gid); // com quem fiz o merge
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static byte[] createGroupMergeMessagePayload(CleanSlateRoutingLayer routingLayer, MergeProposalData mergeProposalData) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_GROUP_MERGE);
            bados.writeShort(routingLayer.getNode().getId()); // source node ID
            bados.writeLong(routingLayer.myGroupId); // source group ID
            bados.writeInt(mergeProposalData.payloadCopy.length); // merge proposal info Payload size
            bados.write(mergeProposalData.payloadCopy); // merge proposal info
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
