/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.logging.file;

import java.io.OutputStream;
import org.mei.securesim.components.logging.EnergyLogger;

/**
 *
 * @author posilva
 */
public abstract class EnergyFileLogger extends EnergyLogger{
    String filename;
    OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
   

}
