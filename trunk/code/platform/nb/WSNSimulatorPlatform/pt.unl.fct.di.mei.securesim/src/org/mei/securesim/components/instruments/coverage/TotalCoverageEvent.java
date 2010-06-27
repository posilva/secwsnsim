/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.coverage;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.instruments.coverage.ICoverageHandler;
import org.mei.securesim.components.instruments.events.RepeatableEvent;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author Pedro Marques da Silva
 */
public class TotalCoverageEvent extends RepeatableEvent {

    Node sourceNode;
    Node destinationNode;
    private Class messageClass;

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

    @Override
    protected void doActions() {
        try {
        
            Object c = messageClass.newInstance();
            ICoverageMessage ctm= (ICoverageMessage) c;
            ICoverageHandler srcId= (ICoverageHandler) sourceNode.getRoutingLayer();
            ICoverageHandler dstId= (ICoverageHandler) destinationNode.getRoutingLayer();
            ctm.setSourceId(srcId.getCoverageId());
            ctm.setDestinationId(dstId.getCoverageId());
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
}
