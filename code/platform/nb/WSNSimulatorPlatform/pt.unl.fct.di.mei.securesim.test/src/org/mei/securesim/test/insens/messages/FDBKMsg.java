package org.mei.securesim.test.insens.messages;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mei.securesim.test.insens.utils.ByteArrayDataOutputStream;
import org.mei.securesim.test.insens.utils.INSENSConstants;
import org.mei.securesim.test.insens.utils.NbrInfo;
import org.mei.securesim.test.insens.utils.PathInfo;

/**
 *
 * @author posilva
 */
public class FDBKMsg extends INSENSMsg {

    byte[] MACRParent;
    PathInfo pathInfo = new PathInfo();
    NbrInfo nbrInfo = new NbrInfo();

    @Override
    protected int getDataSize() {
        return 8 + pathInfo.size * (4 + 8) + 12 * nbrInfo.size;
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
        setShowColor(true);
        setColor(Color.CYAN);
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

}

