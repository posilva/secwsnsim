/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.logging;

import java.io.IOException;
import org.mei.securesim.utils.xml.XMLWriter;

/**
 *
 * @author posilva
 */
public class EnergyLogger extends XMLWriter{

    public EnergyLogger(String fileName) throws IOException {
        super(fileName);
    }
}
