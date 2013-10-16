package hu.mudlee.bbtv.movie;

import hu.mudlee.bbtv.programguide.NoScheduledMovieAvailableException;
import hu.mudlee.bbtv.programguide.ProgramGuide;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MoviePlayer {
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private EmbeddedMediaPlayer mediaPlayer;
	private final ProgramGuide programGuide;
	private final NoSignalBackgroundTask noSignalBackgroundTask;
	private final Logger logger = Logger.getLogger(MoviePlayer.class);
	private boolean playingNoSignal = false;

	public MoviePlayer(ProgramGuide programGuide) {
		this.programGuide = programGuide;
		this.noSignalBackgroundTask = new NoSignalBackgroundTask();
		noSignalBackgroundTask.timeToTrySearchForNextVideo(new Runnable() {
			@Override
			public void run() {
				searchForNextMovie();
			}
		});
	}

	public void setUp() {
		JFrame frame = new JFrame("Blueberry TV");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		mediaPlayer = mediaPlayerComponent.getMediaPlayer();

		frame.setContentPane(mediaPlayerComponent);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
			}
		});

		frame.setVisible(true);
		mediaPlayer.setRepeat(false);

		MoviePlayerEventListener eventListener = new MoviePlayerEventListener();
		eventListener.whenFinished(new Runnable() {
			@Override
			public void run() {
				searchForNextMovie();
			}
		});

		mediaPlayer.addMediaPlayerEventListener(eventListener);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				mediaPlayer.release();
				mediaPlayerComponent.release();
				logger.info("TV shutdown");
			}
		});
	}

	public void searchForNextMovie() {
		String now = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss").print(DateTime.now());

		try {
			logger.info("Searching for next movie. Current time is '" + now + "'");
			Movie movie = programGuide.getCurrentMovie();
			playMovie(movie);
		} catch (NoScheduledMovieAvailableException e) {
			logger.info("No scheduled movie has found.");
			playNoSignal();
		} catch (MovieHasEndedException e) {
			logger.info("Movie has ended.");
			searchForNextMovie();
		}
	}

	private void playMovie(Movie movie) throws MovieHasEndedException {
		noSignalBackgroundTask.kill();
		playingNoSignal = false;

		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}

		logger.info("Playing movie '" + movie.getPath() + "'");
		mediaPlayer.playMedia(movie.getPath());

		float position = movie.getCurrentPosition();
		logger.info("Seeking to " + position + "%");
		mediaPlayer.setPosition(position);
	}

	private void playNoSignal() {
		if (playingNoSignal) {
			logger.info("# still no signal");
		} else {
			logger.info("Starting to play no signal.");
			NoSignalMovieCreator noSignalMovieCreator = new NoSignalMovieCreator();
			Movie noSignal = noSignalMovieCreator.create();

			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}

			mediaPlayer.playMedia(noSignal.getPath());
			playingNoSignal = true;
		}

		if (!noSignalBackgroundTask.isRunning()) {
			noSignalBackgroundTask.start();
		}
	}
}
