package propra22.q8493367.entities;


/**
 * The Interface IMetric provides a method
 * to measure the distance between two points.
 */
public interface IMetric {
	
	/**
	 * Returns the distance between to points measured 
	 * by the underlying metric.
	 *
	 * @param x1 the x coordinate of the first point
	 * @param y1 the y coordinate of the first point
	 * @param x2 the x coordinate of the second point
	 * @param y2 the y coordinate of the second point
	 * @return the distance
	 */
	public double distance(int x1, int y1, int x2, int y2);
}
