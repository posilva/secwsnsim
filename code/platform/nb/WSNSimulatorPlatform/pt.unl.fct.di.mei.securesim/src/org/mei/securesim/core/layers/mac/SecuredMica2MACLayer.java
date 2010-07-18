/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.layers.mac;

import org.mei.securesim.core.engine.Message;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.utils.DataUtils;

/**
 *
 * @author posilva
 */
public class SecuredMica2MACLayer extends Mica2MACLayer {

    protected byte[] keyData = CryptoFunctions.MACLayerGlobalKey();
    protected byte[] iv = CryptoFunctions.MACLayerGlobalIV();

    protected Message decryptMessage(Object message) {

        try {
            Message cm = (Message) message;
            //            System.out.println("Decrypt message: "+ message + " Size: "+ cm.getPayload().length);
            DataInputStream dis = DataUtils.createDataFromByteArray(cm.getPayload());
            int c = dis.readInt();
            int m = dis.readInt();
            byte[] data = new byte[c];
            byte[] mic = new byte[m];
            dis.read(data);
            dis.read(mic);
            getNode().getBateryEnergy().consumeMACVerification(data.length);
            if (CryptoFunctions.verifyMessageIntegrity(data, mic, keyData)) {
                byte[] cdata = CryptoFunctions.decipherData(data, keyData, iv);
                getNode().getBateryEnergy().consumeDecryption(cdata.length);
                return new Message(cdata);

            }
        } catch (IOException ex) {
            Logger.getLogger(SecuredMica2MACLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected boolean encryptMessage() {
        try {
            byte[] data = ((Message) getNode().getMessage()).getPayload();
            byte[] cipherData;
            cipherData = CryptoFunctions.cipherData(data, keyData, iv);
            getNode().getBateryEnergy().consumeEncryption(data.length);
            byte[] mic = CryptoFunctions.createMIC(cipherData, keyData);
            getNode().getBateryEnergy().consumeMAC(data.length);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);
            dos.writeInt(cipherData.length);
            dos.writeInt(mic.length);
            dos.write(cipherData);
            dos.write(mic);
            dos.flush();
            byte[] payload = byteArrayOutputStream.toByteArray();
            ((Message) getNode().getMessage()).setPayload(payload);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SecuredMica2MACLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    protected void transmitMessage() {
        logger().log(Level.FINE, getNode().getId() + ": Trasmit Message");
        if (encryptMessage()) {
            super.transmitMessage();
        }
    }

    @Override
    public boolean deliverMessage(Object message) {
//        DefaultMessage m = (DefaultMessage) message;
        Message m = decryptMessage(message);
        if (m == null) {
            return false;
        } else {
            logger().log(Level.FINE, getNode().getId() + ": Deliver Message");
            return super.deliverMessage(m);
        }
    }

    static Logger logger() {
        return Logger.getLogger(SecuredMica2MACLayer.class.getName());
    }
}
