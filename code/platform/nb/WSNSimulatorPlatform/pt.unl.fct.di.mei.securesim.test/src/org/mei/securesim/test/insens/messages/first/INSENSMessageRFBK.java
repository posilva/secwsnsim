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
import org.mei.securesim.core.engine.BaseMessage;
import org.mei.securesim.utils.DataUtils;

/**
 *
 * @author pedro
 */
public class INSENSMessageRFBK extends INSENSMessage {

    byte[] MACR_Parent;
    PathInfo pathInfo;
    NbrInfo nbrInfo;
    byte[] MACF;

    public INSENSMessageRFBK(byte[] payload) {
        super(payload);
        wrapper = new Wrapper();
    }

    protected class Wrapper extends INSENSMessage.Wrapper {

        @Override
        public void wrap(BaseMessage m) {
            try {
                DataInputStream dis = DataUtils.createDataFromByteArray(m.getPayload());
                type = dis.readInt();
                OWS = dis.readInt();

            } catch (IOException ex) {
                Logger.getLogger(INSENSMessageRFBK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public byte[] createPayload(int _type, int _ows, int _size, Vector<Integer> _path, byte[] _MACF) {
            type = _type;
            OWS = _ows;
            MACF = _MACF;
            return createPayloadData();
        }

        public byte[] createPayloadData() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                dos.writeInt(type);
                dos.writeLong(OWS);
                for (int i = 0; i < MACF.length; i++) {
                    dos.writeByte(MACF[i]);
                }
                dos.flush();
                return bos.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(INSENSMessageRFBK.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;

        }

        public byte[] getMACF() {
            return MACF;
        }

        public void setMACF(byte[] _MACF) {
            MACF = _MACF;
        }

        public byte[] getMACR_Parent() {
            return MACR_Parent;
        }

        public void setMACR_Parent(byte[] _MACR_Parent) {
            MACR_Parent = _MACR_Parent;
        }

        public NbrInfo getNbrInfo() {
            return nbrInfo;
        }

        public void setNbrInfo(NbrInfo _nbrInfo) {
            nbrInfo = _nbrInfo;
        }

        public PathInfo getPathInfo() {
            return pathInfo;
        }

        public void setPathInfo(PathInfo _pathInfo) {
            pathInfo = _pathInfo;
        }
    }

    public class PathInfo {

        public int id;
        public int size;
        public Vector<Integer> pathToNode;
        public byte[] MACR;
    }

    public class NbrInfo {

        int size;
        Vector<NodeMAC> nodeMacs;

        public class NodeMAC {

            int id;
            byte[] MACR;
        }
    }
}
