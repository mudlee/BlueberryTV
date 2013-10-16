package hu.mudlee.bbtv.common;

public enum Day {
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;

	public static Day byDayOfWeek(int dayOfWeek) {
		switch (dayOfWeek) {
			case 1:
				return MONDAY;
			case 2:
				return TUESDAY;
			case 3:
				return WEDNESDAY;
			case 4:
				return THURSDAY;
			case 5:
				return FRIDAY;
			case 6:
				return SATURDAY;
			case 7:
				return SUNDAY;
			default:
				throw new IllegalStateException("Invalid day of week");
		}
	}

	public static Day byString(String day) {
		day = day.toLowerCase();

		if (day.equals("monday")) {
			return MONDAY;
		} else if (day.equals("tuesday")) {
			return TUESDAY;
		} else if (day.equals("wednesday")) {
			return WEDNESDAY;
		} else if (day.equals("thursday")) {
			return THURSDAY;
		} else if (day.equals("friday")) {
			return FRIDAY;
		} else if (day.equals("saturday")) {
			return SATURDAY;
		} else if (day.equals("sunday")) {
			return SUNDAY;
		}

		throw new IllegalStateException("Invalid day " + day);
	}

	public static int getDayOfWeek(Day day) {
		switch (day) {
			case MONDAY:
				return 1;
			case TUESDAY:
				return 2;
			case WEDNESDAY:
				return 3;
			case THURSDAY:
				return 4;
			case FRIDAY:
				return 5;
			case SATURDAY:
				return 6;
			case SUNDAY:
				return 7;
			default:
				throw new IllegalStateException("Invalid day " + day);
		}
	}
}
