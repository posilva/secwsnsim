/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class PersistantObject implements Persistent {

    /**
     *
     * @param file
     * @throws PersistantException
     */
    public void saveToXML(String file) throws PersistantException {
        try {
            XMLConfiguration configuration = new XMLConfiguration();
            saveToXML(configuration);
            configuration.save(file);
        } catch (ConfigurationException ex) {
            throw new PersistantException(ex);
        }
    }

    /**
     *
     * @param file
     * @throws PersistantException
     */
    public void loadFromXML(String file) throws PersistantException {
        try {
            XMLConfiguration configuration = new XMLConfiguration(file);
            configuration.load();
            loadFromXML(configuration);
        } catch (ConfigurationException ex) {
            throw new PersistantException(ex);
        }
    }
}
