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
public class CleanSlateConstants {

    public static final byte DEBUG_LEVEL_NONE = 0, DEBUG_LEVEL_NORMAL = 1, DEBUG_LEVEL_FINE = 2, DEBUG_LEVEL_FINNEST = 3, DEBUG_LEVEL_ALL = 9;
    public static final int DISCOVERY_MESSAGES_INTERVAL = 20000;
    public static final int MAX_DISCOVERY_MESSAGES_SENT = 3;
    public final static int INVALID_TYPE = -1;
    public final static byte MSG_HELLO = 1;
    public final static byte MSG_MERGE_PROPOSAL = 2;
    public final static byte MSG_MERGE_PROPOSAL_REFUSE = 3;
    public final static byte MSG_MERGE_PROPOSAL_AGREEMENT = 4;
    public final static byte MSG_POST_MERGE = 5;
    public final static byte MSG_NEIGHBOR_GROUP_ANNOUNCE = 6;
    /**
     * 
     */
    private static byte[] NAPrivateKey = CryptoFunctions.createSkipjackKey();
    private static byte[] NAPublicKey = CryptoFunctions.createSkipjackKey();
    static int ACTION_START = 0;
    static long NEIGHBOR_DISCOVERY_BOUND_TIME = Simulator.ONE_SECOND * 60;
    static double MAX_DELAYED_MESSAGE_BOUND = Simulator.ONE_SECOND * 3;
    static double MIN_DELAYED_MESSAGE_BOUND = 2000;
    static double NEIGHBOR_SIGNAL_THRESHOLD = 0.0;
    static short BOOT_STATE = 0;
    static short NEIGHBOR_DISCOVERY_STATE = 1;
    static short MERGE_PROPOSAL_STATE = 2;
    static short MERGE_WAITING_STATE = 3;

    public static byte[] getNAPrivateKey() {
        return NAPrivateKey;
    }

    public static byte[] getNAPublicKey() {
        return NAPublicKey;
    }
}
