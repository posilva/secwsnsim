/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens;

import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.basic.Mica2SensorNode;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.gui.IDisplayable;

/**
 *
 * @author pedro
 */
public class INSENSNode extends Mica2SensorNode implements IDisplayable {

    public INSENSNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
    }

}
