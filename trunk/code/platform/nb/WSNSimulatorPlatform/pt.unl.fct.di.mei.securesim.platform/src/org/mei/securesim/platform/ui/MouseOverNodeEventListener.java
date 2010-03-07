/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.platform.ui;

import java.util.EventListener;

/**
 *
 * @author posilva
 */
public interface MouseOverNodeEventListener extends EventListener{
    public void mouseExitCircleOccured(NodeInfoEvent evt);
    public void mouseEnterCircleOccured(NodeInfoEvent evt);
}
