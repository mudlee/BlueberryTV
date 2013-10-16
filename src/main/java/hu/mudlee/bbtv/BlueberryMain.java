package hu.mudlee.bbtv;

import hu.mudlee.bbtv.common.BlueberryTV;
import hu.mudlee.bbtv.common.OperatingSystem;
import hu.mudlee.bbtv.config.Configuration;
import hu.mudlee.bbtv.config.ConfigurationParser;
import hu.mudlee.bbtv.config.ExceptionHandler;
import hu.mudlee.bbtv.config.VLCLibraryLoader;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import javax.swing.*;

public class BlueberryMain {
	public static void main(final String[] args) {
		String osString = System.getProperty("os.name");
		if (!(osString.contains("Linux") || osString.contains("Windows"))) {
			throw new RuntimeException("Your operating system is not supported: '" + osString + "'");
		}
		final OperatingSystem operatingSystem = OperatingSystem.byString(osString);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ConfigurationParser configurationParser = new ConfigurationParser();
				Configuration configuration = configurationParser.parse(args);
				ExceptionHandler exceptionHandler = new ExceptionHandler();

				if (configuration.isDebug()) {
					Logger.getRootLogger().setLevel(Level.INFO);
				} else {
					Logger.getRootLogger().setLevel(Level.ERROR);
					ConsoleAppender appender = (ConsoleAppender) Logger.getRootLogger().getAppender("stdout");
					appender.setLayout(new PatternLayout("ERROR\nI'm so sorry, something went wrong.\nFor more details use -debug\nError message: %m%n\n"));
				}

				VLCLibraryLoader vlcLibraryLoader = new VLCLibraryLoader();
				vlcLibraryLoader.load(operatingSystem);

				try {
					BlueberryTV blueberryTV = new BlueberryTV(configuration);
					blueberryTV.turnOn();
				} catch (Exception e) {
					exceptionHandler.handle(e, configuration);
				}
			}
		});
	}
}
