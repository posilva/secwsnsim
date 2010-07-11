package org.mei.securesim.protocols.insens.messages;

import org.mei.securesim.core.engine.Message;

/**
 *
 * @author CIAdmin
 */
public class INSENSMessage extends Message {

    public INSENSMessage(byte[] payload) {
        super(payload);
    }

    public INSENSMessage() {
        super();
    }
}
