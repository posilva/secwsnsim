package org.wisenet.simulator.temp.insens.messages;

import org.wisenet.simulator.core.Message;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class INSENSMessage extends Message {

    public INSENSMessage(byte[] payload) {
        super(payload);
    }

    public INSENSMessage() {
        super();
    }
}
