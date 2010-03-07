/**
 * 
 */
package org.mei.securesim.platform.utils;

import java.util.Random;

/**
 * @author posilva
 *
 */
public class RandomGenerator {
	
	
	static Random random=null; 
	
	static boolean initiated = false;
	private static void init(long seed) {
		random = new Random(seed);
		initiated =true;
	}
	private static void init() {
		random = new Random();
		initiated =true;
	}

	public static Random random()  {
		if (!initiated ) init();
		return random;
	}
}
