/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.unl.fct.di.mei.securesim.platform.charts;

import java.awt.Graphics;

/**
 *
 * @author posilva
 */
public interface IChartDisplay {
    public Graphics getG();
    public void update();
    public void updateChart(double x,double y);
}
