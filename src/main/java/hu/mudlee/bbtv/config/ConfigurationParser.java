package hu.mudlee.bbtv.config;

import java.io.File;
import java.security.InvalidParameterException;

public class ConfigurationParser {
	public Configuration parse(String[] args) {
		Configuration configuration = new Configuration();

		for (String arg : args) {
			String[] keyValueArg = arg.split("=");
			KeyValue keyValue = new KeyValue(keyValueArg);

			if (keyValue.getKey().equals("-moviesPath")) {
				checkKeyValueHasValue(keyValue, arg);

				String moviesPath = fixSeparators(keyValue.getValue());
				if (moviesPath.endsWith(File.separator)) {
					moviesPath = moviesPath.substring(0, moviesPath.length() - 1);
				}
				configuration.setMoviesPath(new File(moviesPath));
			} else if (keyValue.getKey().equals("-programGuidePath")) {
				checkKeyValueHasValue(keyValue, arg);

				String programGuidePath = fixSeparators(keyValue.getValue());
				configuration.setProgramGuidePath(new File(programGuidePath));
			} else if (keyValue.getKey().equals("-debug")) {
				configuration.setDebug();
			} else {
				throw new InvalidParameterException("Invalid parameter '" + keyValue.getKey() + "'");
			}
		}

		return configuration;
	}

	private String fixSeparators(String value) {
		return value.replace("/", File.separator).replace("\\", File.separator);
	}

	private void checkKeyValueHasValue(KeyValue keyValue, String arg) {
		if (keyValue.getValue() == null) {
			throw new InvalidParameterException("Invalid parameter format '" + arg + "', you can use -moviesPath=c:\\movies");
		}
	}

	private class KeyValue {
		private String key = null;
		private String value = null;

		private KeyValue(String[] value) {
			this.key = value[0];
			if (value.length == 2) {
				this.value = value[1];
			}
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}
}
