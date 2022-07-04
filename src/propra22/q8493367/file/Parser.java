package propra22.q8493367.file;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * A Parser which can extract a point from a string.
 */
public class Parser implements IParser {
    
	/**  The the regular expression which has to be matched. */
	private String regex = "(\\s)*([+-]{0,1}[0-9]+)(\\s+)([+-]{0,1}[0-9]+)(\\s+.*){0,1}";
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPoint parseLine(String line) {
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		
		if(matcher.matches()) {
			String xString = matcher.group(2);
			String yString = matcher.group(4);
			int x = Integer.parseInt(xString);
			int y = Integer.parseInt(yString);
			return new Point(x, y);
		}
		else {
			return null;
		}
	}
}
