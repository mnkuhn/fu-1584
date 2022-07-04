package propra22.q8493367.metric;



/**
 * The Class ManhattanDistance implements 
 * a manhattan metric.
 */
public class ManhattanMetric implements IMetric {
    
	/**
	 *{@inheritDoc}
	 */
	@Override
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}
}
