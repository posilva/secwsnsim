package org.mei.securesim.test.insens.messages;

import java.io.IOException;
import java.util.Arrays;
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
    PathInfo pathInfo=new  PathInfo();
    NbrInfo nbrInfo = new NbrInfo();
@Override
    protected int getDataSize() {
        return 8+pathInfo.size*(4+8)+12*nbrInfo.size;
    }
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
    public Object clone() throws CloneNotSupportedException {

        FDBKMsg f = (FDBKMsg) super.clone();
        f.setNbrInfo((NbrInfo) nbrInfo.clone());
        f.setPathInfo((PathInfo) pathInfo.clone());
        f.setMACRParent(Arrays.copyOf(MACRParent, MACRParent.length));
        return f;

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

    

    public class PathInfo implements Cloneable {

        public int id;
        public int size;
        public Vector pathToMe;
        public byte[] MACRx;

        private byte[] toByteArray() {
            try {
                ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
                bados.writeInt(id);
                bados.writeInt(size);
                for (Object n : pathToMe) {
                    bados.writeInt((Integer) n);
                }
                bados.write(MACRx);
                return bados.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(FDBKMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        @Override
        public Object clone() throws CloneNotSupportedException {
            PathInfo p = (PathInfo) super.clone();
            p.pathToMe= new Vector();
            for (int i = 0; i < pathToMe.size(); ++i) {
                p.pathToMe.addElement(((Integer) pathToMe.elementAt(i)).intValue());
            }
            p.MACRx = Arrays.copyOf(MACRx, MACRx.length);
            return p;
        }

        @Override
        public String toString() {

            String out = "[" + id + "] (";
            for (Object integer : pathToMe) {
                out += ((Integer) integer).intValue();
                out += ",";
            }
            out += ")";
            return out;
        }
    }

    public class NbrInfo implements Cloneable {

        public int size;
        public Vector macs;

        private byte[] toByteArray() {
            try {
                ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();

                bados.writeInt(size);
                for (Object m : macs) {
                    bados.writeInt(((MACS) m).id);
                    bados.write(((MACS) m).MACR);
                }

                return bados.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(FDBKMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            NbrInfo n = (NbrInfo) super.clone();
            n.macs=new Vector();
            for (int i = 0; i < macs.size(); ++i) {
                n.macs.addElement(((MACS) macs.elementAt(i)).clone());
            }
            return n;
        }

        @Override
        public String toString() {

            String out = "(";
            for (Object object : macs) {
                MACS m = (MACS) object;
                out += "" + m.id + ",";
            }
            out += ")";
            return out;
        }
    }

    public class MACS implements Cloneable {

        public int id;
        public byte[] MACR;

        public MACS(int id, byte[] MACR) {
            this.id = id;
            this.MACR = MACR;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            MACS m = (MACS) super.clone();
            m.MACR = Arrays.copyOf(MACR, MACR.length);
            return m;

        }
    }
}

