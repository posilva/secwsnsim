/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author posilva
 */
public class DataUtils {

    public static DataInputStream createDataFromByteArray(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Data block cannot be null");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("Data block cannot be empty");
        }
        return new DataInputStream(new ByteArrayInputStream(data));
    }

    public static byte [] objectToByteArray( Object o){
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            (oos).writeObject(o);
            byte[] data = bos.toByteArray();
            return data;
        } catch (IOException ex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);

        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            }
        }
        return null;
    }

    public static Object objectFromByteArray(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bis);
            return ois.readObject();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        return null;
    }
}
