/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.common;

import java.io.Serializable;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ObjectParameter implements Serializable, Cloneable {

    private static long counter = 0;
    protected String name = "parameter" + (counter++);
    protected String label = "This is the " + name;
    protected boolean required = true;
    protected Object value = null;

    public ObjectParameter(String name, Object value) {
        initObject(name, name, value, true);
    }

    public ObjectParameter(String name, String label, Object value) {
        initObject(name, label, value, true);
    }

    public ObjectParameter(String name, String label, Object value, boolean required) {
        initObject(name, label, value, required);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private void initObject(String name, String label, Object value, boolean required) {
        this.name = name;
        this.label = label;
        this.value = value;
        this.required = required;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
