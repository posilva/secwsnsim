/**
 * 
 */
package org.mei.securesim.utils;

import java.util.Random;

/**
 * @author posilva
 *
 */
public class RandomGenerator {

    protected Random random = null;
    private long seed;

    public RandomGenerator(long seed) {
        this.seed = seed;
        init();
    }

    public RandomGenerator() {
        this.seed = 1024;// System.currentTimeMillis();
        init();
    }

    private void init() {
        random = new Random(seed);
    }

    public Random random() {
        return random;
    }
    public void reset() {
        init();
    }

    public void reset(long seed) {
        this.seed = seed;
        init();
    }
    public double nextDoubleBetween(int min, int max){
        return min + (double)(random.nextDouble() * ((max - min) + 1));
    }
    public int nextDoubleInt(int min, int max){
        return min + (int)(random.nextInt() * ((max - min) + 1));
    }
    public long nextDoubleLong(int min, int max){
        return min + (long)(random.nextLong() * ((max - min) + 1));
    }


}
