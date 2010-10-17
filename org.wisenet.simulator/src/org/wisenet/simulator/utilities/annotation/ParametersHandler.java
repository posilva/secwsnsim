/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.utilities.annotation;

import java.lang.reflect.Field;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public final class ParametersHandler {

    /**
     *
     * @param c
     * @return
     */
    public static boolean isAnnotated(Class c) {
        Class[] interfaces = c.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            Class class1 = interfaces[i];
            if (class1.getName().equals(Annotated.class.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param o
     */
    public static void readParameters(Object o) {
        if (isAnnotated(o.getClass())) {
            Vector inputParameters = new Vector();
            for (Field f : o.getClass().getDeclaredFields()) {
                Parameter p = f.getAnnotation(Parameter.class);
                if (p != null) {
                    inputParameters.add(f);
                }
            }

            for (Object fd : inputParameters) {
                try {
                    Field field = (Field) fd;
                    Parameter p = field.getAnnotation(Parameter.class);
                    String in = JOptionPane.showInputDialog(p.label());
                    field.setAccessible(true);
                    field.set(o, in);
                } catch (IllegalAccessException ex) {
                    Utilities.handleException(ex);
                } catch (IllegalArgumentException ex) {
                    Utilities.handleException(ex);
                }
            }
        }
    }
}
