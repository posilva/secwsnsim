package org.mei.securesim.test.insens.messages;

import java.util.Vector;
import org.mei.securesim.test.insens.INSENSConstants;

/**
 *
 * @author posilva
 */
public class RREQMsg extends INSENSMsg {

    int size;
    Vector<Integer> path;

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
}
