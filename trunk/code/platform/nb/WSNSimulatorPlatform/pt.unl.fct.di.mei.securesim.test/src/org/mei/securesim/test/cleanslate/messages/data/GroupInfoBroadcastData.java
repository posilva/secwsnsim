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
public class GroupInfoBroadcastData extends BaseMessageData {

    public short sourceGroupSize;

    public GroupInfoBroadcastData(byte[] payload) {
        super(payload);
        try {
            sourceGroupSize = badis.readShort();
        } catch (IOException ex) {
            Logger.getLogger(GroupInfoBroadcastData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
