/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.mac;

import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.ObjectParameter;
import org.wisenet.simulator.common.ObjectParameters;
import org.wisenet.simulator.common.PersistantException;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class MACLayerParameters extends ObjectParameters {

    @Override
    protected void setSupportedParameters() {
    }

    @Override
    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        String name = "MACLayer";
        for (ObjectParameter param : this.getAllParameters()) {
            configuration.addProperty(name + "." + param.getName() + ".label", param.getLabel());
            configuration.addProperty(name + "." + param.getName() + ".value", param.getValue());
            configuration.addProperty(name + "." + param.getName() + ".required", param.isRequired());
        }
    }

    @Override
    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        String name = "MACLayer";
        for (ObjectParameter param : this.getAllParameters()) {
            String label = (String) configuration.getString(name + "." + param.getName() + ".label");
            Object value = configuration.getProperty(name + "." + param.getName() + ".value");
            Boolean required = (Boolean) configuration.getBoolean(name + "." + param.getName() + ".required");
            set(new ObjectParameter(param.getName(), label, value, required));
        }
    }
}
