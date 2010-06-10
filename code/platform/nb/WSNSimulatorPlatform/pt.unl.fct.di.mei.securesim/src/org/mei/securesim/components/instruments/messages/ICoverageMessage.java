/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.messages;

/**
 *
 * @author Pedro Marques da Silva
 */
public interface ICoverageMessage {

    public Object getSourceNodeId();

    public Object getDestinationNodeId();

    public int getId();

    public void setSourceId(Object id);

    public void setDestinationId(Object id);
}
