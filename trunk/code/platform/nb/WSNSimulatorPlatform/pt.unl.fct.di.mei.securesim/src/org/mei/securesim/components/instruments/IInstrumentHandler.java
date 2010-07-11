/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments;

/**
 *
 * @author CIAdmin
 */
public interface IInstrumentHandler {

    public Object getUniqueId();

    public void probing(IInstrumentMessage message);

    

}
