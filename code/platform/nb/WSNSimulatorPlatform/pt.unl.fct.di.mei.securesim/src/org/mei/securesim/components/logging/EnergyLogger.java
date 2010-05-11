/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.logging;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author posilva
 */
public class EnergyLogger {

    protected BufferedOutputStream outputStream;
    protected String logFilename;
    private boolean opened = false;
    final static String XML_ENCODE_LINE = "<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "windows-1252" + '"' + "?>\n";

    public EnergyLogger(String logFilename) throws FileNotFoundException {
        this.logFilename = logFilename;
        outputStream = new BufferedOutputStream(new FileOutputStream(logFilename));
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
        if (!opened) openLog();
//        String log = "<id></id><time></time><value></value><event></event>";

    }

    public synchronized void log(String log) {
        try {
            if (!opened) openLog();
            outputStream.write(log.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(EnergyLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openLog() {
        if (!opened) {
            try {
                outputStream.write(XML_ENCODE_LINE.getBytes());
                opened=true;
            } catch (IOException ex) {
                Logger.getLogger(EnergyLogger.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }
}
