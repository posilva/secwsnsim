/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.common;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class ObjectParameters {

    protected HashMap<String, Object> parameters = new HashMap<String, Object>();

    protected HashMap<String, Object> getParameters() {
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
            parameters.put(name, value);
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
}
