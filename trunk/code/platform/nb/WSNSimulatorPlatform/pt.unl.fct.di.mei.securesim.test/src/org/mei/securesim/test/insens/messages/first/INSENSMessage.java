/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens.messages.first;

import org.mei.securesim.core.engine.BaseMessage;

/**
 *
 * @author pedro
 */
public class INSENSMessage extends BaseMessage {

    protected int type;
    protected int OWS;
    protected Wrapper wrapper;

    public INSENSMessage(byte[] payload) {
        super(payload);
        wrapper = new Wrapper();
    }

    public Wrapper getWrapper() {
        return wrapper;
    }

    protected class Wrapper {

        public void wrap(BaseMessage m) {
            throw new UnsupportedOperationException("Must be implemented in derived classes");
        }

        public byte[] createPayload() {
            throw new UnsupportedOperationException("Must be implemented in derived classes");
        }

        public int getOWS() {
            return OWS;
        }

        public int getType() {
            return type;
        }

        public void setOWS(int _OWS) {
            OWS = _OWS;
        }

        public void setType(int _type) {
            type = _type;
        }
    }
}
