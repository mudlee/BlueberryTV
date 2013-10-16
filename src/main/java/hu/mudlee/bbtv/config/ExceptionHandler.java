package hu.mudlee.bbtv.config;

import hu.mudlee.bbtv.common.BlueberryException;
import org.apache.log4j.Logger;

public class ExceptionHandler {
	private final Logger logger = Logger.getLogger(ExceptionHandler.class);

	public void handle(Exception e, Configuration configuration) {
		if (configuration.isDebug()) {
			throw new BlueberryException(e);
		} else {
			logger.error(e.getMessage());
		}
	}
}
