package org.mei.securesim.test.insens.utils;

import java.security.Key;
import java.util.Random;
import org.mei.securesim.core.engine.Simulator;

/**
 *
 * @author posilva
 */
public class INSENSConstants {

    public static final int MSG_RREQ = 0;
    public static final int MSG_FDBK = 1;
    public static final int MSG_RUPD = 2;
    public static final int MSG_APP = 3;
    public static final int MSG_DATA = 4;
    public static int ACTION_START = 1;
    public static long INITIAL_NK = (new Random()).nextInt();

    public  static int OWSARRAY_SIZE = 100;
    public static long[] OWSArray;
    public static Key[] KEY_SET;
    public static final long FEEDBACK_WAITING_TIME=30*Simulator.ONE_SECOND;

    public static final long MAX_DELAY_TIME_MESSAGE=(long) (Simulator.ONE_SECOND*6);
    public static final long MIN_DELAY_TIME_MESSAGE=(long) (Simulator.ONE_SECOND*3);

    static int ows=0;
    public static long getNextOWS() {
        return ows++;
    }
}
