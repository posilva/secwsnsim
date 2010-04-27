/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.test.insens.utils.INSENSConstants;

/**
 *
 * @author POSilva
 */
public class MessagesFactory {
    
    public static INSENSMsg getInitialRouteRequestMessage(int id, long ows, Key keyM){
        byte[] mac = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(INSENSConstants.MSG_RREQ);
            dos.writeInt(id);
            dos.writeLong(ows);
            dos.flush();
            byte[] data = baos.toByteArray();
            mac = CryptoFunctions.createMAC(data, keyM.getEncoded());
            dos.write(mac);
            dos.flush();
            return new RREQMsg(baos.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(MessagesFactory.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static INSENSMsg getRouteRequestMessage(int id, long ows, byte[] macP, Key keyM){
        byte[] mac = null;
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(INSENSConstants.MSG_RREQ);
            dos.writeInt(id);
            dos.writeLong(ows);
            dos.flush();

            ByteArrayOutputStream baosMac = new ByteArrayOutputStream();
            DataOutputStream dosMac = new DataOutputStream(baos);
            dosMac.writeInt(id);
            dosMac.writeLong(ows);
            dosMac.write(macP);
            dosMac.flush();
            byte[] data = baosMac.toByteArray();

            mac = CryptoFunctions.createMAC(data, keyM.getEncoded());
            dos.write(mac);
            dos.flush();
            RREQMsg m = new RREQMsg(baos.toByteArray());
            m.setMAC(mac);
            m.setOWS(ows);
            m.setType(id);
            m.setId(id);
            return m;
        } catch (IOException ex) {
            Logger.getLogger(MessagesFactory.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
