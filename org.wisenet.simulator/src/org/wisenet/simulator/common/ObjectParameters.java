/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class ObjectParameters extends PersistantObject {

    protected HashMap<String, ObjectParameter> parameters = new HashMap<String, ObjectParameter>();

    protected HashMap<String, ObjectParameter> getParameters() {
        return parameters;
    }

    /**
     *
     */
    public ObjectParameters() {
        setup();
    }

    /**
     * Set the supported parameters and the default values
     */
    protected abstract void setSupportedParameters();

    /**
     *
     */
    private void setup() {
        setSupportedParameters();
    }

    /**
     * 
     * @param name
     * @param value
     */
    public void set(String name, Object value) {
        if (parameters.containsKey(name)) {
            parameters.put(name, new ObjectParameter(name, value));
        } else {
            throw new IllegalStateException("Parameter not supported: " + name);
        }
    }

    public void set(ObjectParameter parameter) {
        String name = parameter.getName();

        if (parameters.containsKey(name)) {
            parameters.put(name, parameter);
        } else {
            throw new IllegalStateException("Parameter not supported: " + name);
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public Object get(String name) {
        return getParameter(name).getValue();
    }

    public ObjectParameter getParameter(String name) {
        if (parameters.containsKey(name)) {
            return parameters.get(name);
        } else {
            throw new IllegalStateException("Parameter not supported: " + name);
        }
    }

    /**
     * 
     * @return
     */
    public final Set<String> getAllParametersNames() {
        final Set s = parameters.keySet();
        return s;
    }

    public final Set<ObjectParameter> getAllParameters() {
        final Set s = new HashSet(parameters.values());
        return s;
    }

    final protected void init(String name, Object value) {
        parameters.put(name, new ObjectParameter(name, value));
    }

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
         String name = getClass().getSimpleName();
        for (ObjectParameter param : this.getAllParameters()) {
            configuration.addProperty(name+"." + param.getName() + ".label", param.getLabel());
            configuration.addProperty(name+"."+ param.getName() + ".value", param.getValue());
            configuration.addProperty(name+"."+ param.getName() + ".required", param.isRequired());
        }
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        String name = getClass().getSimpleName();
        for (ObjectParameter param : this.getAllParameters()) {
            String label = (String) configuration.getString(name + "." + param.getName() + ".label");
            Object value = configuration.getProperty(name + "." + param.getName() + ".value");
            Boolean required = (Boolean) configuration.getBoolean(name + "." + param.getName() + ".required");
            set(new ObjectParameter(param.getName(), label, value, required));
        }
    }
}
