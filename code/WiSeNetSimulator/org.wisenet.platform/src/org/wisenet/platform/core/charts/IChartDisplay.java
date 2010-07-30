/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wisenet.platform.core.charts;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author posilva
 */
public interface IChartDisplay {
    public Graphics getG();
    public void update();
    public void updateChart(double x,double y);
    public Rectangle getRectangle();
}
