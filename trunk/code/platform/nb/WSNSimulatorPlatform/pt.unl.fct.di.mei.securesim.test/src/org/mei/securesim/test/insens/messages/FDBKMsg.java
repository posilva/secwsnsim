package org.mei.securesim.test.insens.messages;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.test.insens.utils.ByteArrayDataOutputStream;
import org.mei.securesim.test.insens.utils.INSENSConstants;

/**
 *
 * @author posilva
 */
public class FDBKMsg extends INSENSMsg {

    byte[] MACRParent;
    PathInfo pathInfo;
    NbrInfo nbrInfo;

    public byte[] getMACRParent() {
        return MACRParent;
    }

    public void setMACRParent(byte[] MACRParent) {
        this.MACRParent = MACRParent;
    }

    public NbrInfo getNbrInfo() {
        return nbrInfo;
    }

    public void setNbrInfo(NbrInfo nbrInfo) {
        this.nbrInfo = nbrInfo;
    }

    public PathInfo getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(PathInfo pathInfo) {
        this.pathInfo = pathInfo;
    }

    public FDBKMsg(byte[] payload) {
        super(payload);
        setType(INSENSConstants.MSG_FDBK);
    }

    @Override
    public  Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public byte[] toByteArray() {
        try {
            byte[] pathInfoByteArray = getPathInfo().toByteArray();
            byte[] nbrInfoByteArray = getNbrInfo().toByteArray();
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.write(pathInfoByteArray);
            bados.write(nbrInfoByteArray);
            bados.writeLong(getOWS());
            bados.writeInt(getType());
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(FDBKMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public class PathInfo implements Cloneable{

        public int id;
        public int size;
        public Vector<Integer> pathToMe;
        public byte[] MACRx;

        private byte[] toByteArray() {
            try {
                ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
                bados.writeInt(id);
                bados.writeInt(size);
                for (Integer n : pathToMe) {
                    bados.writeInt(n);
                }
                bados.write(MACRx);
                return bados.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(FDBKMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
    }

    public class NbrInfo implements Cloneable{

        public int size;
        public Vector<MACS> macs;

        private byte[] toByteArray() {
            try {
                ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();

                bados.writeInt(size);
                for (MACS m : macs) {
                    bados.writeInt(m.id);
                    bados.write(m.MACR);
                }

                return bados.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(FDBKMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
    }


    public class MACS implements Cloneable{

        public int id;

        public MACS(int id, byte[] MACR) {
            this.id = id;
            this.MACR = MACR;
        }
        public byte[] MACR;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
    }
}

