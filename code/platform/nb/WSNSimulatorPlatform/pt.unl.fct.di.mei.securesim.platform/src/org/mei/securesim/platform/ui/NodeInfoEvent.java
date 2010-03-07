/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.platform.ui;

import java.util.EventObject;

/**
 *
 * @author posilva
 */
public class NodeInfoEvent extends EventObject{
    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public NodeInfoEvent(Object source, int x, int y) {
        super(source);
        this.x = x;
        this.y = y;

    }

//    public GraphicNode getCircle(){
//        return  (GraphicNode) source;
//    }

}
