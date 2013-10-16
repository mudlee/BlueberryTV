package hu.mudlee.bbtv.programguide;

import hu.mudlee.bbtv.common.DateTimeParser;
import hu.mudlee.bbtv.common.Day;
import hu.mudlee.bbtv.movie.Movie;
import hu.mudlee.bbtv.movie.MovieDurationProvider;
import hu.mudlee.bbtv.movie.MovieType;
import hu.mudlee.bbtv.movie.ThirdPartySourceDownloader;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ProgramGuideParser {
	private final Logger logger = Logger.getLogger(ProgramGuideParser.class);
	private ProgramGuide programGuide;
	private File moviesPath;
	private File programGuidePath;
	private ThirdPartySourceDownloader thirdPartySourceDownloader;

	public ProgramGuideParser(File moviesPath, File programGuidePath) {
		this.moviesPath = moviesPath;
		this.programGuidePath = programGuidePath;
		this.thirdPartySourceDownloader = new ThirdPartySourceDownloader(moviesPath);
	}

	public ProgramGuide parse() {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(programGuidePath);
			document.getDocumentElement().normalize();

			NodeList root = document.getElementsByTagName("week");
			if (root.getLength() != 1) {
				throw new ProgramGuideException("The program guide must contains only one week element.");
			}

			Node guide = root.item(0);
			iterateThrough(guide);

			return programGuide;
		} catch (ProgramGuideException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	private void iterateThrough(Node guide) {
		NodeList dayNodes = guide.getChildNodes();

		TreeMap<Day, List<Movie>> programsByDay = new TreeMap<Day, List<Movie>>();

		for (int dayIndex = 0; dayIndex < dayNodes.getLength(); dayIndex++) {
			Node dayNode = dayNodes.item(dayIndex);
			if (dayNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			List<Movie> movies = new ArrayList<Movie>();
			Day day = Day.byString(dayNode.getNodeName());

			NodeList programNodes = dayNode.getChildNodes();
			for (int programIndex = 0; programIndex < programNodes.getLength(); programIndex++) {
				Node program = programNodes.item(programIndex);
				if (program.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}

				Movie movie = parseMovieFromNode(dayNode, program);
				movies.add(movie);
			}

			programsByDay.put(day, movies);
		}

		programGuide = new ProgramGuide(programsByDay);
	}

	private Movie parseMovieFromNode(Node dayNode, Node programNode) {
		Day day = Day.byString(dayNode.getNodeName());
		int dayOfWeek = Day.getDayOfWeek(day);

		NamedNodeMap attributes = programNode.getAttributes();
		Node fromAttr = attributes.getNamedItem("from");
		String from = fromAttr.getNodeValue();
		String movieName = programNode.getTextContent();
		String mediaPath;
		MovieType type = getTypeByMovieString(programNode.getTextContent());

		if (type == MovieType.LOCAL_MEDIA) {
			mediaPath = moviesPath.getPath() + File.separator + movieName;
			logger.info("Adding local media at '" + mediaPath + "'");

			File file = new File(mediaPath);
			if (!file.exists()) {
				throw new ProgramGuideException("Movie file not found at '" + mediaPath + "'");
			}
		} else {
			logger.info("Adding third party source from '" + movieName + "'");

			File downloadedMovie = thirdPartySourceDownloader.downloadSource(movieName);
			mediaPath = downloadedMovie.getPath();
		}

		long duration = new MovieDurationProvider(mediaPath).getDuration();
		return new Movie(
				mediaPath,
				DateTimeParser.parse(dayOfWeek, from),
				duration,
				type
		);
	}

	private MovieType getTypeByMovieString(String movie) {
		if (movie.startsWith("http://youtube") || movie.startsWith("http://www.youtube")) {
			return MovieType.YOUTUBE_URL;
		}
		if (movie.startsWith("http://vimeo") || movie.startsWith("https://vimeo")) {
			return MovieType.VIMEO_URL;
		}
		return MovieType.LOCAL_MEDIA;
	}
}
