/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */

package org.wisenet.platform.core.charts;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface IChartDisplay {
    public Graphics getG();
    public void update();
    public void updateChart(double x,double y);
    public Rectangle getRectangle();
}
