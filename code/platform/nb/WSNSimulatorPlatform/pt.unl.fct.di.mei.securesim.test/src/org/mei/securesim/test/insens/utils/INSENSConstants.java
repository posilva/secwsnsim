package org.mei.securesim.test.insens.utils;

import java.security.Key;
import java.util.Random;
import org.mei.securesim.core.engine.Simulator;

/**
 *
 * @author posilva
 */
public class INSENSConstants {
    public static final short BYTE_SIZE = 1;

    public static final short SHORT_SIZE = 2;
    public static final short INT_SIZE = 4;
    public static final short LONG_SIZE = 8;

    public static final byte MSG_RREQ = 0;
    public static final byte MSG_FDBK = 1;
    public static final byte MSG_RUPD = 2;
    public static final byte MSG_APP = 3;
    public static final byte MSG_DATA = 4;
    public static int ACTION_START = 1;
    public static long INITIAL_NK = (new Random()).nextInt();

    public  static int OWSARRAY_SIZE = 100;
    public static long[] OWSArray;
    public static Key[] KEY_SET;
    public static final long FEEDBACK_WAITING_TIME=30*Simulator.ONE_SECOND;

    public static final int MAX_DELAY_TIME_MESSAGE= (Simulator.ONE_SECOND*30);
    public static final int MIN_DELAY_TIME_MESSAGE= (Simulator.ONE_SECOND*1);

    static int ows=0;
    public static byte INVALID_TYPE=-1;
    public static int ACTION_BUILD=2;
    public static long getNextOWS() {
        return ows++;
    }
}
