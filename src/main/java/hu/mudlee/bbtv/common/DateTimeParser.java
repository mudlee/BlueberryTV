package hu.mudlee.bbtv.common;

import org.joda.time.DateTime;

public class DateTimeParser {
	public static DateTime parse(int dayOfWeek, String start) {
		String[] fromParts = start.split(":");
		if (fromParts.length != 2) {
			throw new IllegalStateException("Invalid date string '" + start + "'");
		}

		int fromHour = Integer.valueOf(fromParts[0]);
		int fromMinute = Integer.valueOf(fromParts[1]);

		return DateTime.now().withDayOfWeek(dayOfWeek).withTime(fromHour, fromMinute, 0, 0);
	}
}
