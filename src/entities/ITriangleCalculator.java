package entities;



/**
 * The Interface ITriangleCalculator declares the method which
 * calculates the biggest triangle of the convex hull.
 */
public interface ITriangleCalculator {
	
	/**
	 * Calculates the triangle of the convex hull.
	 *
	 * @param triangle the triangle whose attributes 
	 * are set to those of the biggest triangle.
	 */
	public void calculate(Triangle triangle);
}
