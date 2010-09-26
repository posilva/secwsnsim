/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.platform.utils.IOUtils;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class PlatformConfiguration {

    public static final String CFG_CONFIG_DIR_KEY = "main.config.dir";
    public static final String CFG_CONFIG_DIR_KEY_DEFAULT = "conf";
    public static final String CFG_SOURCES_DIR_KEY = "main.source.dir";
    public static final String CFG_SOURCES_DIR_KEY_DEFAULT = "sources";
    public static String PLATFORM_CONFIGURATION_FILE = "Platform.properties";
    public static final String CFG_OUTPUT_DIR_KEY = "main.output.dir";
    public static final String CFG_LOGS_DIR_KEY_DEFAULT = "logs";
    public static final String CFG_LOGS_DIR_KEY = "main.logs.dir";
    public static final String CFG_GOOGLE_MAPS_KEY_KEY_DEFAULT = "ABQIAAAAXg0F4Zi2pcwHeCjCvk7LRhSoSGvlWgPKK04fS0Rib--DYJNIihQASC7FQc_5lQHTrrgdlZoWfL-eZg";
    public static final String CFG_GOOGLE_MAPS_KEY_KEY = "google.maps.key";
    public static final String CFG_OUTPUT_DIR_KEY_DEFAULT = "output"    ;
    public static final String CFG_CLASSPATH_DIRS_KEY = "extra.classpath.dirs";
    /**
     * Static instance variable for singleton pattern
     */
    private static PlatformConfiguration instance = null;
    /**
     * Configuration properties 
     */
    private Properties plataformProperties = null;
    private String sourcesDirectory;
    private String configDirectory;
    private String outputDirectory;
    private String logsDirectory;
    private String googleMapsKey;

    /**
     * Default constructor
     */
    public PlatformConfiguration() {
        plataformProperties = new Properties();
    }

    /**
     * Singleton access method
     * @return
     */
    public static PlatformConfiguration getInstance() {
        if (instance == null) {
            instance = new PlatformConfiguration();
        }

        return instance;
    }

    public String getLogsDirectory() {
        return logsDirectory;
    }

    /**
     * Read configuration from file
     * @throws Exception if read fail
     */
    public boolean read() throws Exception {
        String configFile = getExistingConfigurationFile();
        if (configFile == null) {
            throw new FileNotFoundException("Platform configuration file <" + PLATFORM_CONFIGURATION_FILE + "> doesn't exists ");
        }
        plataformProperties.load(new FileInputStream(configFile));
        return loadConfigurations();
    }

    private boolean createDefaultDir(String c) {
        if (!IOUtils.dirExists(c)) {
            if (!IOUtils.createDir(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the possible name of the configuration file
     * this can avoid typo errors in configuration file name
     * @return
     */
    private String getExistingConfigurationFile() {
        Vector<String> possibleFileNames = new Vector<String>();
        possibleFileNames.add(PLATFORM_CONFIGURATION_FILE);
        possibleFileNames.add(PLATFORM_CONFIGURATION_FILE.toLowerCase());
        possibleFileNames.add(PLATFORM_CONFIGURATION_FILE.toUpperCase());
        for (String name : possibleFileNames) {
            File f = new File(name);
            if (f.exists()) {
                return name;
            }
        }
        return null;
    }

    private boolean loadConfigurations() {
        sourcesDirectory = getPlataformProperties().getProperty(CFG_SOURCES_DIR_KEY, CFG_SOURCES_DIR_KEY_DEFAULT);
        configDirectory = getPlataformProperties().getProperty(CFG_CONFIG_DIR_KEY, CFG_CONFIG_DIR_KEY_DEFAULT);
        outputDirectory = getPlataformProperties().getProperty(CFG_OUTPUT_DIR_KEY, CFG_OUTPUT_DIR_KEY_DEFAULT);
        logsDirectory = getPlataformProperties().getProperty(CFG_LOGS_DIR_KEY, CFG_LOGS_DIR_KEY_DEFAULT);
        googleMapsKey = getPlataformProperties().getProperty(CFG_GOOGLE_MAPS_KEY_KEY, CFG_GOOGLE_MAPS_KEY_KEY_DEFAULT);
        return PlatformConfiguration.getInstance().createDefaults();
    }

    public Properties getPlataformProperties() {
        return plataformProperties;
    }

    public String getConfigDirectory() {
        return configDirectory;
    }

    public String getGoogleMapsKey() {
        return googleMapsKey;
    }

    public String getSourcesDirectory() {
        return sourcesDirectory;
    }

    public static void main(String[] args) {
        try {
            boolean b = PlatformConfiguration.getInstance().read();
            System.out.println("Defaults: " + (b ? "OK" : "NOK"));
        } catch (Exception ex) {
            Logger.getLogger(PlatformConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected boolean createDefaults() {
        String c = PlatformConfiguration.getInstance().getConfigDirectory();
        String s = PlatformConfiguration.getInstance().getSourcesDirectory();
        String o = PlatformConfiguration.getInstance().getOutputDirectory();
        String l = PlatformConfiguration.getInstance().getLogsDirectory();
        return (createDefaultDir(c) && createDefaultDir(s) && createDefaultDir(o)&& createDefaultDir(l));

    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void init() throws Exception {
        if (!read()){
            throw  new IllegalStateException("Error reading platform settings!");
        }

    }
}
