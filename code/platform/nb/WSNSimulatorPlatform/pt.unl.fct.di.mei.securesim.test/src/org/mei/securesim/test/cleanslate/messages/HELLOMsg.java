/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.cleanslate.messages;

/**
 *
 * @author CIAdmin
 */
public class HELLOMsg extends CleanSlateMsg{
    private String nodeId;

    public HELLOMsg(byte[] payload) {
        super(payload);
    }
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }



}
