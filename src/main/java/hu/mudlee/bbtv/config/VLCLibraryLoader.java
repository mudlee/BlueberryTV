package hu.mudlee.bbtv.config;

import com.sun.jna.NativeLibrary;
import hu.mudlee.bbtv.common.OperatingSystem;
import org.apache.log4j.Logger;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class VLCLibraryLoader {
	private final Logger logger = Logger.getLogger(VLCLibraryLoader.class);

	public void load(OperatingSystem operatingSystem) {
		logger.info("Loading VLC library '" + RuntimeUtil.getLibVlcLibraryName() + "'");

		switch (operatingSystem) {
			case WINDOWS:
				NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "system/library/vlc/windows");
				break;
			case LINUX:
				NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "system/library/vlc/linux");
				break;
			default:
				throw new IllegalStateException();
		}
	}
}
