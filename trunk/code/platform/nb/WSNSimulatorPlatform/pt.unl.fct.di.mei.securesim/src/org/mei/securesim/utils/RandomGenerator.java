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
        this.seed = System.currentTimeMillis();
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
}
