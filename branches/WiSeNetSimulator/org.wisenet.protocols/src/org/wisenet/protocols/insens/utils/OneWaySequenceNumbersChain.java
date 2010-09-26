/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.utils;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class OneWaySequenceNumbersChain extends Vector<Long> {

    private int chainSize;
    private final Iterator<Long> chainIterator;

    public OneWaySequenceNumbersChain(int chainSize) {
        this.chainSize = chainSize;
        createSequence(chainSize);
        chainIterator = this.iterator();
        if (chainIterator.hasNext()) {
            chainIterator.next();
        }
    }

    /**
     * Gets the next number from the one way hash sequence
     * @param number
     * @return
     */
    protected static long nextOneWayHash(long number) {
        return JSHash(String.valueOf(number));
    }

    /**
     * Hash function for create one-away hash numbers
     * @param str
     * @return
     */
    private static long JSHash(String str) {
        long hash = 1315423911;
        for (int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }
        return hash;
    }

    /**
     * Creates a sequence initiated by Start with the size indicated
     * @param start
     * @param size
     */
    protected final void createSequence(int size) {
        long next = new Random(System.currentTimeMillis()).nextLong();
        Vector<Long> v = new Vector<Long>();
        v.add(next);
        for (int i = 0; i < size; i++) {
            next = nextOneWayHash(next);
            v.add(next);
        }
        for (int i = v.size(); i > 0; i--) {
            add(v.get(i - 1));
        }
    }

    /**
     * Gets the size of this sequence number chain
     * @return
     */
    public int getChainSize() {
        return chainSize;
    }

    /**
     * Gets the next sequence number in the chain
     * @return
     */
    public long getNextSequenceNumber() {
        return chainIterator.next();

    }

    /**
     * Verifies if the current ows is obtain from the next
     * @param current
     * @param next
     * @return
     */
    public static boolean verifySequenceNumber(long current, long next) {
        return nextOneWayHash(next) == current;
    }
}
