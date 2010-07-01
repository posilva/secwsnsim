/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.coverage;

/**
 *
 * @author Pedro Marques da Silva
 */
public interface IPartiallCoverageMessage {

    public Object getSourceId();

    public Object getDestinationId();

    public Object getUniqueId();

    public void setSourceId(Object id);

    public void setDestinationId(Object id);

    public void setUniqueId(Object id);
}
