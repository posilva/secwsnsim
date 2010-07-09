/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.instruments.coverage.ITotalCoverageHandler;
import org.mei.securesim.components.instruments.latency.ILatencyHandler;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.basic.Mica2SensorNode;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.core.ui.ISimulationDisplay;
import org.mei.securesim.gui.IDisplayable;

/**
 *
 * @author pedro
 */
public class INSENSNode extends Mica2SensorNode implements IDisplayable, ITotalCoverageHandler, ILatencyHandler {

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
    public synchronized  void  displayOn(ISimulationDisplay disp) {
        super.displayOn(disp);
        if (isSinkNode()) {
            getGraphicNode().mark();
        }
    }

    public Object getCoverageId() {
        return getId();
    }

    public Object getLatencyUniqueId() {
        return getId();
    }
}
