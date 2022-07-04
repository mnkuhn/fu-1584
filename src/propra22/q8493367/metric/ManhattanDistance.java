package propra22.q8493367.metric;
public class ManhattanDistance implements IMetric {

	@Override
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}
}
