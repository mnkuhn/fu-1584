package propra22.q8493367.animation;

import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.point.IPoint;
/**
 * Interface for a pair of tangents of a convex hull
 */

public interface ITangentPair {
    /**
     * Calculates the new slope of the tangent lines 
     * and the point of contact.
     */
	void step();
    
	/**
	 * Gets the first tangent
	 * @return Array of the two
	 * endpoints (index 0 and 2) and the contact point 
	 * (index 1) of the tangent.
	 */
	IPoint[] getTangent1();
	
	/**
	 * Gets the second tangent
	 * @return Array of the two
	 * endpoints (index 0 and 2) and the contact point 
	 * (index 1) of the tangent.
	 */
	IPoint[] getTangent2();
    
	/**
	 * Initializes the tangent pair.
	 * @param hull
	 */
	void initialize(IHull hull);
     
	/**
	 * Sets the length of the tangents
	 * @param lenght
	 */
	void setLength(float lenght);

	
}
