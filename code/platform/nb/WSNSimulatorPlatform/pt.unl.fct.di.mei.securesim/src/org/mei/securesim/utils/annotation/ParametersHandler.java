/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.utils.annotation;

import java.lang.reflect.Field;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author posilva
 */
public final class ParametersHandler {

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
                    Logger.getLogger(ParametersHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(AnnotationTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
