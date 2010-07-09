/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens;

import org.mei.securesim.core.engine.Simulator;

/**
 *
 * @author CIAdmin
 */
public class INSENSConstants {

    public static final long FEEDBACK_START_TIME_BOUND = Simulator.ONE_SECOND * 60;
    public static final long FEEDBACK_MSG_RECEIVED_TIME_BOUND = Simulator.ONE_SECOND * 30;
    public static final byte FEEDBACK_MSG_RECEIVED_RETRIES = 3;
    public static final long MESSAGE_DISPATCH_RATE = Simulator.ONE_SECOND * 3;
    public static final int CHAIN_SIZE = 100;
    public static double MAX_DELAYED_MESSAGE_BOUND = Simulator.ONE_SECOND * 3;
    public static double MIN_DELAYED_MESSAGE_BOUND = 2000;
    /**
     * Messages types
     */
    public static final byte MSG_ROUTE_REQUEST = 1;
    public static final byte MSG_FEEDBACK = 2;
    public static final byte MSG_ROUTE_UPDATE = 3;
    public static final byte MSG_DATA = 4;
    public static final byte MSG_ROUTE_UPDATE_ACK = 5;
    /**
     * Helpers
     */
    public static double SIGNAL_STRENGH_THRESHOLD = 0.5;
    public static double SIGNAL_NOISE_THRESHOLD = 1000.0;
}
