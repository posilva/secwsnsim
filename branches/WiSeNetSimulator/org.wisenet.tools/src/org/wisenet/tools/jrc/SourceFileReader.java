/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.tools.jrc;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SourceFileReader {
    public static final String JAVA_EXTENSIONS = ".java";

    String classFileName;
    String sourcesDir = "." + File.separator + "src";

    public SourceFileReader() {
    }
    private static SourceFileReader instance = null;

    public static SourceFileReader getInstance() {
        if (instance == null) {
            instance = new SourceFileReader();
        }
        return instance;
    }

    public String getSourcesDir() {
        return sourcesDir;
    }

    public void setSourcesDir(String sourcesDir) {
        this.sourcesDir = sourcesDir;
    }

    public String loadClassSource(String className) {
        String newClassName = null;
        if (className.contains(".")) {
            newClassName = className.replace(".", File.separator);
        } else {
            newClassName = className;
        }
        if (newClassName != null) {
            newClassName += JAVA_EXTENSIONS;
        }

        return newClassName;
    }

    public String getFullPathSourceFile(String className) throws Exception {
        String sourceFile = loadClassSource(className);
        String fullPath = getSourcesDir() + File.separator + sourceFile;
        if (!new File(fullPath).exists()) {
            throw new FileNotFoundException(fullPath + " File not found!");
        }
        return fullPath;
    }
}
