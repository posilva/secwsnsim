
package org.mei.securesim.test.insens;

import java.util.Random;

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
    protected static boolean initiated = false;
    private static int OWSARRAY_SIZE = 100;

    public static void createOneWayFunctionSequenceNumber() {
    }

    public static long JSHash(String str) {
        long hash = 1315423911;
        for (int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }
        return hash;
    }

    public static void initINSENS() {
        createInicialOWSArray();

        initiated = true;
    }

    public static boolean isInitiated() {
        return initiated;
    }

    private static void createInicialOWSArray() {
        Random r = new Random();

        long OWSArray[] = new long[OWSARRAY_SIZE];
        OWSArray[0] = JSHash("" + r.nextLong());

        for (int i = 1; i < OWSArray.length; i++) {
            OWSArray[i] = JSHash("" + OWSArray[i - 1]);
        }

    }

    static long getNextOWS() {
        return 0;
    }
}
