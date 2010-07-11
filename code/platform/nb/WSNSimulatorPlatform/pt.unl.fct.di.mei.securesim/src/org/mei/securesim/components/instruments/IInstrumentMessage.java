/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments;

/**
 *
 * @author Pedro Marques da Silva
 */
public interface IInstrumentMessage {

    public Object getSourceId();

    public Object getDestinationId();

    public long getUniqueId();

    public void setSourceId(Object id);

    public void setDestinationId(Object id);

    public void setUniqueId(long id);
}
