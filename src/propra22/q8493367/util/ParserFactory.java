package propra22.q8493367.util;

/**
 * 
 * A factory for parsers.
 *
 */

public class ParserFactory {
	
	/**
	 * Returns the parser specified 
	 * by the parser type.
	 * @param parserType
	 * @return the parser
	 */
	public IParser getParser(String parserType) {
		if(parserType == null) {
			return null;
		} else if (parserType == "REGEX") {
			return new RegExParser();
		} else if (parserType == "CONVENTIONAL") {
			return new ConventionalParser();
		}
		return null;
	}
}
