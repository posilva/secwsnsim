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
public class BroadcastMergeProposalRefuseData extends BaseMessageData {

    public long refuseGroupID;
    public int sequenceNumber;

    public BroadcastMergeProposalRefuseData(byte[] payload) {
        super(payload);
        try {
            refuseGroupID = badis.readShort();
            sequenceNumber = badis.readInt();
        } catch (IOException ex) {
            Logger.getLogger(BroadcastMergeProposalRefuseData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
