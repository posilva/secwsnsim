package org.mei.securesim.test.insens.messages;

import java.util.Vector;
import org.mei.securesim.test.insens.INSENSConstants;

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

    public class PathInfo {
        public int id;
        public int size;
        public Vector<Integer> pathToMe;
        public byte[] MACRx;
    }

    public class NbrInfo {
        public int size;

        public class MACS{
            public int id;
            public byte[] MACR;
        }

    }
}
