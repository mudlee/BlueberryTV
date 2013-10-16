package hu.mudlee.bbtv.movie;

import org.joda.time.DateTime;

public class NoSignalMovieCreator {
	private static Movie signal = null;

	public Movie create() {
		if (signal == null) {
			String path = "system/video/no_signal.mp4";
			MovieDurationProvider movieDurationProvider = new MovieDurationProvider(path);

			signal = new Movie(
					path,
					DateTime.now(),
					movieDurationProvider.getDuration(),
					MovieType.LOCAL_MEDIA
			);
		}

		return signal;
	}
}
