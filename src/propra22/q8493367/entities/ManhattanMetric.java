package propra22.q8493367.entities;



/**
 * The Class ManhattanMetric implements 
 * a manhattan metric also known as taxicab geometry.
 * @see <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Taxicab geometry</a>
 */
public class ManhattanMetric implements IMetric {
    
	@Override
	public double distance(int x1, int y1, int x2, int y2) {
		// Cast to double to avoid overflow
		return Math.abs((double)x2 - (double)x1) + Math.abs((double)y2 - (double)y1);
	}
}
