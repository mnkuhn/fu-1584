package propra22.q8493367.entities;



/**
 * The Class ManhattanMetric implements 
 * a manhattan metric also known as taxicab geometry.
 * @see <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Taxicab geometry</a>
 */
public class ManhattanMetric implements IMetric {
    
	@Override
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}
}
