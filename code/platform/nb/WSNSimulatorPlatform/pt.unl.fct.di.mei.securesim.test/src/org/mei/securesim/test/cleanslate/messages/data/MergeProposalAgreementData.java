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
public class MergeProposalAgreementData extends BaseMessageData{
    public int mergeProposalPayloadSize;
    public byte [] mergeProposalPayload;

    public MergeProposalAgreementData(byte[] payload) {
        super(payload);
        try {
            mergeProposalPayloadSize = badis.readInt();
            mergeProposalPayload = new byte[mergeProposalPayloadSize];
            badis.read(mergeProposalPayload);
        } catch (IOException ex) {
            Logger.getLogger(MergeProposalAgreementData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

}
