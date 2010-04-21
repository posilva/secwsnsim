package org.mei.securesim.test.insens.messages;

import java.util.Vector;
import org.mei.securesim.test.insens.utils.INSENSConstants;

/**
 *
 * @author posilva
 */
public class RREQMsg extends INSENSMsg {

    int size;
    Vector<Integer> path;
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
}
