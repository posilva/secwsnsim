/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.platform.utils;

import java.io.File;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class IOUtils {

    public static boolean fileExists(String file) {
        return fileExists(new File(file));
    }

    public static boolean fileExists(File file) {
        return file.exists();
    }

    public static boolean dirExists(String file) {
        return dirExists(new File(file));
    }

    public static boolean dirExists(File file) {
        return file.isDirectory() && file.exists();
    }

    public static boolean dirExists(String file, boolean create) {
        return dirExists(new File(file), create);
    }

    public static boolean dirExists(File file, boolean create) {
        boolean exists = dirExists(file);

        if (!exists && create) {
            return createDir(file);
        } else {
            return exists;
        }
    }

    public static boolean createDir(String dir) {
        return createDir(new File(dir));
    }

    public static boolean createDir(File dir) {
        if (dir.exists()) {
            return false;
        } else {
            return (dir.mkdir());
        }
    }
}
