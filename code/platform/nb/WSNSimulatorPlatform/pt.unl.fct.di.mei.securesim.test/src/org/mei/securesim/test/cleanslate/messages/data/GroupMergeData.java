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
public class GroupMergeData extends BaseMessageData {

    public byte[] mergeProposalData;

    public GroupMergeData(byte[] payload) {
        super(payload);
        try {
            int size= badis.readInt();
            mergeProposalData=new byte[size];
            badis.readFully(mergeProposalData);
        } catch (IOException ex) {
            Logger.getLogger(GroupMergeData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
