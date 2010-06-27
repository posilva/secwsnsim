/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.flooding.tests;

import org.mei.securesim.components.instruments.coverage.NodeIdComparator;
import org.mei.securesim.test.common.BasicNode;

/**
 *
 * @author CIAdmin
 */
public class FloodingComparator extends NodeIdComparator {

    @Override
    public boolean isEqual(Object srcId, Object dstId) {
        
        return ((Short)srcId).equals((Short) dstId);
    }
}
