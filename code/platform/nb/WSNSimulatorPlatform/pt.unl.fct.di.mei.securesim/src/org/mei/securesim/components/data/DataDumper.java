/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author posilva
 */
public class DataDumper extends Thread {

    InputStream inputStream;
    OutputStream outputStream;

    public DataDumper(InputStream is, OutputStream os) {
        this.outputStream = os;
        inputStream = is;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                if (inputStream.available() > 0) {
                    outputStream.write(inputStream.read());
                }
            } catch (IOException ex) {
                Logger.getLogger(DataDumper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
