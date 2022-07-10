package propra22.q8493367.convex;

import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IQuadrangle;

/**
 * The Interface IDiameterCalculator provides a method
 * which calculates the diameter of a convex hull.
 */
public interface IDiameterAndQuadrangleCalulator {
	
	/**
	 * Calculates the diameter of the convex hull.
	 *
	 * @param diameter - the diameter object
	 * @param inQuadrangle - the in quadrangle object
	 */
	public void calculate(IDiameter diameter, IQuadrangle inQuadrangle);  // inQuadrangle gehört in den QuadrangleCalculator
}
