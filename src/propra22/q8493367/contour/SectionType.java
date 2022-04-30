package propra22.q8493367.contour;

/**
 * SectionType identifies the 4 sections of the contour polygon i.e. the convex hull.
 * 
 * LOWERLEFT:  points starting at point with lowest x coordinate and highest y coordinate ordered by rising x coordinate and rising y coordinate
 * UPPERLEFT:  points starting at point with lowest x coordinate and highest y coordinate ordered by rising x coordinate and falling y coordinate
 * LOWERRIGHT: points starting at point with highest x coordinate and highest y coordinate ordered by falling x coordinate and falling y coordinate
 * UPPERRIGHT: points starting at point with highest x coordinate and highest y coordinate ordered by falling x coordinate and falling y coordinate
 * 
 * It also provides a sign attribute which is used to determine the sign of the result of the DFV algorithm
 */

public enum SectionType {
	
	LOWERLEFT(1), LOWERRIGHT(-1), UPPERRIGHT(1), UPPERLEFT(-1);
	
	private int sign;
	private SectionType(int sign) {
		this.sign = sign;
	}
	
	public int getSign() {
		return sign;
	}
}
