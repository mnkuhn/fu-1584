package propra22.q8493367.animation;

import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.contour.IHull;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class AntipodalPairs {
	List<IPoint[]> antiPodalPairs = new ArrayList<>();
	int index;
	
	
	
	
	public IPoint getHullPointBefore(int antipodalPoint) {
		int tmpIndex = index;
		IPoint a = antiPodalPairs.get(tmpIndex)[antipodalPoint];
		tmpIndex = Math.floorMod(tmpIndex - 1, antiPodalPairs.size());
		while(a == antiPodalPairs.get(tmpIndex)[antipodalPoint]) {
			tmpIndex = Math.floorMod(tmpIndex - 1, antiPodalPairs.size());
		}
		return antiPodalPairs.get(tmpIndex)[antipodalPoint];
	}
	
	public IPoint getHullPoint(int antipodalPoint) {
		return antiPodalPairs.get(index)[antipodalPoint];
	}
	
	public IPoint getHullPointAfter(int antipodalPoint) {
		int tmpIndex = index;
		IPoint a = antiPodalPairs.get(tmpIndex)[antipodalPoint];
		tmpIndex = Math.floorMod(tmpIndex + 1, antiPodalPairs.size());
		while(a == antiPodalPairs.get(tmpIndex)[antipodalPoint]) {
			tmpIndex = Math.floorMod(tmpIndex + 1, antiPodalPairs.size());
		}
		return antiPodalPairs.get(tmpIndex)[antipodalPoint];
	}
	
	
	
	
	
	
	public void update(IHull convexHull) {
		antiPodalPairs.clear();

		IHullIterator aIt = convexHull.getLeftIt();
		IHullIterator cIt = convexHull.getRightIt();

		// Put first antipodal pair into the list
		antiPodalPairs.add(new IPoint[] { aIt.getPoint(), cIt.getPoint() });

		// Convex hull has more than one point
		if (aIt.getPoint() != cIt.getPoint()) {

			IPoint left = aIt.getPoint();
			IPoint right = cIt.getPoint();
			// ( !((aIt.getPoint() == left) && (cIt.getPoint() == right)) )
			do {
				long angleComparisonTestResult = Point.angleComparisonTest(aIt.getPoint(), aIt.getNextPoint(),
						cIt.getPoint(), cIt.getNextPoint());
				// angleComparisonResult > 0
				if (angleComparisonTestResult > 0) {
					aIt.next();
					// angleComparisonResult < 0
				} else if (angleComparisonTestResult < 0) {
					cIt.next();
				}
				// angleComparisonResult == 0
				else {
					if (Point.isShorter(aIt.getPoint(), aIt.getNextPoint(), cIt.getPoint(), cIt.getNextPoint())) {
						antiPodalPairs.add(new IPoint[] { aIt.getNextPoint(), cIt.getPoint() });

						cIt.next();
					} else {
						antiPodalPairs.add(new IPoint[] { aIt.getPoint(), cIt.getNextPoint() });
						aIt.next();
					}
				}
				antiPodalPairs.add(new IPoint[] { aIt.getPoint(), cIt.getPoint() });
			} while (!((aIt.getPoint() == left) && (cIt.getPoint() == right)));
		}

		// Set new index
		index = Math.floorMod(index, antiPodalPairs.size());
	}

	public void next() {
		index = Math.floorMod(index + 1, antiPodalPairs.size());	
	}
	
	public void previous() {
		index = Math.floorMod(index - 1, antiPodalPairs.size());	
	}
	
	public boolean diameterIsZero() {
		IPoint a = antiPodalPairs.get(0)[0];
	    IPoint b = antiPodalPairs.get(0)[1];
		return  antiPodalPairs.size() == 1 && a == b ? true : false;
	}
}
