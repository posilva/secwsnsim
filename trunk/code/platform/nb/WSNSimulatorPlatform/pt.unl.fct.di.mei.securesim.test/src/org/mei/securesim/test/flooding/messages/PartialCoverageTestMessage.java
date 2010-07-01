/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.flooding.messages;

import org.mei.securesim.components.instruments.coverage.ITotalCoverageMessage;

/**
 *
 * @author CIAdmin
 */
public class PartialCoverageTestMessage extends FloodingMessage implements ITotalCoverageMessage {

    public PartialCoverageTestMessage() {
    }

    public PartialCoverageTestMessage(byte[] payload) {
        super(payload);
    }

    public Object getSourceId() {
        return getSource();
    }

    public Object getDestinationId() {
        return getDestin();
    }

    public Object getUniqueId() {
        return getData();
    }

    public void setSourceId(Object id) {
        setSource((Short)id);
    }

    public void setDestinationId(Object id) {
        setDestin((Short)id);
    }

    public void setUniqueId(Object id) {
        setData(id.toString());
    }

}
