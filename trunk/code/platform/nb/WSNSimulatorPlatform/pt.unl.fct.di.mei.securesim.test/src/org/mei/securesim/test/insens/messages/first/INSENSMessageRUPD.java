/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens.messages.first;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.core.engine.DefaultMessage;
import org.mei.securesim.test.pingpong.PingPongApplication;
import org.mei.securesim.utils.DataUtils;

/**
 *
 * @author pedro
 */
public class INSENSMessageRUPD extends INSENSMessage {

    int size;
    Vector<Integer> path;
    byte[] MACR;

    public INSENSMessageRUPD(byte[] payload) {
        super(payload);
        wrapper = new Wrapper();
    }

    protected class Wrapper extends INSENSMessage.Wrapper {

        @Override
        public void wrap(DefaultMessage m) {
            try {
                DataInputStream dis = DataUtils.createDataFromByteArray(m.getPayload());
                type = dis.readInt();
                OWS = dis.readInt();
                size=dis.readInt();
                path = new Vector<Integer>();
                for (int i = 0; i < size; i++) {
                    path.add(i);
                }

            } catch (IOException ex) {
                Logger.getLogger(INSENSMessageRUPD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public byte[] createPayload(int _type, int _ows, int _size, Vector<Integer> _path, byte[] _MACR) {
            type = _type;
            OWS = _ows;
            size = _size;
            path = _path;
            MACR = _MACR;
            return createPayloadData();
        }

        public byte[] createPayloadData() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                dos.writeInt(type);
                dos.writeLong(OWS);
                dos.writeInt(size);
                for (Integer id : path) {
                    dos.writeInt(id);
                }
                for (int i = 0; i < MACR.length; i++) {
                    dos.writeByte(MACR[i]);
                }
                dos.flush();
                return bos.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(INSENSMessageRUPD.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;

        }

        public Vector<Integer> getPath() {
            return path;
        }

        public void setPath(Vector<Integer> _path) {
            path = _path;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int _size) {
            size = _size;
        }

        public byte[] getMACR() {
            return MACR;
        }

        public void setMACR(byte[] _MACR) {
            MACR = _MACR;
        }
    }
}
