/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.test.insens.utils;

import java.security.Key;
import java.util.Arrays;
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
    protected static Map MACKEY_MAP = new HashMap();
    private static long ows=0;

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

    public static void shareKeyWithBaseStation(int id, byte[] key) {
        KEY_MAP.put(id, key);

    }
     public static void shareMACKeyWithBaseStation(int id, byte[] key) {
        MACKEY_MAP.put(id, key);

    }
    public static void debug(Object msg ){
        System.out.println(msg);
    } 
    public static byte[] getShareKey(int id) {
        return (byte[]) KEY_MAP.get(id);
    }
    public static byte[] getShareMacKey(int id) {
        return (byte[]) MACKEY_MAP.get(id);
    }

    public static long getNextOWS(int roundNumber) {
        return ows++;
    }

    public static byte []cloneMAC (byte[] a ){

        if (a==null) return null;
        return Arrays.copyOf(a, a.length) ;
    }

     private static String	digits = "0123456789abcdef";

    /**
     * Retorna string hexadecimal a partir de um byte array de certo tamanho
     *
     * @param data : bytes a coverter
     * @param length : numero de bytes no bloco de dados a serem convertidos.
     * @return  hex : representacaop em hexadecimal dos dados
     */

   public static String toHex(byte[] data, int length)
    {
        StringBuffer	buf = new StringBuffer();

        for (int i = 0; i != length; i++)
        {
            int	v = data[i] & 0xff;

            buf.append(digits.charAt(v >> 4));
            buf.append(digits.charAt(v & 0xf));
        }

        return buf.toString();
    }

    /**
     * Retorna dados passados como byte array numa string hexadecimal
     *
     * @param data : bytes a serem convertidos
     * @return : representacao hexadecimal dos dados.
     */
    public static String toHex(byte[] data)
    {
        return toHex(data, data.length);
    }
}
