/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate;

import java.awt.Color;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.basic.Mica2SensorNode;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.core.ui.ISimulationDisplay;

/**
 *
 * @author CIAdmin
 */
public class CleanSlateNode extends Mica2SensorNode {

    private byte[] networkKey = null;
    private byte[] NAPublicKey = null;

    public CleanSlateNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
    }

    @Override
    public void init() {
        if (getId() == 1) {
            setSinkNode(true);
        }
        getGraphicNode().setBackcolor(Color.BLACK);
        getGraphicNode().setMarkColor(Color.RED);
        super.init();
        networkKey = CryptoFunctions.createSkipjackKey();
        NAPublicKey = CleanSlateConstants.getNAPrivateKey();
    }

    public byte[] getNAKey() {
        return NAPublicKey;
    }

    public byte[] getNetworkKey() {
        return networkKey;
    }

    @Override
    public void displayOn(ISimulationDisplay disp) {
        super.displayOn(disp);
        if (isSinkNode()) {
            getGraphicNode().mark();
        }
    }

}
