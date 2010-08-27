/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.tools.jrc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SourceFileReader {

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
            newClassName += ".java";
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

    public static void main(String[] args) {
        try {
            System.out.println(SourceFileReader.getInstance().getFullPathSourceFile(RuntimeCompiler.class.getName()) + " Exists!");
        } catch (Exception ex) {
            Logger.getLogger(SourceFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
