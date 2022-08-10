package propra22.q8493367.entities;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * The Class ListHull is an abstract class for a Hull
 * based on a List data structure.
 */
public class LinkedListHull extends ListHull {
	
	
    /**
     * Instantiates a new linked list hull. The four
     * lists for the contours are initialized 
     * with linked lists.
     */
    public LinkedListHull() {
    	upperLeft = new LinkedList<>();
    	lowerLeft = new LinkedList<>();
    	upperRight = new LinkedList<>();
    	lowerRight = new LinkedList<>();
    	
    	/*
    	upperLeft.add(new Point(2, 1));
    	upperLeft.add(new Point(5, 3));
    	upperLeft.add(new Point(8, 4));
    	upperLeft.add(new Point(15, 5));
    	upperLeft.add(new Point(18, 9));
    	*/
    }
	


	@Override
	public int[][] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<Point> toList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IHullIterator leftIterator() {
		return new HullIterator(0);
	}

	
	@Override
	public IHullIterator rightIterator() {
		return new HullIterator(1);
	}

	
	@Override
	public boolean hasOnePoint() {
		return upperLeft.get(0) == lowerRight.get(0);
	}

	
	@Override
	public void set(PointSet pointSet) {
		clear();
		if(!pointSet.isEmpty()) {
			calculateUpperLeft(pointSet);
			calculateLowerLeft(pointSet);
			calculateUpperRight(pointSet);
			calculateLowerRight(pointSet);
		}
	}





	/**
	 * Calculates the upper left contour of 
	 * the contour polygon.
	 *
	 * @param pointSet the point set on which
	 * the calculation is based on.
	 */
	private void calculateUpperLeft(PointSet pointSet) {
		Point point = pointSet.getPointAt(0);
		upperLeft.add(point);
		
		int maxYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				upperLeft.add(point);
			}
		}
		biggestYFound = maxYSoFar;
		
	}
	

	/**
	 * Calculates the lower left contour of 
	 * the contour polygon.
	 *
	 * @param pointSet the point set on which
	 * the calculation is based on.
	 */
	private void calculateLowerLeft(PointSet pointSet) {
		Point point = pointSet.getPointAt(0);
		lowerLeft.add(point);
		
		int minYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				lowerLeft.add(point);
			}
		}
		smallestYFound = minYSoFar;
		
	}
	

	/**
	 * Calculates the upper right contour of 
	 * the contour polygon.
	 *
	 * @param pointSet the point set on which
	 * the calculation is based on.
	 */
	private void calculateUpperRight(PointSet pointSet) {
		Point point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		upperRight.add(point);
		
		int maxYSoFar = point.getY();
		int pointY;
	    int i = pointSet.getNumberOfPoints() - 2;
		while(maxYSoFar != biggestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				upperRight.add(point);
			}
		}
		
	}


	/**
	 * Calculates the lower right contour of 
	 * the contour polygon.
	 *
	 * @param pointSet the point set on which
	 * the calculation is based on.
	 */
	private void calculateLowerRight(PointSet pointSet) {
		Point point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		lowerRight.add(point);	
		
		int minYSoFar = point.getY();
		int pointY;
		int i = pointSet.getNumberOfPoints() - 2;
		while(minYSoFar != smallestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				lowerRight.add(point);
			}
		}	
	}
	
	/**
	 * Calculates the contour specified by the contour type of the convex hull.
	 *
	 * @param contourType the contour type
	 */
	@Override
	protected void cleanContour(ContourType contourType) {
		if (!contourIsEmpty(contourType)) {
			if (getSizeOfContour(contourType) >= 3) {
				ListIterator<Point> it = getIterator(contourType);

				Point baseA;
				Point baseB;
				Point tip;

				while (it.hasNext()) {

					baseA = it.next();

					if (it.hasNext())
						baseB = it.next();
					else
						break;

					if (it.hasNext())
						tip = it.next();
					else
						break;

					it.previous();
					it.previous();

					// tip is on the inner side of the line through base and base + 1
					if (!(contourType.getSign() * Point.signedTriangleArea(baseA, baseB, tip) > 0)) {
						// do nothing

						/*
						 * tip is on the outer side of the line through baseA and baseB or next is
						 * exactly on the line through baseA and baseB
						 */

						it.remove();
						it.previous();

						if (it.hasPrevious()) {
							baseA = it.previous();
							it.next();
							baseB = it.next();
							// go back before baseB
							it.previous();
							while (it.hasPrevious()
									&& contourType.getSign() * Point.signedTriangleArea(baseA, baseB, tip) < 0) {
								it.remove();
								baseB = baseA;
								it.previous();
								baseA = it.previous();
							}

						}
					}
				}
			}
		}
	}
	
	/**
	 * Gets the iterator for the contour specified
	 * by the contour type.
	 *
	 * @param contourType the contour type
	 * @return the iterator
	 */
	private ListIterator<Point> getIterator(ContourType contourType){
		switch (contourType) {
		case UPPERLEFT: {
			return upperLeft.listIterator();
			}
		case LOWERLEFT: {
			return lowerLeft.listIterator();
			}
		case UPPERRIGHT: {
			return upperRight.listIterator();
			}
		case LOWERRIGHT: {
			return lowerRight.listIterator();

			}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + contourType);
			}
		}
	}
	
	/**
	 * A iterator which allows to iterate through the
	 * points of the hull. No modification of the 
	 * hull is possible.
	 */
	private class HullIterator implements IHullIterator {

		/** The current iterator. */
		ListIterator<Point> currentIterator;
		
		/** The contour type. */
		private ContourType contourType;

		
		/**
		 * Instantiates a new hull iterator. If type is zero
		 * the iterator starts at the point with the highest y
		 * coordinate from all the points with the smallest 
		 * x coordinate. If type is 1 the iterator starts at 
		 * the point with the smallest y coordinate from all
		 * the points with the biggest x coordinate.
		 *
		 * @param type the type
		 */
		private HullIterator(int type) {
			if(type == 0) {
				currentIterator = upperLeft.listIterator();
				contourType = ContourType.UPPERLEFT;	
			}
			if(type == 1) {
				currentIterator = lowerRight.listIterator();
				contourType = ContourType.LOWERRIGHT;
			}
		}
		
		/**
		 * Gets the point the iterator currently 
		 * points on.
		 *
		 * @return the point the iterator currently 
		 * points on.
		 */
		@Override
		public Point getPoint() {
			switch (contourType) {
			case UPPERLEFT: {
				Point point = currentIterator.next();
				currentIterator.previous();
				return point;
			}
			case UPPERRIGHT: {
				Point point = currentIterator.previous();
				currentIterator.next();
				return point;
			}
			case LOWERRIGHT: {
				Point point = currentIterator.next();
				currentIterator.previous();
				return point;
			}
			case LOWERLEFT: {
				Point point = currentIterator.previous();
				currentIterator.next();
				return point;
			}
			}
			return null;
		}

		/**
		 * Moves the iterator to the next point
		 * in the data structure of the four lists,
		 * moving clockwise.
		 * It has to be checked, if this point 
		 * is different from the previous point.
		 */
		private void goNext() {

			switch (contourType) {
			case UPPERLEFT: {
				if (currentIterator.hasNext()) {
					currentIterator.next();
					if(!currentIterator.hasNext()) {
						goNext();
					}
				} else {
					contourType = ContourType.UPPERRIGHT;
					currentIterator = upperRight.listIterator(upperRight.size());
				}
				break;
			}
			case UPPERRIGHT: {
				if (currentIterator.hasPrevious()) {
					currentIterator.previous();
					if(!currentIterator.hasPrevious()) {
						goNext();
					}
				} else {
					contourType = ContourType.LOWERRIGHT;
					currentIterator = lowerRight.listIterator();
				}
				break;
			}
			case LOWERRIGHT: {
				if (currentIterator.hasNext()) {
					currentIterator.next();
					if(!currentIterator.hasNext()) {
						goNext();
					}
				} else {
					contourType = ContourType.LOWERLEFT;
					currentIterator = lowerLeft.listIterator(lowerLeft.size());
				}
				break;
			}
			case LOWERLEFT: {
				if (currentIterator.hasPrevious()) {
					currentIterator.previous();
					if(!currentIterator.hasPrevious()) {
						goNext();
					}
				} else {
					contourType = ContourType.UPPERLEFT;
					currentIterator = upperLeft.listIterator();
				}
				break;
			}

			}

		}
		
		/**
		 * Moves the iterator to the previous point
		 * in the data structure of the four lists,
		 * moving counterclockwise.
		 * It has to be checked, if this point 
		 * is different from the previous point.
		 */
		private void goPrevious() {
			switch(contourType) {
			case UPPERLEFT: {
				if (currentIterator.hasPrevious()) {
					currentIterator.previous();
					if(!currentIterator.hasPrevious()) {
						goPrevious();
					}
				} else {
					contourType = ContourType.LOWERLEFT;
					currentIterator = lowerLeft.listIterator(0);
				}
				break;
			}
			case UPPERRIGHT:{ 
				if (currentIterator.hasNext()) {
					currentIterator.next();
					if(!currentIterator.hasNext()) {
						goPrevious();
					}
				} else {
					contourType = ContourType.UPPERLEFT;
					currentIterator = upperLeft.listIterator(upperLeft.size());
				}
				break;
			}
			case LOWERRIGHT: {
				if (currentIterator.hasPrevious()) {
					currentIterator.previous();
					if(!currentIterator.hasPrevious()) {
						goPrevious();
					}
				} else {
					contourType = ContourType.UPPERRIGHT;
					currentIterator = upperRight.listIterator(0);
				}
				break;
			}
			case LOWERLEFT: {
				if (currentIterator.hasNext()) {
					currentIterator.next();
					if(!currentIterator.hasNext()) {
						goPrevious();
					}
				} else {
					contourType = ContourType.LOWERRIGHT;
					currentIterator = lowerRight.listIterator(lowerRight.size());
				}
				break;
			}	
			}
		}

		@Override
		public Point getNextPoint() {
			next();
			Point nextPoint = getPoint();
			previous();
			return nextPoint;
			 
		}

		
		@Override
		public Point getPreviousPoint() {
			previous();
			Point previousPoint = getPoint();
			next();
			return previousPoint;
		}

		@Override
		public void next() {
			Point currentPoint = getPoint();
			goNext();
			while(currentPoint == getPoint()) {
				goNext();
			}
			
		}

		
		@Override
		public void previous() {
			Point currentPoint = getPoint();
			goPrevious();
			while(currentPoint == getPoint()) {
				goPrevious();
			}
		}
	}
	
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		LinkedListHull hull = new LinkedListHull();
		hull.clean();
	}





	
}
