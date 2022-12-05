package entities;


/**
 * The Enum ContourType identifies the 4 contours of the contour polygon i.e. the convex hull.
 * It also provides a sign method which is used to determine the sign of the result of the DFV algorithm. 
 * We need this method because we have to distinguish clockwise and counterclockwise movement.
 */

public enum ContourType {
	
	/**
	 * The upper left contour. 
	 * From all points with the smallest x coordinate, the point with the largest 
	 * y coordinate is selected. Starting from point, the other points 
	 * follow with increasing x and increasing y coordinates until the point with
	 * the highest y coordinate is reached.
	 */
	UPPERLEFT(1L), 
	
	/** 
	 * The upper right contour.
	 * From all points with the largest x coordinate, the point with the smallest 
	 * y coordinate is selected. Starting from this point, the other points 
	 * follow with decreasing x and increasing y coordinates until the point with the
	 * highest y coordinate is reached.
	 */
	UPPERRIGHT(-1L), 
	
	/** 
	 * The lower right contour.
	 * From all points with the smallest x coordinate, the point with the largest 
	 * y coordinate is selected. Starting from this point, the other points 
	 * follow with increasing x and increasing y coordinates until the point with
	 * the lowest y coordinate is reached.
	 */
	LOWERRIGHT(1L), 
	
	/** 
	 * The lower left contour.
	 * From all points with the smallest x coordinate, the point with the largest 
	 * y coordinate is selected. Starting from this point, the other points 
	 * follow with increasing x and decreasing y coordinates until the point with
	 * the lowest y coordinate is reached.
	 */
	LOWERLEFT(-1L);
	
	/** The sign of the contour type which is -1 or 1. This value is needed to 
	 * take the direction of movement into account in the DFV algorithm.
	 */
	private long sign;
	
	/**
	 * Private constructor for ContourType.
	 *
	 * @param sign  the sign which is -1 or 1
	 */
	private ContourType(long sign) {
		this.sign = sign;
	}
	
	/**
	 * Returns the sign of the contour type.
	 *
	 * @return the sign which is -1 or 1
	 */
	public long getSign() {
		return sign;
	}
}
