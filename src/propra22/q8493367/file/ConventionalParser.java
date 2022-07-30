package propra22.q8493367.file;

import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

/**
 * A Parser which can extract a point from a string.
 */
public class ConventionalParser implements IParser {
	
	@Override
	public IPoint parseLine(String line) {

		int xCoordinate = 0;
		int xSign = 1;

		int yCoordinate = 0;
		int ySign = 1;
		;

		int currentPosition = 0;

		while (currentPosition < line.length()) {
			if (line.charAt(currentPosition) == ' ') {
				currentPosition++;
			} else {
				if (Character.isDigit(line.charAt(currentPosition)))
					break;
				else if (line.charAt(currentPosition) == '-') {
					xSign = -1;
					currentPosition++;
					break;
				} else {
					return null;
				}
			}
		}

		
		while (currentPosition < line.length()) {
			if (Character.isDigit(line.charAt(currentPosition))) {
				xCoordinate = xCoordinate * 10 + Character.getNumericValue(line.charAt(currentPosition++));
			} else {
				break;
			}
		}

		if (currentPosition < line.length() && line.charAt(currentPosition) == ' ') {
			currentPosition++;
		} else {
			return null;
		}

		while (currentPosition < line.length()) {
			if (line.charAt(currentPosition) == ' ') {
				currentPosition++;
			} else {
				if (Character.isDigit(line.charAt(currentPosition)))
					break;
				else if (line.charAt(currentPosition) == '-') {
					ySign = -1;
					currentPosition++;
					break;
				} else {
					return null;
				}
			}
		}

		while (currentPosition < line.length()) {
			if (Character.isDigit(line.charAt(currentPosition))) {
				yCoordinate = yCoordinate * 10 + Character.getNumericValue(line.charAt(currentPosition++));
			} else {
				break;
			}
		}

		if (currentPosition < line.length() && line.charAt(currentPosition++) != ' ') {
			return null;
		}

		return new Point(xCoordinate * xSign, yCoordinate * ySign);
	}
	
	public static void main(String[] args) {
		String str = "-12  -245 hallo hier ist ein Kommentar";
		IParser parser = new ConventionalParser();
		IPoint parsedPoint = parser.parseLine(str);
		if(parsedPoint != null) {
			System.out.println(parsedPoint);
		}
		else {
			System.out.println("Der ist null");
		}
	}
}
