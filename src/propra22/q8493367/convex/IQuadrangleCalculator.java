package propra22.q8493367.convex;

import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.draw.model.Quadrangle;


/**
 * The Interface IQuadrangleCalculator provides
 * a method for calculating the biggest quadrangle
 * of a convex hull.
 */
public interface IQuadrangleCalculator {
	
	/**
	 * Calculates the biggest quadrangle
	 * of a convex hull.
	 *
	 * @param aIt represents the point A as described in the description of the algorithm
	 * @param bIt represents the point B as described in the description of the algorithm
	 * @param cIt represents the point C as described in the description of the algorithm
	 * @param dIt represents the point D as described in the description of the algorithm
	 * @return the biggest quadrangle related to the diagonal which goes from the point
	 * represented by aIt to the point represented by cIt. This means that aIt and cIt are
	 * not changed. They can be considered as constants.
	 */
	public Quadrangle calculateQuadrangle(
			IHullIterator aIt, 
			IHullIterator bIt, 
			IHullIterator cIt, 
			IHullIterator dIt);
}
