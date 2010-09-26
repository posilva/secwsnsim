/**
 * 
 */
package org.wisenet.simulator.utilities;

/**
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 *
 */
public class RandomGeneratorNotInitiatedException extends Exception {

	/**
	 * 
	 */
	public RandomGeneratorNotInitiatedException() {
		
	}

	/**
	 * @param arg0
	 */
	public RandomGeneratorNotInitiatedException(String arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 */
	public RandomGeneratorNotInitiatedException(Throwable arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RandomGeneratorNotInitiatedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
