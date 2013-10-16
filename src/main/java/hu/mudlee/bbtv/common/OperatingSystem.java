package hu.mudlee.bbtv.common;

public enum OperatingSystem {
	WINDOWS,
	LINUX;

	public static OperatingSystem byString(String string) {
		if (string.contains("Linux")) {
			return LINUX;
		} else if (string.contains("Windows")) {
			return WINDOWS;
		}

		throw new IllegalStateException("Invalid OperatingSystem '" + string + "'");
	}
}
