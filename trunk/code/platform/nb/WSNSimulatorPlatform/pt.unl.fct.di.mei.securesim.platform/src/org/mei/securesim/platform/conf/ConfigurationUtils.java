/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.platform.conf;

import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import org.mei.securesim.platform.conf.ClassConfigReader;
import org.mei.securesim.platform.conf.ClassConfigReader.ClassDefinitions;

/**
 *
 * @author posilva
 */
public class ConfigurationUtils {

    public static final String CONF_NODE_CLASSES_PROPERTIES = "conf/NodeClasses.properties";
    public static final String CONF_RADIOMODEL_CLASSES_PROPERTIES = "conf/RadioModelClasses.properties";
    public static final String CONF_SIMULATOR_CLASSES_PROPERTIES = "conf/SimulatorClasses.properties";

    public static void loadComboWithClasses(JComboBox cbo, HashSet<ClassDefinitions> classes) {
        if (cbo == null) {
            return;
        }
        cbo.removeAllItems();

        for (ClassDefinitions classDefinitions : classes) {
            cbo.addItem(classDefinitions);
        }
    }

    public static HashSet<ClassDefinitions> loadConfigurationClasses(String propertiesFile) {
        try {
            //        final String confSimulatorClassesproperties = "conf/SimulatorClasses.properties";
            ClassConfigReader sc = new ClassConfigReader(propertiesFile);
            return sc.getClasses();
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
