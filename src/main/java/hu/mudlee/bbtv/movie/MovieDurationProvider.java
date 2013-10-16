package hu.mudlee.bbtv.movie;

import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IContainer;
import org.apache.log4j.Logger;

public class MovieDurationProvider {
	private final String movie;
	private final Logger logger = Logger.getLogger(MovieDurationProvider.class);

	public MovieDurationProvider(String movie) {
		this.movie = movie;
	}

	public long getDuration() {
		logger.info("Getting duration of '" + movie + "'");

		IContainer container = IContainer.make();
		if (container.open(movie, IContainer.Type.READ, null) < 0) {
			throw new RuntimeException("Cannot open '" + movie + "'");
		}

		logger.info("# Duration (ms): " + ((container.getDuration() == Global.NO_PTS) ? "unknown" : "" + container.getDuration() / 100));
		return container.getDuration() / 1000;
	}
}
