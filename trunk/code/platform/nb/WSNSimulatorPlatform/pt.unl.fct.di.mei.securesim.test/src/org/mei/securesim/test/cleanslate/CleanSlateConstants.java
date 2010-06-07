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

    public final static int INVALID_TYPE = -1;
    public final static byte MSG_HELLO = 1;
    public final static byte MSG_MERGE_PROPOSAL_REQUEST = 2;
    public final static byte MSG_MERGE_PROPOSAL_REPLY = 3;
    public final static byte MSG_MERGE_PROPOSAL_ACK = 4;
    public final static byte MSG_MERGE_PROPOSAL_REFUSE = 5;
    public final static byte MSG_KNOWNED_GROUP_INFO = 6;
    public final static byte MSG_UPDATE_GROUPINFO = 7;
    public final static byte MSG_BROADCAST_MERGE_PROPOSAL_REFUSE = 8;
    public final static byte MSG_GROUP_NEIGHBORING_INFO = 9;
    /**
     * 
     */
    private static byte[] NAPrivateKey = CryptoFunctions.createSkipjackKey();
    private static byte[] NAPublicKey = CryptoFunctions.createSkipjackKey();
    static int ACTION_START = 0;
    static int EVENT_ACTION_BOUND_DISCOVERY = 1;
    static long NEIGHBOR_DISCOVERY_BOUND_TIME = Simulator.ONE_SECOND * 60;
    static long NEIGHBOR_DISCOVERY_REPEAT_TIME = Simulator.ONE_SECOND * 10;
    static double DELAYED_MESSAGE_BOUND = Simulator.ONE_SECOND * 1;
    static long COLLECT_NEIGHBOR_INFO_BOUND_TIME = Simulator.ONE_SECOND * 10;
    static long START_MERGE_BOUND_TIME = (long) (Simulator.ONE_SECOND * .2);
    static double GROUP_INFO_BROADCAST_REPEAT_TIME = (long) (Simulator.ONE_SECOND * 5);
    static long RESTART_GROUP_MERGE_BOUND_TIME = (long) (Simulator.ONE_SECOND * 3);
    static long FLOODING_NEIGHBOR_INFO_BOUND_TIME = (long) (Simulator.ONE_SECOND * 3);
    static long MERGE_WAITING_BOUND_TIME= (long) (Simulator.ONE_SECOND * 3);;

    public static byte[] getNAPrivateKey() {
        return NAPrivateKey;
    }

    public static byte[] getNAPublicKey() {
        return NAPublicKey;
    }
}
