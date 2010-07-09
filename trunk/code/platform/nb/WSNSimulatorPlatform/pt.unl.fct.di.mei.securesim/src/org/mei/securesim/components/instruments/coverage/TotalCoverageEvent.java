/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.coverage;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author Pedro Marques da Silva
 */
public class TotalCoverageEvent extends Event {

    Node sourceNode;
    Node destinationNode;
    private Class messageClass;
    private long messageUniqueId;

    public long getMessageUniqueId() {
        return messageUniqueId;
    }

    public void setMessageUniqueId(long messageUniqueId) {
        this.messageUniqueId = messageUniqueId;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }

    protected void doActions() {
        try {
        
            Object c = messageClass.newInstance();
            ITotalCoverageMessage ctm= (ITotalCoverageMessage) c;
            ctm.setData("TCE".getBytes());
            ITotalCoverageHandler srcId= (ITotalCoverageHandler) sourceNode;
            ITotalCoverageHandler dstId= (ITotalCoverageHandler) destinationNode;
            ctm.setSourceId(srcId.getCoverageId());
            ctm.setDestinationId(dstId.getCoverageId());
            ctm.setUniqueId(getMessageUniqueId());
            sourceNode.sendMessage(ctm);
        } catch (InstantiationException ex) {
            Logger.getLogger(TotalCoverageEvent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TotalCoverageEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMessageClass(Class messageClass) {
        this.messageClass=messageClass;
    }

    @Override
    public void execute() {
        doActions();
    }
}
