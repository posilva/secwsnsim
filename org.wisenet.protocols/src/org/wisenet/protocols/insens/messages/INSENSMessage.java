package org.wisenet.protocols.insens.messages;

import org.wisenet.simulator.core.Message;

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
