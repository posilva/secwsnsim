/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate.messages.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.test.cleanslate.CleanSlateRoutingLayer;

/**
 *
 * @author Pedro Marques da Silva
 */
public class HELLOMsgData extends BaseMessageData {

    byte[] signature;

    public HELLOMsgData(byte[] payload) {
        super(payload);
        try {
            signature = new byte[CryptoFunctions.MAC_SIZE];
            badis.read(signature);
        } catch (IOException ex) {
            Logger.getLogger(HELLOMsgData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
