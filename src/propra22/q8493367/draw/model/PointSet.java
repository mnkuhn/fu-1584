package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * This class implements the interface IDrawPanelModel. It provides methods to 
 */
public class PointSet implements IPointSet {
	
	// the points
	private List<IPoint> points = new ArrayList<>();
	
	
	@Override
	public  boolean hasPoint(IPoint point) {
		return searchPoint(point) >= 0;
	}
	
	@Override
	public void addPoint(IPoint point) {
		if(!hasPoint(point)) {points.add(point);}
	}
	
	@Override
	public void removePoint(IPoint point) {
		points.remove(point);	
	}
	
	
	@Override
	public void removePoint(int x, int y) {
		int index = searchPoint(new Point(x, y));
		if(index >= 0) {points.remove(index);}	
	}
	
	
	@Override
	public void lexSort() {
		Collections.sort(points);
	}

	
	private int searchPoint(IPoint point) {
		return Collections.binarySearch(points, point);
	}
	
	@Override
	public int getNumberOfPoints() {
		return points.size();
	}
	
	@Override
	public IPoint getPointAt(int index) throws IndexOutOfBoundsException {
		return points.get(index);
	}
	
	@Override
	public boolean isEmpty() {
		return points.isEmpty();
	}
		
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < getNumberOfPoints(); i++) {
			  stringBuilder.append(points.get(i).toString() + "\n");
		}
		return stringBuilder.toString();
	}

	@Override
    public void clear() {
		points.clear();	
	}
}
