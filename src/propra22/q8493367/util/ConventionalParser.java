package propra22.q8493367.util;

import propra22.q8493367.entities.Point;

/**
 * A Parser which can extract a point from a string.
 * It checks every single character of the String
 * by moving from left tto right.
 */
public class ConventionalParser implements IParser {
	
	@Override
	public Point parseLine(String line) {

		int xCoordinate = 0;
		int xSign = 1;

		int yCoordinate = 0;
		int ySign = 1;

		int currentPosition = 0;
        
		// Search for whitespaces or + or -
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
				} else if(line.charAt(currentPosition) == '+') {
					currentPosition++;
					break;
				} else {
					return null;
				}
			}
		}

		// Get the first integer
		while (currentPosition < line.length()) {
			if (Character.isDigit(line.charAt(currentPosition))) {
				xCoordinate = xCoordinate * 10 + Character.getNumericValue(line.charAt(currentPosition++));
			} else {
				break;
			}
		}
        
		// Now there must be a whitespace.
		if (currentPosition < line.length() && line.charAt(currentPosition) == ' ') {
			currentPosition++;
		} else {
			return null;
		}
        
		// Search for whitespaces or + or -
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
				} else if (line.charAt(currentPosition) == '+') {
					currentPosition++;
					break;
				}
				else {
					return null;
				}
			}
		}
        
		// Get the second integer
		while (currentPosition < line.length()) {
			if (Character.isDigit(line.charAt(currentPosition))) {
				yCoordinate = yCoordinate * 10 + Character.getNumericValue(line.charAt(currentPosition++));
			} else {
				break;
			}
		}
        
		// If the line has not ended yet there must be a whitespace
		if (currentPosition < line.length() && line.charAt(currentPosition++) != ' ') {
			return null;
		}
        
		// Ignore the rest of the line
		return new Point(xCoordinate * xSign, yCoordinate * ySign);
	}
}
