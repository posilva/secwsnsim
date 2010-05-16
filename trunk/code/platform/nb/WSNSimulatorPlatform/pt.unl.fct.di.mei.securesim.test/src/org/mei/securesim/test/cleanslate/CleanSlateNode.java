/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate;

import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.basic.Mica2SensorNode;
import org.mei.securesim.core.radio.RadioModel;

/**
 *
 * @author CIAdmin
 */
public class CleanSlateNode extends Mica2SensorNode {

    private byte[] networkKey = null;
    private byte[] NAKey = null;

    public CleanSlateNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
    }

    @Override
    public void init() {
        super.init();
        networkKey = CryptoFunctions.createSkipjackKey();
        NAKey = CleanSlateConstants.getNAKey();
    }

    public byte[] getNAKey() {
        return NAKey;
    }

    public byte[] getNetworkKey() {
        return networkKey;
    }

}
