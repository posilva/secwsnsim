/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.messages.evaluation;

import org.mei.securesim.components.instruments.coverage.ITotalCoverageMessage;

/**
 *
 * @author CIAdmin
 */
public class TotalCoverageTestMessage extends EvaluationINSENSDATAMessage implements ITotalCoverageMessage {

    public TotalCoverageTestMessage() {
    }

    public TotalCoverageTestMessage(byte[] payload) {
        super(payload);
    }

    public void setData(byte[] data) {
        setPayload(data);
    }

    public byte[] getData() {
        return getPayload();
    }
}
