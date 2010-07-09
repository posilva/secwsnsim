package org.mei.securesim.protocols.insens.messages;

import org.mei.securesim.core.engine.BaseMessage;

/**
 *
 * @author CIAdmin
 */
public class INSENSMessage extends BaseMessage {

    public INSENSMessage(byte[] payload) {
        super(payload);
    }

    public INSENSMessage() {
        super();
    }
}
