/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

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
}
