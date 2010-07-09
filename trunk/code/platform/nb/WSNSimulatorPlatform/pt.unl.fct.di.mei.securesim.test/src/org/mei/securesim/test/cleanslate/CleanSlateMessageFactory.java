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
import org.mei.securesim.protocols.common.ByteArrayDataOutputStream;

/**
 *
 * @author Pedro Marques da Silva
 */
public class CleanSlateMessageFactory {

    public static byte[] createHELLOMessagePayload(CleanSlateRoutingLayer routingLayer) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_HELLO);
            bados.writeShort(routingLayer.getNode().getId()); // message source
            bados.writeLong(routingLayer.myGroupId); // message source group
            bados.writeShort(routingLayer.getNode().getId()); // last node
            bados.writeLong(routingLayer.myGroupId); // last node group
            bados.writeInt(0); // sequence number
            bados.write(CleanSlateFunctions.signData(CleanSlateFunctions.shortToByteArray(routingLayer.getNode().getId()), routingLayer.getCSNode().getNAKey()));
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateMessageFactory.class.getName()).log(Level.SEVERE, null, ex);
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
            bados.writeShort(routingLayer.getNode().getId()); // last node ID
            bados.writeLong(routingLayer.myGroupId); // last group ID
            bados.writeInt(0); // sequence number
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

    public static byte[] createPostMergeMessagePayload(CleanSlateRoutingLayer routingLayer, long otherGroupId, long myOldID) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_POST_MERGE);
            bados.writeShort(routingLayer.getNode().getId()); // source node ID
            bados.writeLong(routingLayer.myGroupId); // source group ID
            bados.writeShort(routingLayer.getNode().getId()); // last node ID
            bados.writeLong(routingLayer.myGroupId); // last group ID
            bados.writeInt(0); // sequence number
            
            bados.writeShort(routingLayer.myGroupSize); // last group Size
            bados.writeShort(routingLayer.listNeighboringGroups.size()); // size of neighbors list

            for (Iterator it = routingLayer.listNeighboringGroups.values().iterator(); it.hasNext();) { // acrescentei o values
                NeighborInfo ni = (NeighborInfo) it.next();
                bados.writeLong(ni.getGroupID());
                bados.writeShort(ni.getSize());
            } // write list of neighbors

            bados.writeLong(otherGroupId); // com quem fiz o merge
            bados.writeLong(myOldID); // my old groupid
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Creates a neighbor group announce message payload
     * @param routingLayer
     * @param sequenceNumber
     * @return
     */
    static byte[] createNeighborGroupAnnounceMessagePayload(CleanSlateRoutingLayer routingLayer,  int seqNr,short srcNode, long srcGroupID) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_NEIGHBOR_GROUP_ANNOUNCE);
            bados.writeShort(srcNode); // source node ID
            bados.writeLong(srcGroupID); // source group ID
            bados.writeShort(routingLayer.getNode().getId()); // last node ID
            bados.writeLong(routingLayer.myGroupId); // last group ID
            bados.writeInt(seqNr);
            bados.writeShort(routingLayer.myGroupSize); // source group Size
            bados.writeShort(routingLayer.listNeighboringGroups.size()); // size of neighbors list
            for (Iterator it = routingLayer.listNeighboringGroups.values().iterator(); it.hasNext();) { // acrescentei o values
                NeighborInfo ni = (NeighborInfo) it.next();
                bados.writeLong(ni.getGroupID());
                bados.writeShort(ni.getSize());
            } // write list of neighbors
            
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    static byte[] createMergeProposalRefuseMessagePayload(CleanSlateRoutingLayer routingLayer, long refusedGroupId, int seqNr, short srcNode, long srcGroupID) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_MERGE_PROPOSAL_REFUSE);
            bados.writeShort(srcNode); // source node ID
            bados.writeLong(srcGroupID); // source group ID
            bados.writeShort(routingLayer.getNode().getId()); // last node ID
            bados.writeLong(routingLayer.myGroupId); // last group ID
            bados.writeInt(seqNr);
            bados.writeLong(refusedGroupId); // the group that refuses the merge
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Create a Merge Proposal Agreement message payload
     * This message intends to inform the group that a edge node agrees to merge
     * with other group edge node, so every nodes in the group must start merge
     * process
     * @param routingLayer
     * @param messageData
     * @param sequenceNumber
     * @return
     */
    static byte[] createMergeProposalAgreementMessagePayload(CleanSlateRoutingLayer routingLayer, MergeProposalData messageData,int seqNr, short srcNodeId, long srcGroupId) {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeByte(CleanSlateConstants.MSG_MERGE_PROPOSAL_AGREEMENT);
            bados.writeShort(srcNodeId); // source node ID
            bados.writeLong(srcGroupId); // source group ID
            bados.writeShort(routingLayer.getNode().getId()); // last node ID
            bados.writeLong(routingLayer.myGroupId); // last group ID
            bados.writeInt(seqNr);

            byte[] payload = messageData.toByteArray();
            bados.writeInt(payload.length); // size of merge proposal message
            bados.write(payload); // the merge proposal message

            bados.writeInt(seqNr);
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
