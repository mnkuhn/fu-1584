package propra22.q8493367.entities;

/**
 * An interface for a parser. It provides a method
 * for parsing a string.
 */
public interface IParser {
	
	/**
	 * Parses the string.
	 *
	 * @param line the string which is to be parsed
	 * @return the point which is extracted from the line
	 */
	public Point parseLine(String line);
}
