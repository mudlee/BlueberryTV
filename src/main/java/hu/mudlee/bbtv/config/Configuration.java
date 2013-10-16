package hu.mudlee.bbtv.config;

import org.apache.log4j.Logger;

import java.io.File;

public class Configuration {
	private final Logger logger = Logger.getLogger(Configuration.class);
	private File moviesPath;
	private File programGuidePath;
	private boolean debug = false;

	public void validate() {
		if (moviesPath == null) {
			throw new ConfigurationException("Missin parameter '-moviesPath'");
		}
		if (programGuidePath == null) {
			throw new ConfigurationException("Missing parameter '-programGuidePath'");
		}

		if (!moviesPath.isDirectory()) {
			throw new ConfigurationException("Movies path doesn't exists '" + moviesPath.getPath() + "'");
		}

		if (!programGuidePath.isFile()) {
			throw new ConfigurationException("Couldn't found programguide at '" + programGuidePath.getPath() + "'");
		}

		logger.info("Configuration is valid.");
	}

	public File getMoviesPath() {
		return moviesPath;
	}

	public File getProgramGuidePath() {
		return programGuidePath;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug() {
		this.debug = true;
	}

	public void setMoviesPath(File moviesPath) {
		this.moviesPath = moviesPath;
	}

	public void setProgramGuidePath(File programGuidePath) {
		this.programGuidePath = programGuidePath;
	}
}
