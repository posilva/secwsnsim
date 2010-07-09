/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.flooding.tests;

import org.mei.securesim.components.instruments.coverage.NodeIdComparator;
import org.mei.securesim.protocols.common.BasicNode;

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
