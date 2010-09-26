package org.wisenet.protocols.insens.messages;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.protocols.insens.INSENSConstants;
import org.wisenet.protocols.insens.INSENSException;
import org.wisenet.protocols.insens.INSENSFunctions;
import org.wisenet.simulator.core.Message;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class INSENSMessage extends Message {

    public INSENSMessage(byte[] payload) {

        super(payload);
        byte type;
        try {
            type = INSENSFunctions.getMessageType(payload);

            setMessageColor(type);
        } catch (INSENSException ex) {
            Logger.getLogger(INSENSMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public INSENSMessage() {
        super();
    }

    private void setMessageColor(byte type) {
        switch (type) {
            case INSENSConstants.MSG_FEEDBACK:
                setColor(Color.RED);
            case INSENSConstants.MSG_DATA:
                setColor(Color.GREEN);
            case INSENSConstants.MSG_ROUTE_REQUEST:
                setColor(Color.YELLOW);
            case INSENSConstants.MSG_ROUTE_UPDATE:
                setColor(Color.CYAN);
        }
    }
}
