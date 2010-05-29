/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.cleanslate;

import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.core.engine.Simulator;

/**
 *
 * @author CIAdmin
 */
class CleanSlateConstants {

    public final static int INVALID_TYPE = -1;
    public final static byte MSG_HELLO = 1;
    public final static byte MSG_GROUP_PROPOSAL = 2;
    public final static byte MSG_KNOWNED_GROUP_INFO = 3;
    public static byte MSG_UPDATE_GROUPINFO=4;
    /**
     * 
     */
    private static byte[] NAPrivateKey = CryptoFunctions.createSkipjackKey();
    private static byte[] NAPublicKey = CryptoFunctions.createSkipjackKey();
    static int ACTION_START = 0;
    static int EVENT_ACTION_BOUND_DISCOVERY = 1;
    static long NEIGHBOR_DISCOVERY_BOUND_TIME = Simulator.ONE_SECOND * 30;

    public static byte[] getNAPrivateKey() {
        return NAPrivateKey;
    }

    public static byte[] getNAPublicKey() {
        return NAPublicKey;
    }
}
