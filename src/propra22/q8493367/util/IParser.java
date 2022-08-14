package propra22.q8493367.util;

import propra22.q8493367.entities.Point;

/**
 * An Interface for a parser. It declares a method
 * for parsing a string.
 */
public interface IParser {
	
	/**
	 * Extracts a point from a String.
	 *
	 * @param line the string which is to be parsed
	 * @return the point which is extracted from the line
	 */
	public Point parseLine(String line);
}
