/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.common;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface Parameterizable {

    public ObjectParameters getParameters();
     public void  setParameters(ObjectParameters params);
}
