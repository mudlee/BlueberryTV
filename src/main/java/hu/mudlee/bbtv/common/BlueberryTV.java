package hu.mudlee.bbtv.common;

import hu.mudlee.bbtv.config.Configuration;
import hu.mudlee.bbtv.movie.MoviePlayer;
import hu.mudlee.bbtv.programguide.ProgramGuide;
import hu.mudlee.bbtv.programguide.ProgramGuideParser;
import org.apache.log4j.Logger;

public class BlueberryTV {
	private final Logger logger = Logger.getLogger(BlueberryTV.class);
	private final Configuration configuration;

	public BlueberryTV(Configuration configuration) {
		this.configuration = configuration;
		this.configuration.validate();
	}

	public void turnOn() {
		logger.info("Turning on...");
		ProgramGuideParser programGuideParser = new ProgramGuideParser(
				configuration.getMoviesPath(),
				configuration.getProgramGuidePath()
		);

		logger.info("Preparing program guide...");
		ProgramGuide programGuide = programGuideParser.parse();
		programGuide.validate();

		logger.info("Starting movieplayer...");
		MoviePlayer moviePlayer = new MoviePlayer(programGuide);
		moviePlayer.setUp();
		moviePlayer.searchForNextMovie();
	}
}
