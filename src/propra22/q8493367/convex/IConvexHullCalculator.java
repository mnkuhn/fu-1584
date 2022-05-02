package propra22.q8493367.convex;

import propra22.q8493367.contour.SectionType;

// TODO: Auto-generated Javadoc
/**
 * The  IContourPolygonCaluclator interface provides a method
 * for calulating one of the four sections and a method for updating
 * the model.
 */
public interface IConvexHullCalculator {
    
	/**
	 * Calculate one of the four sections
	 *
	 * @param sectionType -  the type of the section
	 */
	void calculateSection(SectionType sectionType);

	/**
	 * updates the draw panel model
	 */
	void updateModel();

}
