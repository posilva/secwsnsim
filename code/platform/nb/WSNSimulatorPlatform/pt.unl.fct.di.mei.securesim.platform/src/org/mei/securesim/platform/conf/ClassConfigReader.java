/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.platform.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author posilva
 */
public class ClassConfigReader {

    Properties properties = new Properties();


    public class ClassDefinitions {

        public String className;
        public String label;

        private ClassDefinitions(String className, String label) {
            this.className = className;
            this.label = label;

        }

        @Override
        public String toString() {
            return label;
        }

    }
    HashSet<ClassDefinitions> classes = new HashSet<ClassDefinitions>();
    

    public ClassConfigReader(String file) throws IOException {
        try {
            load(file);
            for (Object key : properties.keySet()) {
                String className = (String) key;
                String label = properties.getProperty(className);

                classes.add(new ClassDefinitions(className, label));
            }
            validateClasses(classes);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassConfigReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void validateClasses(Collection classes) throws ClassNotFoundException {
        for (Object className : classes) {
            Class c = Class.forName(((ClassDefinitions) className).className);
        }
    }

    public HashSet<ClassDefinitions> getClasses() {
        return classes;
    }

    public void setClasses(HashSet<ClassDefinitions> classes) {
        this.classes = classes;
    }
    protected void load(String file) throws IOException {
        properties.load(new FileInputStream(file));
    }

}
