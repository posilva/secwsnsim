/**
 * 
 */
package pt.unl.fct.di.mei.securesim.configuration;

/**
 * @author posilva
 * 
 */
public abstract class ConfigurableObject {

	protected Configuration configuration = null;

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	protected abstract void init();

}
