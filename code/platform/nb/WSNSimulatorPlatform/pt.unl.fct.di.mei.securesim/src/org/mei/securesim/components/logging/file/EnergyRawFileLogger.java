/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.logging.file;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author posilva
 */
public class EnergyRawFileLogger extends EnergyFileLogger {

    @Override
    public void init() {
        try {
            this.filename = "Energy_" + System.currentTimeMillis() + ".dat";
            FileOutputStream fos = new FileOutputStream(filename);
            outputStream = new DataOutputStream(new BufferedOutputStream(fos));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EnergyRawFileLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized  void update(short id, String event, long realTime, long simTime, double value, String state) {
        try {
            ((DataOutputStream) getOutputStream()).writeShort(id);
            ((DataOutputStream) getOutputStream()).writeUTF(event);
            ((DataOutputStream) getOutputStream()).writeLong(realTime);
            ((DataOutputStream) getOutputStream()).writeLong(simTime);
            ((DataOutputStream) getOutputStream()).writeDouble(value);
            ((DataOutputStream) getOutputStream()).writeUTF(state);
            getOutputStream().flush();
        } catch (IOException ex) {
            Logger.getLogger(EnergyRawFileLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void open() {
        
    }

    @Override
    public void close() {
        try {
            ((DataOutputStream)getOutputStream()).close();
        } catch (Exception e) {
        }
    }
}
