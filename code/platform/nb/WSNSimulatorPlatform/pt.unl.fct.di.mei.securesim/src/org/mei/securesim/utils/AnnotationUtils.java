/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.utils;

import java.lang.reflect.Field;
import java.util.Vector;
import org.mei.securesim.utils.annotation.Annotated;
import org.mei.securesim.utils.annotation.EnergyModelParameter;

/**
 *
 * @author posilva
 */
public class AnnotationUtils {

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

    public static Vector readEnergyModelParametersFields(Object o) {
        Vector inputParameters = new Vector();
        if (isAnnotated(o.getClass())) {

            for (Field f : o.getClass().getDeclaredFields()) {
                EnergyModelParameter p = f.getAnnotation(EnergyModelParameter.class);
                if (p != null) {
                    inputParameters.add(f);
                }

            }

        }
        return inputParameters;
    }

}
