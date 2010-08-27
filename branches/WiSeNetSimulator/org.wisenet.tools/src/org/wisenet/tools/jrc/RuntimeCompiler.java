/**
 * Copyright (C) 2010  posilva
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or (at
 *  your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. 
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package org.wisenet.tools.jrc;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import javax.swing.JOptionPane;

import org.wisenet.tools.jrc.editor.SourceEditor;

/**
 * @author Pedro Marques da Silva
 * 
 */
public class RuntimeCompiler {

    File sourceFile = null;
    File classDir = new File(".");
    private Object compiledObject = null;
    private ClassLoader parentLoader = RuntimeCompiler.class.getClassLoader();

    /**
     * @return the compiledObject
     */
    public Object getCompiledObject() {
        return compiledObject;
    }

    /**
     * @return the parentLoader
     */
    public ClassLoader getParentLoader() {
        return parentLoader;
    }

    /**
     * @param parentLoader
     *            the parentLoader to set
     */
    public void setParentLoader(ClassLoader parentLoader) {
        this.parentLoader = parentLoader;
    }

    /**
     * @return the sourceFile
     */
    public File getSourceFile() {
        return sourceFile;
    }

    /**
     * @param sourceFile
     *            the sourceFile to set
     */
    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * @return the classDir
     */
    public File getClassDir() {
        return classDir;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public boolean compile() throws Exception {
        File sourceCode = getSourceFile();

        String filename = sourceCode.getName();
        String classname = filename.substring(0, filename.length() - 5);
        String classpath = getClassPath();
        String[] args2 = new String[]{"-classpath", classpath, "-d",
            getClassDir().getAbsolutePath(), filename};

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        int status = com.sun.tools.javac.Main.compile(args2, printWriter);
        if (status == 0) {
            compiledObject = loadClass(classname, getClassDir());
            return true;
        } else {
            throw new RuntimeCompilationException(sw.toString());
        }

    }

    /**
     * @param classDir
     *            the classDir to set
     */
    public void setClassDir(File classDir) {
        this.classDir = classDir;
    }

    public Object invokeMethod(String name, Class[] parameters,
            Object[] arguments) throws Exception {
        Method method = compiledObject.getClass().getMethod(name, parameters);
        Object result = method.invoke(compiledObject, arguments);
        return result;
    }

    /**
     * @param classname
     * @return
     * @throws Exception
     */
    private Object loadClass(String classname, File classesDir)
            throws Exception {
        if (parentLoader == null) {
            parentLoader = RuntimeCompiler.class.getClassLoader();
        }

        URLClassLoader classloader = new URLClassLoader(new URL[]{classesDir.toURI().toURL()}, parentLoader);
        Class cls1 = classloader.loadClass(classname);
        return cls1.newInstance();
    }

    protected String getClassPath() {
        return System.getProperties().getProperty("java.class.path", null);
    }

    public void edit() throws Exception {
        SourceEditor srcEditor = new SourceEditor(this);
        srcEditor.edit();

    }

    public static void main(String[] args) throws Exception {
        try {
            RuntimeCompiler rc = new RuntimeCompiler();
            rc.setClassDir(new File(System.getProperty("user.dir")));
            rc.setSourceFile(new File("Source.java"));
            rc.edit();
            if (rc.compile()) {
                Class[] parameters = new Class[]{Object.class};
                Object arguments[] = {"Hello teste"};
                String method = "executeAttack";
                Object result = rc.invokeMethod(method, parameters, arguments);
                System.out.println("SOURCE: " + result);
            } else {
                System.out.println("Error compiling");
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            JOptionPane.showMessageDialog(null, sw.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
