package hu.mudlee.bbtv.movie;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class Movie {
	private String path;
	private DateTime start;
	private long duration;
	private MovieType type;

	public Movie(String path, DateTime start, long duration, MovieType type) {
		this.path = path;
		this.start = start;
		this.duration = duration;
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public DateTime getStart() {
		return start;
	}

	public long getDuration() {
		return duration;
	}

	public float getCurrentPosition() throws MovieHasEndedException {
		DateTime now = DateTime.now();
		Seconds seconds = Seconds.secondsBetween(start, now);
		int betweenSeconds = seconds.getSeconds();

		int durationInSec = (int) duration / 1000;
		if (seconds.getSeconds() > durationInSec) {
			throw new MovieHasEndedException();
		}

		return (float) betweenSeconds / durationInSec;
	}

	public DateTime getEnd() {
		int sec = (int) duration / 1000;
		return start.plusSeconds(sec);
	}

	public boolean isThirdPartySource() {
		return type == MovieType.YOUTUBE_URL || type == MovieType.VIMEO_URL;
	}
}