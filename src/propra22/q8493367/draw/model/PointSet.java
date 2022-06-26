package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import propra22.q8493367.draw.controller.IDrawPanelControllerObserver;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * This class implements the interface IDrawPanelModel. It provides methods to 
 */
public class PointSet implements IPointSet {
	
	// the points
	private List<IPoint> points = new ArrayList<>();
	
	private boolean hasChanged = false;
	
	private int minX = 0;
	private int maxX = 0;
	private int minY = 0;
	private int maxY = 0;
	
	
	
	@Override
	public  boolean hasPoint(IPoint point) {
		return searchPoint(point) >= 0;
	}
	
	@Override
	public void addPoint(IPoint point) {
		if(!hasPoint(point)) {points.add(point);}
		checkForNewBounds(point);
		hasChanged = true;
	}

	private void checkForNewBounds(IPoint point) {
		if (points.size() == 0) {
			minX = 0;
			maxX = 0;
			minY = 0;
			maxY = 0;
		}
		else if(points.size() == 1) {
			maxX = point.getX();
			minX = point.getX();
			maxY = point.getY();
			minY = point.getY();
		}
		else if(points.size() > 1) {
			int tempX = point.getX();
			int tempY = point.getY();
			if(tempX > maxX) {maxX = tempX;}
			if(tempX < minX) {minX = tempX;}
			if(tempY > maxY) {maxY = tempY;}
			if(tempY < minY) {minY = tempY;}
		}
	}
	
	@Override
	public void removePoint(IPoint point) {
		points.remove(point);
		hasChanged = true;
	}
	
	
	@Override
	public void removePoint(int x, int y) {
		int index = searchPoint(new Point(x, y));
		if(index >= 0) {
			points.remove(index);
			hasChanged = true;
		}	
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
		hasChanged = true;
		
		
	}

	@Override
	public boolean hasChanged() {
		return hasChanged;
	}
	
	@Override
	public void setHasChanged(boolean b) {
		hasChanged = b;
	}

	@Override
	public int getMinX() {
		return minX;
	}

	@Override
	public int getMaxX() {
		return maxX;
	}

	@Override
	public int getMinY() {
		return minY;
	}

	@Override
	public int getMaxY() {
		return maxY;
	}

	@Override
	public int size() {
		return points.size();
	}
	
	
	
	
	
}
