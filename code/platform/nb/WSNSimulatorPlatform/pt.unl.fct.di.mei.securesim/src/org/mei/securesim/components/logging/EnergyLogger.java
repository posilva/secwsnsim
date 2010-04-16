/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.logging;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author posilva
 */
public class EnergyLogger {
    protected BufferedOutputStream outputStream;
    protected String logFilename;

    public EnergyLogger(String logFilename) throws FileNotFoundException {
        this.logFilename = logFilename;
        outputStream=new BufferedOutputStream(new FileOutputStream(logFilename));
    }

    public String getLogFilename() {
        return logFilename;
    }

    public void setLogFilename(String logFilename) {
        this.logFilename = logFilename;
    }

    public BufferedOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(BufferedOutputStream outputStream) {
        this.outputStream = outputStream;
    }
    public synchronized void log(String nodeId, String timestamp, String value, String event) {
        String l="<id></id><time></time><value></value><event></event>";
    } 
}
