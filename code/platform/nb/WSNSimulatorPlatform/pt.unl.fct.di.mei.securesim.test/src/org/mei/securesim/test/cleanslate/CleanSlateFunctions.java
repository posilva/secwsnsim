/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.core.engine.DefaultMessage;
import org.mei.securesim.test.cleanslate.messages.CleanSlateMsg;
import org.mei.securesim.test.cleanslate.utils.NeighborInfo;
import org.mei.securesim.test.common.ByteArrayDataOutputStream;
import org.mei.securesim.test.common.GeneralHashFunctions;

/**
 *
 * @author CIAdmin
 */
public class CleanSlateFunctions {


    public static byte[] shortToByteArray(short x) {
        byte[] initGroup = new byte[2];
        initGroup[0] = (byte) (x & 0xff);
        initGroup[1] = (byte) ((x >> 8) & 0xff);
        return initGroup;
    }
    public static byte[] groupToByteArray(long x) throws IOException {
        ByteArrayDataOutputStream bados =new ByteArrayDataOutputStream();
        bados.writeLong(x);
        return bados.toByteArray();
    }

    static byte[] signData(byte[] data, byte[] key) {
        return CryptoFunctions.createMAC(data, key);
    }

    static byte getMessageTypeFromPayload(byte[] payload) {
        if (payload == null) {
            return CleanSlateConstants.INVALID_TYPE;
        }
        return payload[0];
    }

    static boolean verifySignature(CleanSlateMsg msg) {
        return true;
    }

    static void printNeighborInfo(Collection<Long> listNeighbors, String info) {
        if (listNeighbors == null) {
            System.out.println(info+ " - [NULL]");
            return;
        }
        if (listNeighbors.size() == 0) {
            System.out.println(info+ " - []");
            return;
        }
        System.out.print(info+ " - [ ");
        for (Iterator it = listNeighbors.iterator(); it.hasNext();) {
            long gid = (Long) it.next();
            System.out.print(gid+ " ");
        }
        System.out.println("]");

    }
    /**
     * Calculates a Hash of the group Id based on merge info 
     * @return
     */
    public static long groupIdHash(long gid, short size, long other_gid,short other_size ){
        String hash_data = gid+":"+size+":"+other_gid+":"+other_size;
        return GeneralHashFunctions.JSHash(hash_data);
    }

    static void printNeighborsGroupInfo(Collection<NeighborInfo> listNeighbors, String info) {

         if (listNeighbors == null) {
            System.out.println(info+ " - Neighbors Groups [NULL]");
            return;
        }
        if (listNeighbors.size() == 0) {
            System.out.println(info+ " - Neighbors Groups []");
            return;
        }
        System.out.print(info+ " - Neighbors Groups [ ");
        for (Iterator it = listNeighbors.iterator(); it.hasNext();) {
            NeighborInfo gid = (NeighborInfo) it.next();
            System.out.print(gid.toString()+ " ");
        }
        System.out.println("]");
    }

    public static byte[] cloneMessage(Object message) {
        try {
            DefaultMessage m = (DefaultMessage) message;
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.write(m.getPayload());
            bados.flush();
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(CleanSlateFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
