package hu.mudlee.bbtv.programguide;

import hu.mudlee.bbtv.common.Day;
import hu.mudlee.bbtv.movie.Movie;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProgramGuide {
	private TreeMap<Day, List<Movie>> programsByDay;

	public ProgramGuide(TreeMap<Day, List<Movie>> programsByDay) {
		this.programsByDay = programsByDay;
	}

	public void validate() {
		for (Map.Entry<Day, List<Movie>> entry : programsByDay.entrySet()) {
			Day day = entry.getKey();
			List<Movie> movies = entry.getValue();

			if (movies.isEmpty()) {
				continue;
			}

			validateADay(movies);
		}
	}

	public Movie getCurrentMovie() throws NoScheduledMovieAvailableException {
		DateTime now = DateTime.now();
		Day currentDay = Day.byDayOfWeek(now.getDayOfWeek());
		List<Movie> todayShow = programsByDay.get(currentDay);

		for (Movie movie : todayShow) {
			if (movie.getStart().isBefore(now) && movie.getEnd().isAfter(now)) {
				return movie;
			}
		}

		throw new NoScheduledMovieAvailableException();
	}

	private void validateADay(List<Movie> movies) {
		for (int i = 0; i < movies.size(); i++) {
			if ((i + 1) > (movies.size() - 1)) {
				return; // TODO: Last item in the list, we should check the next day's first program
			}

			Movie movie = movies.get(i);
			Movie nextMovie = movies.get(i + 1);

			if (movie.getEnd().isAfter(nextMovie.getStart())) {
				throw new ProgramGuideException(
						MessageFormat.format("Program \"{0}\" is not valid, because it''s too close to the next movie \"{1}\". First movie starts at {2} and the duration is {3}sec",
								movie.getPath(),
								nextMovie.getPath(),
								DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").print(movie.getStart()),
								movie.getDuration() / 1000
						)
				);
			}
		}
	}
}
