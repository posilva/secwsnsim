/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.platform.evaluation;

import java.awt.Rectangle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.platform.utils.gui.GUI_Utils;
import org.wisenet.simulator.components.evaluation.EvaluationDeployModeEnum;
import org.wisenet.simulator.components.evaluation.IEvaluationDeploy;
import org.wisenet.simulator.components.instruments.SimulationController;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.topology.GridTopologyManager;
import org.wisenet.simulator.components.topology.RandomTopologyManager;
import org.wisenet.simulator.core.nodes.Node;
import org.wisenet.simulator.core.nodes.basic.SensorNode;
import org.wisenet.simulator.core.nodes.factories.NodeFactory;

/**
 *
 * @author CIAdmin
 */
public class PlatformEvaluationDeploy implements IEvaluationDeploy {

    private EvaluationDeployModeEnum mode;
    private int deployParam;
    private int w;
    private int h;

    public PlatformEvaluationDeploy(EvaluationDeployModeEnum mode, int deployParam, int w, int h) {
        this.mode = mode;
        this.deployParam = deployParam;
        this.w = w;
        this.h = h;
    }

    public void deploy() {

        if (this.mode == EvaluationDeployModeEnum.GRID) {
            deployGrid();
        } else if (this.mode == EvaluationDeployModeEnum.RANDOM) {
            deployRandom();
        }
    }

    private void deployRandom() {
        try {
            Simulation simulation = SimulationController.getInstance().getSimulation();
            Rectangle deployArea = new Rectangle(10, 10, w, h);
            int nNodes = deployParam;

            NodeFactory nf = simulation.getNodeFactory();
            Vector<Node> nodes = (Vector<Node>) nf.createNodes(nNodes);
            RandomTopologyManager tm = new RandomTopologyManager();
            nodes = tm.apply(deployArea, nodes);
            for (Node node : nodes) {
                node.getConfig().setSetRadioRange(simulation.getNodeRange());
                simulation.getNetwork().addNode((SensorNode) node);
            }
            SimulationController.getInstance().buildNetwork();
        } catch (Exception ex) {
            Logger.getLogger(PlatformEvaluationDeploy.class.getName()).log(Level.SEVERE, null, ex);
            GUI_Utils.showException(ex);
        }

    }

    private void deployGrid() {
        try {
            Simulation simulation = SimulationController.getInstance().getSimulation();
            Rectangle deployArea = new Rectangle(10, 10, w, h);
            int nNodesLine = (int) (deployArea.getWidth() / deployParam);
            int nNodesrow = (int) (deployArea.getHeight() / deployParam);
            int nNodes = nNodesLine * nNodesrow;
            NodeFactory nf = simulation.getNodeFactory();
            Vector<Node> nodes = (Vector<Node>) nf.createNodes(nNodes);
            GridTopologyManager tm = new GridTopologyManager();
            tm.setDistance(deployParam);
            nodes = tm.apply(deployArea, nodes);
            for (Node node : nodes) {
                node.getConfig().setSetRadioRange(simulation.getNodeRange());
                simulation.getNetwork().addNode((SensorNode) node);
            }
            SimulationController.getInstance().buildNetwork();
        } catch (Exception ex) {
            Logger.getLogger(PlatformEvaluationDeploy.class.getName()).log(Level.SEVERE, null, ex);
            GUI_Utils.showException(ex);
        }
    }
}
