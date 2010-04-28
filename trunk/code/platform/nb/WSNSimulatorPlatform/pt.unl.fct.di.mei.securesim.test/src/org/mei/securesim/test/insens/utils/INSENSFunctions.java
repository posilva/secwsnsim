/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens.utils;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.mei.securesim.core.engine.Simulator;

/**
 *
 * @author POSilva
 */
public class INSENSFunctions {

    protected static boolean initiated = false;
    protected static Map KEY_MAP = new HashMap();

    public static long oneWayHash(long number) {
        return JSHash(String.valueOf(number));
    }

    private static long JSHash(String str) {
        long hash = 1315423911;
        for (int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }
        return hash;
    }

    public static void createOneWayFunctionSequenceNumber() {
    }

    public static void initINSENS(Simulator simulator) {
        createInicialOWSArray();
        distributeKeys(simulator.getNodes().size());
        initiated = true;
    }

    public static boolean isInitiated() {
        return initiated;
    }

    private static void createInicialOWSArray() {
        Random r = new Random();

        INSENSConstants.OWSArray = new long[INSENSConstants.OWSARRAY_SIZE];
        INSENSConstants.OWSArray[0] = INSENSFunctions.oneWayHash(r.nextLong());

        for (int i = 1; i < INSENSConstants.OWSArray.length; i++) {
            INSENSConstants.OWSArray[i] = INSENSFunctions.oneWayHash(INSENSConstants.OWSArray[i - 1]);
        }

    }

    private static void distributeKeys(int size) {
        INSENSConstants.KEY_SET = new Key[size];

    }

    public static void shareKeyWithBaseStation(int id, Key key) {
        KEY_MAP.put(id, key);

    }

    public static Key getShareKey(int id) {
        return (Key) KEY_MAP.get(id);
    }

    public static long getNextOWS(int roundNumber) {
        return 0;
    }
}
