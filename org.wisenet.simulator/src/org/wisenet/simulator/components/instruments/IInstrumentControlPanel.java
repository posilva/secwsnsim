/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface IInstrumentControlPanel {

    /**
     *
     * @param controller
     */
    public void refresh(AbstractInstrument controller);
}
