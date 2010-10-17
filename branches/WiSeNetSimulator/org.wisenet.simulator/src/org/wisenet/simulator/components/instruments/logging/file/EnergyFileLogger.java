/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments.logging.file;

import java.io.OutputStream;
import org.wisenet.simulator.components.instruments.logging.EnergyLogger;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class EnergyFileLogger extends EnergyLogger{
    String filename;
    OutputStream outputStream;

    /**
     *
     * @return
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     *
     * @param outputStream
     */
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
   

}
