package pt.unl.fct.di.mei.securesim.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public abstract class Configuration {
	protected XMLConfiguration config = new XMLConfiguration();
	protected String filename;
	protected boolean loaded;

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param loaded
	 *            the loaded to set
	 */
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	/**
	 * @return the loaded
	 */
	public boolean isLoaded() {
		return loaded;
	}

	public void loadFromFile(String filename) throws BaseConfigurationException {
		try {
			this.config.load(filename);
			this.filename = filename;
			this.loaded = true;
			this.init();
		} catch (ConfigurationException e) {
			throw new BaseConfigurationException(e);
		}
	}
	/**
	 *  Este método é invocado apos carregamento do ficheiro
	 *  as classes derivadas devem implementar para afectar
	 *  as configurações a propriedades que estejam expostas
	 *  como publicas
	 */
	protected abstract void init() ;
}
