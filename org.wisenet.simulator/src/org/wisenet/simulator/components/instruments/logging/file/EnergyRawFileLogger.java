/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments.logging.file;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class EnergyRawFileLogger extends EnergyFileLogger {

    /**
     *
     */
    public static final String LOGS_DIR = "logs";
    private boolean updating;
    private final Object writeMonitor = new Object();
    private boolean exiting = false;

    @Override
    public void init() {
        try {
            createLogDir();
            this.filename = LOGS_DIR + File.separatorChar + "Energy_" + System.currentTimeMillis() + ".dat";
            FileOutputStream fos = new FileOutputStream(filename);
            outputStream = new DataOutputStream(new BufferedOutputStream(fos));
        } catch (FileNotFoundException ex) {
            Utilities.handleException(ex);
        }
    }

    @Override
    public void update(short id, String event, long realTime, long simTime, double value, String state) {
        updating = true;
        if (exiting) {
            return;
        }
        try {
            ((DataOutputStream) getOutputStream()).writeShort(id);
            ((DataOutputStream) getOutputStream()).writeUTF(event);
            ((DataOutputStream) getOutputStream()).writeLong(realTime);
            ((DataOutputStream) getOutputStream()).writeLong(simTime);
            ((DataOutputStream) getOutputStream()).writeDouble(value);
            ((DataOutputStream) getOutputStream()).writeUTF(state);
            getOutputStream().flush();
            synchronized (writeMonitor) {
                writeMonitor.notifyAll();
            }

        } catch (IOException ex) {
            Utilities.handleException(ex);
        }
        updating = false;
    }

    /**
     *
     */
    @Override
    public void open() {
    }

    /**
     *
     */
    @Override
    public void close() {
        try {
            synchronized (writeMonitor) {
                exiting = true;
                if (updating) {
                    writeMonitor.wait();
                }
                ((DataOutputStream) getOutputStream()).close();
                exiting = true;

            }
        } catch (Exception e) {
            Utilities.handleException(e);
        }
    }

    private void createLogDir() {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            logDir.mkdir();
        }
    }

    /**
     *
     */
    @Override
    public void reset() {
        close();
        init();
    }
}
