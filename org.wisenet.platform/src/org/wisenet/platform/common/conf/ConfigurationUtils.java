/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.platform.common.conf;

import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import org.wisenet.platform.common.conf.ClassConfigReader.ClassDefinitions;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ConfigurationUtils {

    public static final String CONF_NODE_CLASSES_PROPERTIES = "conf/NodeClasses.properties";
    public static final String CONF_RADIOMODEL_CLASSES_PROPERTIES = "conf/RadioModelClasses.properties";
    public static final String CONF_SIMULATOR_CLASSES_PROPERTIES = "conf/SimulatorClasses.properties";
    public static final String CONF_ROUTING_CLASSES_PROPERTIES = "conf/RoutingLayerClasses.properties";
    public static final String CONF_MAC_CLASSES_PROPERTIES = "conf/MACLayerClasses.properties";
    public static final String CONF_APPLICATION_CLASSES_PROPERTIES = "conf/ApplicationClasses.properties";

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
