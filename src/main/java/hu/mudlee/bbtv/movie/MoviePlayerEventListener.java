package hu.mudlee.bbtv.movie;

import org.apache.log4j.Logger;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

public class MoviePlayerEventListener implements MediaPlayerEventListener {
	private final Logger logger = Logger.getLogger(MediaPlayerEventListener.class);
	private Runnable whenFinished;

	public void whenFinished(Runnable runnable) {
		whenFinished = runnable;
	}

	@Override
	public void finished(MediaPlayer mediaPlayer) {
		logger.info("'" + mediaPlayer.getMediaMeta().getTitle() + "' has finished.");
		whenFinished.run();
	}

	@Override
	public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t media, String mrl) {

	}

	@Override
	public void opening(MediaPlayer mediaPlayer) {
	}

	@Override
	public void buffering(MediaPlayer mediaPlayer, float newCache) {
	}

	@Override
	public void playing(MediaPlayer mediaPlayer) {
	}

	@Override
	public void paused(MediaPlayer mediaPlayer) {
	}

	@Override
	public void stopped(MediaPlayer mediaPlayer) {
	}

	@Override
	public void forward(MediaPlayer mediaPlayer) {
	}

	@Override
	public void backward(MediaPlayer mediaPlayer) {
	}

	@Override
	public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
	}

	@Override
	public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
	}

	@Override
	public void seekableChanged(MediaPlayer mediaPlayer, int newSeekable) {
	}

	@Override
	public void pausableChanged(MediaPlayer mediaPlayer, int newSeekable) {
	}

	@Override
	public void titleChanged(MediaPlayer mediaPlayer, int newTitle) {
	}

	@Override
	public void snapshotTaken(MediaPlayer mediaPlayer, String filename) {
	}

	@Override
	public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
	}

	@Override
	public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
	}

	@Override
	public void error(MediaPlayer mediaPlayer) {
	}

	@Override
	public void mediaMetaChanged(MediaPlayer mediaPlayer, int metaType) {
	}

	@Override
	public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
	}

	@Override
	public void mediaDurationChanged(MediaPlayer mediaPlayer, long newDuration) {
	}

	@Override
	public void mediaParsedChanged(MediaPlayer mediaPlayer, int newStatus) {
	}

	@Override
	public void mediaFreed(MediaPlayer mediaPlayer) {
	}

	@Override
	public void mediaStateChanged(MediaPlayer mediaPlayer, int newState) {
	}

	@Override
	public void newMedia(MediaPlayer mediaPlayer) {
	}

	@Override
	public void subItemPlayed(MediaPlayer mediaPlayer, int subItemIndex) {
	}

	@Override
	public void subItemFinished(MediaPlayer mediaPlayer, int subItemIndex) {
	}

	@Override
	public void endOfSubItems(MediaPlayer mediaPlayer) {
	}
}
