/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens.messages;

import java.util.Vector;
import org.mei.securesim.test.insens.utils.INSENSConstants;

/**
 *
 * @author posilva
 */
public class RUPDMsg extends INSENSMsg {

    int size;
    Vector<ForwardingEntries> forwardingTable=new Vector<ForwardingEntries>();

    public RUPDMsg(byte[] payload) {
        super(payload);
        setType(INSENSConstants.MSG_RUPD);

    }

    @Override
    public byte[] toByteArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected int getDataSize() {
        return forwardingTable.size()*16;
        
    }

    public class ForwardingEntries {
    }

    public Vector<ForwardingEntries> getForwardingTable() {
        return forwardingTable;
    }

    public void setForwardingTable(Vector<ForwardingEntries> forwardingTable) {
        this.forwardingTable = forwardingTable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
