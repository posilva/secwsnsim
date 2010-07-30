/**
 * 
 */
package org.wisenet.simulator.components.configuration;

/**
 * @author posilva
 *
 */
public class BaseConfigurationException extends Exception {

	/**
	 * 
	 */
	public BaseConfigurationException() {
		
	}

	/**
	 * @param arg0
	 */
	public BaseConfigurationException(String arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 */
	public BaseConfigurationException(Throwable arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BaseConfigurationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

}
