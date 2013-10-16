package hu.mudlee.bbtv.movie;

import com.github.axet.vget.VGet;
import com.github.axet.vget.info.VideoInfo;
import com.github.axet.wget.info.DownloadInfo;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThirdPartySourceDownloader {
	private final Logger logger = Logger.getLogger(ThirdPartySourceDownloader.class);
	private final File moviesPath;
	private VideoInfo info;
	private int lastPercent;

	public ThirdPartySourceDownloader(File moviesPath) {
		this.moviesPath = moviesPath;
	}

	public File downloadSource(String movieUrl) {
		logger.info("Downloading source from '" + movieUrl + "'");

		try {
			AtomicBoolean stop = new AtomicBoolean(false);
			Runnable notify = new Runnable() {
				@Override
				public void run() {
					VideoInfo videoInfo = info;
					DownloadInfo downloadInfo = videoInfo.getInfo();

					switch (videoInfo.getState()) {
						case EXTRACTING:
							logger.info("Extracting...");
							break;
						case EXTRACTING_DONE:
							logger.info("Extracting done.");
							break;
						case DONE:
							logger.info("Downloading done.");
							break;
						case RETRYING:
							logger.info("Retrying...");
							break;
						case DOWNLOADING:
							float percent = downloadInfo.getCount() / (float) downloadInfo.getLength();
							int intPercent = Math.round(percent * 100);
							if (intPercent % 5 != 0 || lastPercent == intPercent) {
								break;
							}
							lastPercent = intPercent;
							logger.info("# ... " + intPercent + "%");
						default:
							break;
					}
				}
			};

			info = new VideoInfo(new URL(movieUrl));

			String path = moviesPath.getPath() + File.separator;
			VGet vGet = new VGet(info, new File(path));
			vGet.download(stop, notify);

			File downloadedMovie = vGet.getTarget();
			logger.info("Movie saved to '" + downloadedMovie.getPath() + "'");
			return downloadedMovie;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
