package propra22.q8493367.contour;

import propra22.q8493367.point.IPoint;


/**
 * The section calculator is used to calculate one of the four sections lowerLeft, lowerRight, upperRight and upperLeft.
 * In particular, it is used to calculate the sections of the contour polygon or the sections of the convex hull.
 */
public interface ISectionCalculator {
    
	/**
	 * Calculate section.
	 *
	 * @param sectionType the section type
	 */
	void calculateSection(ContourType sectionType);

}
