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
public class MergeProposalRefuseData extends BaseMessageData{
    public long refusedGroupId;
    public MergeProposalRefuseData(byte[] payload) {
        super(payload);
        try {
            refusedGroupId = badis.readLong();
        } catch (IOException ex) {
            Logger.getLogger(MergeProposalRefuseData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
