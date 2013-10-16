package hu.mudlee.bbtv.movie;

import org.apache.log4j.Logger;

public class NoSignalBackgroundTask {
	private boolean stopped = false;
	private Thread thread;
	private final Logger logger = Logger.getLogger(NoSignalMovieCreator.class);
	private Runnable timeToTrySeachForNextVideo;
	private boolean running = false;

	public void timeToTrySearchForNextVideo(Runnable runnable) {
		timeToTrySeachForNextVideo = runnable;
	}

	public void start() {
		stopped = false;
		running = true;

		logger.info("Starting no signal task");

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(1500);
						if (stopped) {
							return;
						}

						timeToTrySeachForNextVideo.run();
					}
				} catch (InterruptedException e) {
					return;
				}
			}
		});

		thread.start();
	}

	public void kill() {
		running = false;

		if (thread == null) {
			return;
		}

		logger.info("Stopping no signal task");
		stopped = true;
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {

		}
	}

	public boolean isRunning() {
		return running;
	}
}
