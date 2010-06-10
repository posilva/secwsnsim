/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.events;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.instruments.messages.CoverageTestMessage;
import org.mei.securesim.components.instruments.messages.ICoverageMessage;
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
            ctm.setSourceId(sourceNode.getId());
            ctm.setDestinationId(destinationNode.getId());
            sourceNode.sendMessage(ctm);
        } catch (InstantiationException ex) {
            Logger.getLogger(TotalCoverageEvent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TotalCoverageEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setMessageClass(Class messageClass) {
        this.messageClass=messageClass;
    }
}
