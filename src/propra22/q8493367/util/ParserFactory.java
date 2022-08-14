package propra22.q8493367.util;

/**
 * 
 * A factory for parsers.
 * 
 *  @see <a href="https://www.youtube.com/watch?v=EcFVTgRHJLM&t=834s">The factory pattern explainded on youtube</a>
 *
 */

public class ParserFactory {
	
	/**
	 * Returns the parser specified 
	 * by the parser type.
	 * @param parserType the type of the parser
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
