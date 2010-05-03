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
public class RREQMsg extends INSENSMsg {

    int size;
    Vector<Integer> path=new Vector<Integer>();
    int id;

    public RREQMsg(byte[] payload) {
        super(payload);
        setType(INSENSConstants.MSG_RREQ);

    }

    public Vector<Integer> getPath() {
        return path;
    }

    public void setPath(Vector<Integer> path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /**
     * 
     * @return
     */
    public byte[] toByteArray() {
        try {
            ByteArrayDataOutputStream bados = new ByteArrayDataOutputStream();
            bados.writeInt(getType());
            bados.writeInt(getId());
            bados.writeLong(getOWS());
            setSize(getPath().size());
            bados.writeInt(getSize());
            for (int i = 0; i < getSize(); i++) {
                bados.writeInt(getPath().get(i));
            }
            return bados.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(RREQMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RREQMsg m = (RREQMsg) super.clone();
        m.path = new Vector<Integer>();
        for (Integer i : path) {
            m.path.addElement(i.intValue());
        }
        return m;
    }

    @Override
    protected int getDataSize() {
        return path.size()*4;
    }

}
