/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.test.insens.utils.INSENSConstants;
import org.mei.securesim.test.insens.utils.INSENSFunctions;
import org.mei.securesim.test.insens.utils.NbrInfo;
import org.mei.securesim.test.insens.utils.PathInfo;
import static org.mei.securesim.test.insens.utils.INSENSFunctions.cloneMAC;
/**
 *
 * @author POSilva
 */
public class MessagesFactory {

    /**
     * |------|------|-----|--------------------------|
     *   RREQ   IDbs   OWS   MAC(Kbs,RREQ||IDbs||OWS)
     * |------|------|-----|--------------------------|
     * @param origId
     * @param ows
     * @param macKey
     * @return
     */
    public static INSENSMsg createRouteRequestMessage(int origId, long ows, Key macKey) {
        RREQMsg message = new RREQMsg(null);
        message.setOrigin(origId);
        message.setType(INSENSConstants.MSG_RREQ);
        message.setId(origId);

        message.setOWS(ows);
        message.setPath(new Vector<Integer>());

        message.getPath().add(origId);

        byte[] data = message.toByteArray();

        byte[] macData = CryptoFunctions.createMAC(data, macKey.getEncoded());

        message.setMAC(macData);

        return message;
    }

    /**
     * |------|-----|-----|------------------------------|
     *   RREQ   IDx   OWS   MAC(Kx,RREQ||IDx||OWS||MACp)
     * |------|-----|-----|------------------------------|
     * @param origId
     *
     * @param ows
     * @param macKey
     * @param parentMac
     * @return
     */
    public static INSENSMsg createForwardRouteRequestMessage(RREQMsg reqMessage, int origId, Key macKey) {
        RREQMsg message = new RREQMsg(null);
        // can be cloned instead of  a manual copy of attributes
        message.setType(INSENSConstants.MSG_RREQ);

        message.setId(origId);

        message.setOWS(reqMessage.getOWS());

        message.setPath(new Vector<Integer>());

        for (Object object : reqMessage.getPath()) {
            int i = ((Integer)object).intValue();
            message.getPath().addElement(i);
        }

        message.getPath().addAll(reqMessage.getPath());
        message.getPath().add(origId);

        byte[] parentMac = cloneMAC(reqMessage.getMAC());

        byte[] data = message.toByteArray();

        byte[] buffer =  new byte[data.length+parentMac.length];

        System.arraycopy(data, 0, buffer,0, data.length);
        System.arraycopy(parentMac, 0, buffer,data.length, parentMac.length);

        byte[] macData = CryptoFunctions.createMAC(buffer, macKey.getEncoded());
        message.setMAC(macData);
        message.setOrigin(origId);
        return message;
    }
    /**
     * |------|-----|-----|------------------------------|
     *   FDBK   IDx   OWS   MAC(Kx,RREQ||IDx||OWS||MACp)
     * |------|-----|-----|------------------------------|
     *
     * @param origId
     * @param ows
     * @param macKey
     * @return
     */
    public static INSENSMsg createFeedbackMessage(long ows, Key macKey,NbrInfo nbrInfo, PathInfo pathInfo, byte[] macParent) {
        
        if (macParent==null ) throw  new IllegalStateException("MacParent Cannot be null");
        FDBKMsg message = new FDBKMsg(null);
        message.setType(INSENSConstants.MSG_FDBK);
        message.setOWS(ows);
        message.setPathInfo(pathInfo);
        message.setNbrInfo(nbrInfo);
        message.setMACRParent(cloneMAC(macParent));
        byte[] data = message.toByteArray();
        byte[] macData = CryptoFunctions.createMAC(data, macKey.getEncoded());

        message.setMAC(macData);
//        System.out.println("Creation of FDBK Message from " + pathInfo.id);
//       System.out.println("\t Sent MACF: "+ INSENSFunctions.toHex(macData));
//       System.out.println("\t Sent Payload: "+ INSENSFunctions.toHex(data));
//       System.out.println("\t Origin KEY: "+ INSENSFunctions.toHex(macKey.getEncoded()));
        return message;
    }

    public static INSENSMsg getInitialRouteRequestMessage(int id, long ows, Key keyM) {
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

//    public static INSENSMsg getRouteRequestMessage(int id, long ows, byte[] macP, Key keyM) {
//        byte[] mac = null;
//        try {
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            DataOutputStream dos = new DataOutputStream(baos);
//            dos.writeInt(INSENSConstants.MSG_RREQ);
//            dos.writeInt(id);
//            dos.writeLong(ows);
//            dos.flush();
//
//            ByteArrayOutputStream baosMac = new ByteArrayOutputStream();
//            DataOutputStream dosMac = new DataOutputStream(baos);
//            dosMac.writeInt(id);
//            dosMac.writeLong(ows);
//            dosMac.write(macP);
//            dosMac.flush();
//            byte[] data = baosMac.toByteArray();
//
//            mac = CryptoFunctions.createMAC(data, keyM.getEncoded());
//            dos.write(mac);
//            dos.flush();
//            RREQMsg m = new RREQMsg(baos.toByteArray());
//            m.setMAC(mac);
//            m.setOWS(ows);
//            m.setType(id);
//            m.setId(id);
//            return m;
//        } catch (IOException ex) {
//            Logger.getLogger(MessagesFactory.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
}
