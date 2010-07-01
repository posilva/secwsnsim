/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.latency;

/**
 *
 * @author CIAdmin
 */
public interface ILatencyMessage {

    public Object getSourceId();

    public Object getDestinationId();

    public Object getUniqueId();

    public void setSourceId(Object id);

    public void setDestinationId(Object id);

    public void setUniqueId(Object id);

    public void incrementHop();

    public int getNumberOfHops();
}
