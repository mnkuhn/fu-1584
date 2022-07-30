package propra22.q8493367.contour;



/**
 * The contour calculator provides a method to calculate one of the four contours lower left, lower right, upper right and upper left.
 * In this application it calculates the contours of the contour polygon and the convex hull.
 */
public interface IContourCalculator {
    
	/**
	 * Calculates the contour determined by the contour type.
	 *
	 * @param contourType the contour type
	 */
	void calculateContour(ContourType contourType);

}
