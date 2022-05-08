package propra22.q8493367.file;

import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The Interface IParser provides a method
 * for parsing a string.
 */
public interface IParser {
	
	/**
	 * Parses the line.
	 *
	 * @param line - the string which is to be parsed
	 * @return the point which is extracted from the line
	 */
	public IPoint parseLine(String line);
}
