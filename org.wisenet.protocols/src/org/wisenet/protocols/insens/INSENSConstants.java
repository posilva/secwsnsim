/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens;

import org.wisenet.simulator.utilities.CryptoFunctions;
import org.wisenet.simulator.core.Simulator;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class INSENSConstants {

    /**
     *
     */
    public static final long FEEDBACK_START_TIME_BOUND = Simulator.ONE_SECOND * 60;

    /**
     *
     */
    public static final long FEEDBACK_MSG_RECEIVED_TIME_BOUND = Simulator.ONE_SECOND * 30;

    /**
     *
     */
    public static final byte FEEDBACK_MSG_RECEIVED_RETRIES = 3;

    /**
     *
     */
    public static final long MESSAGE_DISPATCH_RATE =(long)( Simulator.ONE_SECOND * 1);

    /**
     *
     */
    public static final int CHAIN_SIZE = 100;

    /**
     *
     */
    public static double MAX_DELAYED_MESSAGE_BOUND = Simulator.ONE_SECOND * 1;

    /**
     *
     */
    public static double MIN_DELAYED_MESSAGE_BOUND = 2000;
    
    private static final int IV_COUNTER = 128219;
    /**
     *
     */
    public static byte[] globalIV = CryptoFunctions.createIV(IV_COUNTER);
    /**
     * Messages types
     */
    public static final byte MSG_ROUTE_REQUEST = 1;
    /**
     *
     */
    public static final byte MSG_FEEDBACK = 2;
    /**
     *
     */
    public static final byte MSG_ROUTE_UPDATE = 3;

    /**
     *
     */
    public static final byte MSG_DATA = 4;

    /**
     *
     */
    public static final byte MSG_ROUTE_UPDATE_ACK = 5;
    /**
     * Helpers
     */
    public static double SIGNAL_STRENGH_THRESHOLD = 0.5; // melhor .5
    /**
     * 
     */
    public static double SIGNAL_NOISE_THRESHOLD = 1000.0;
}
