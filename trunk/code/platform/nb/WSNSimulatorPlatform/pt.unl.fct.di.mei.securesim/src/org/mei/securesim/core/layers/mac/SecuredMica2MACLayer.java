/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.layers.mac;

import org.mei.securesim.core.engine.DefaultMessage;
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

    @Override
    protected void transmitMessage() {
        try {

            byte[] data = ((DefaultMessage) getNode().getMessage()).getPayload();
            byte[] cipherData;
//            System.out.println("Encrypt message: "+ getNode().getMessage() + " Size: "+ data.length);
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
            ((DefaultMessage) getNode().getMessage()).setPayload(payload);
            super.transmitMessage();
        } catch (IOException ex) {
            Logger.getLogger(SecuredMica2MACLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean deliverMessage(Object message) {
        try {
            DefaultMessage cm = (DefaultMessage) message;
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
                super.deliverMessage(new DefaultMessage(cdata));
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(SecuredMica2MACLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
}
