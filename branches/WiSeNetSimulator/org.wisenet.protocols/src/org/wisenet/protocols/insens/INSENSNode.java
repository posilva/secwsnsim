/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Mica2SensorNode;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.gui.IDisplayable;

/**
 *
 * @author pedro
 */
public class INSENSNode extends Mica2SensorNode implements IDisplayable {

    /**
     *
     * @param sim
     * @param radioModel
     */
    public INSENSNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
        setEnableFunctioningEnergyConsumption(false);
        setRadius(2);
        setPaintingPaths(true);
        setBaseColor(Color.magenta);
    }

    @Override
    public void init() {
        try {
            if (getId() == 1) {
                setSinkNode(true);
            }
            getGraphicNode().setMarkColor(Color.YELLOW);

            super.init();
        } catch (Exception ex) {
            Logger.getLogger(INSENSNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void displayOn(ISimulationDisplay disp) {
        super.displayOn(disp);
        if (isSinkNode()) {
            getGraphicNode().mark();
        }
        if (getRoutingLayer().isStable()) {
            setBaseColor(Color.BLACK);
        }
    }

    @Override
    public Object getUniqueID() {
        return getId();
    }
}
