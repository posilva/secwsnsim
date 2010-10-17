/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.common;

import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface Persistent {

    /**
     *
     * @param file
     * @throws PersistantException
     */
    public void saveToXML(String file) throws PersistantException;

    /**
     *
     * @param file
     * @throws PersistantException
     */
    public void loadFromXML(String file) throws PersistantException;

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void saveToXML(XMLConfiguration configuration) throws PersistantException;

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void loadFromXML(XMLConfiguration configuration) throws PersistantException;
}
