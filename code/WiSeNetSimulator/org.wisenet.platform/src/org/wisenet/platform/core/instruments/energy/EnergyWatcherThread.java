/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wisenet.platform.core.instruments.energy;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.platform.core.instruments.energy.ui.EnergyWatcherDialog;

/**
 *
 * @author posilva
 */
public class EnergyWatcherThread extends Thread{

    EnergyWatcherDialog dialog;
    protected  PipedOutputStream outputStream=new PipedOutputStream();

    protected PipedInputStream inputStream;

    protected DataInputStream datainputStream;

    public PipedOutputStream getOutputStream() {
        return outputStream;
    }


    public EnergyWatcherThread() {
        try {

        inputStream= new PipedInputStream(outputStream);
        datainputStream= new DataInputStream(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(EnergyWatcherThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        super.run();
        dialog = new EnergyWatcherDialog(null, false);
        dialog.setSize(300, 200);
        dialog.setVisible(true);
        dialog.pack();
        while (true) {
            try {
                double y = datainputStream.readDouble();
                double x = datainputStream.readDouble();
              //  System.out.println("X: "+ x + "Y:"+y);
                dialog.updateChart(x, y);
            } catch (IOException ex) {
                Logger.getLogger(EnergyWatcherThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

}
