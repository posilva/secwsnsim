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
public class PostMergeData extends MergeProposalData {
    public long oldGroupId  ;
    public PostMergeData(byte[] payload) {
        super(payload);
        try {
            oldGroupId = badis.readLong();
        } catch (IOException ex) {
            Logger.getLogger(PostMergeData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
