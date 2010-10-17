/**
 * 
 */
package org.wisenet.simulator.utilities;

import java.util.Random;

/**
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 *
 */
public class RandomGenerator {

    /**
     *
     */
    protected Random random = null;
    private long seed;

    /**
     *
     * @param seed
     */
    public RandomGenerator(long seed) {
        this.seed = seed;
        init();
    }

    /**
     *
     */
    public RandomGenerator() {
        this.seed = 1024;// System.currentTimeMillis();
        init();
    }

    private void init() {
        random = new Random(seed);
    }

    /**
     *
     * @return
     */
    public Random random() {
        return random;
    }
    /**
     *
     */
    public void reset() {
        init();
    }

    /**
     *
     * @param seed
     */
    public void reset(long seed) {
        this.seed = seed;
        init();
    }
    /**
     *
     * @param min
     * @param max
     * @return
     */
    public double nextDoubleBetween(int min, int max){
        return min + (double)(random.nextDouble() * ((max - min) + 1));
    }
    /**
     *
     * @param min
     * @param max
     * @return
     */
    public int nextDoubleInt(int min, int max){
        return min + (int)(random.nextInt() * ((max - min) + 1));
    }
    /**
     *
     * @param min
     * @param max
     * @return
     */
    public long nextDoubleLong(int min, int max){
        return min + (long)(random.nextLong() * ((max - min) + 1));
    }


}
