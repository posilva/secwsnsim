/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */

package org.wisenet.platform.gui.listeners;

import java.util.EventListener;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface SimulationPanelEventListener extends EventListener{

    public void afterNodeDeploy(DeployEvent event);

    public void beforeNodeDeploy(DeployEvent event);

}
