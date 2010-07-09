/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.messages.evaluation;

import org.mei.securesim.components.instruments.coverage.NodeIdComparator;

/**
 *
 * @author CIAdmin
 */
public class INSENSComparator extends NodeIdComparator {

    @Override
    public boolean isEqual(Object srcId, Object dstId) {
        
        return ((Short)srcId).equals((Short) dstId);
    }
}
