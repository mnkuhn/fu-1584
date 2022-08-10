package propra22.q8493367.entities;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class Hull represents a contour polygon or a convex hull.
 */
public class ArrayListHull extends ListHull {
	
	/**
     * Instantiates a new array list hull. The four
     * lists for the contours are initialized 
     * with array lists.
     */
	public ArrayListHull() {
		upperLeft = new ArrayList<>();
		lowerLeft = new ArrayList<>();
		lowerRight = new ArrayList<>();
		upperRight = new ArrayList<>();
		
	}
	
	/**
	 * Adds a point to the contour 
	 * specified by the contour type.
	 *
	 * @param point the point
	 * @param contourType the contour type
	 */
	
	private void addPointToContour(Point point, ContourType contourType) {
		switch (contourType) {
		case UPPERLEFT: {
			upperLeft.add(point);
			break;
		}
		case LOWERLEFT: {
			lowerLeft.add(point);
			break;
		}
		case UPPERRIGHT: {
			upperRight.add(point);
			break;
		}
		case LOWERRIGHT: {
			lowerRight.add(point);
			break;
		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + contourType);
		}
		}
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
	 * Calculates the upper left contour. This function has to be called before
	 * calculateUpperRight because the biggest y value found has to be set. 
	 * {@link ArrayListHull#biggestYFound}
	 *
	 * @param pointSet the point set
	 */
	private void calculateUpperLeft(PointSet pointSet) {
		Point point = pointSet.getPointAt(0);
		addPointToContour(point, ContourType.UPPERLEFT);
		
		int maxYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				addPointToContour(point, ContourType.UPPERLEFT);
			}
		}
		biggestYFound = maxYSoFar;
	}
	
	
    /**
     * Calculates the lower left contour. This function has to be called before
     * calculateLowerRight because the smallest y value found has to be set.
     * {@link ArrayListHull#smallestYFound}
     *
     * @param pointSet the point set
     */
    private void calculateLowerLeft(PointSet pointSet) {
		Point point = pointSet.getPointAt(0);
		addPointToContour(point, ContourType.LOWERLEFT);
		
		int minYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				addPointToContour(point, ContourType.LOWERLEFT);
			}
		}
		smallestYFound = minYSoFar;
	}
	
	
	/**
	 * Calculates the upper right contour. This function has to be called after
	 * calculateUpperLeft() because the biggest y found has to be set before.
	 * {@link ArrayListHull#biggestYFound}
	 *
	 * @param pointSet the point set
	 */
	private void calculateUpperRight(PointSet pointSet) {
		Point point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		addPointToContour(point, ContourType.UPPERRIGHT);	
		
		int maxYSoFar = point.getY();
		int pointY;
	    int i = pointSet.getNumberOfPoints() - 2;
		while(maxYSoFar != biggestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				addPointToContour(point, ContourType.UPPERRIGHT);
			}
		}
	}
	
	
	/**
	 * Calculates the lower right contour. This function has to be called after 
	 * calculateLowerLeft() because the smallest y found has to be set before.
	 * 
	 * {@link ArrayListHull#smallestYFound}
	 *
	 * @param pointSet the point set
	 */
	private void calculateLowerRight(PointSet pointSet) {
		Point point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		addPointToContour(point, ContourType.LOWERRIGHT);		
		
		int minYSoFar = point.getY();
		int pointY;
		int i = pointSet.getNumberOfPoints() - 2;
		while(minYSoFar != smallestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				addPointToContour(point, ContourType.LOWERRIGHT);
			}
		}
	}

	
	/**
	 * Gets the point with index i from the contour 
	 * specified by the contour type.
	 *
	 * @param i the index of the point in the contour
	 * @param contourType the type of the contour
	 * @return the point with index i from the specified contour
	 */
	
	public Point getPointFromContour(int i, ContourType contourType) {
		switch (contourType) {
			case UPPERLEFT: {
				return upperLeft.get(i);
			}
			case LOWERLEFT: {
				return lowerLeft.get(i);
			}
			case UPPERRIGHT: {
				return upperRight.get(i);
			}
			case LOWERRIGHT: {
				return lowerRight.get(i);
			}
			default: {
				return null;
			}
		}
	}

	

	
	
	@Override
	public int[][] toArray() {
		 List<Point> pointList = toList();
		 int[][] pointArr = new int[pointList.size()][2];
		 for(int i = 0; i < pointList.size(); i++) {
			 pointArr[i][0] = pointList.get(i).getX();
			 pointArr[i][1] = pointList.get(i).getY(); 
		 }
		 return pointArr;
	}
	
	
	@Override
	public List<Point> toList() {
		List<Point> pointList = new ArrayList<>();
		IHullIterator it = leftIterator();
		Point start = it.getPoint();
		do {
			pointList.add(it.getPoint());
			it.next();
		} while (it.getPoint() != start);
		return pointList;
	}

	
	@Override
	public boolean isEmpty() {
		return getSizeOfContour(ContourType.UPPERLEFT) == 0;
	}
	
	
	@Override
	public boolean hasOnePoint() {
		return upperLeft.get(0) == lowerRight.get(0);
	}
	
	
	@Override
	public IHullIterator leftIterator() {
		if(!isEmpty()) {
			return new HullIterator(0);
		}
		return null;
		
	}
	

	@Override
	public IHullIterator rightIterator() {
		if(!isEmpty()) {
			return new HullIterator(1);
		}
		return null;
		
	}
	
	
	
	


	

	
	

	/**
	 * An iterator for the hull, which allows the programmer to traverse the points of the 
	 * hull in both directions. In this application a hull is either the contour polygon
	 * or the convex hull.
	 */
	private class HullIterator implements IHullIterator {
		
		/** The contour type of the current element. */
		private ContourType contourType;
		
		/**  The index of the current element. */
		private int index;
		
		
		/** 
		 * The temporary index is used for moving the iterator to the previous
		 * or the next hull point or for reading the previous or next hull point. 
		 */
		private int tmpIndex;
		
		/** 
		 * The temporary contour type is used for moving the iterator to the previous
		 *  or the next hull point or for reading the previous or next hull point. 
		 */
		private ContourType tmpContourType;

		/**
		 * Instantiates a new hull iterator.
		 *
		 * @param type represents the type of the iterator.
		 * 0 stands for the iterator starting on the left most point
		 * 1 stands for the iterator starting on the right most point
		 */
		private HullIterator(int type) {
			if(type == 0) {
				contourType = ContourType.UPPERLEFT;
				index = 0;
			}
			if(type == 1) {
				contourType = ContourType.LOWERRIGHT;
				index = 0;
			}
		}
		
		/**
		 * Returns the point with respect to the current iterator position.
		 *
		 * @return the point of the current iterator position
		 */
		@Override
		public Point getPoint() {
			if(isEmpty())
				return null;
			return getPointFromContour(index, contourType);
		}

		/**
		 * Gets the next point with respect to the current iterator position.
		 *
		 * @return the next point
		 */
		@Override
		public Point getNextPoint() {
			if (isEmpty()) {
				return null;
			} else {
				tmpIndex = index;
				tmpContourType = contourType;
				if (!hasOnePoint()) {
					goNext();
					while (getPointFromContour(tmpIndex, tmpContourType) == getPointFromContour(index, contourType)) {
						goNext();
					}
				}
				return getPointFromContour(tmpIndex, tmpContourType);
			}
		}
		
		/**
		 * Gets the previous point with respect to the current iterator position.
		 *
		 * @return the previous point
		 */
		@Override
		public Point getPreviousPoint() {
			if (isEmpty()) {
				return null;
			} else {
				tmpIndex = index;
				tmpContourType = contourType;
				if (!hasOnePoint()) {
					goPrevious();
					while (getPointFromContour(tmpIndex, tmpContourType) == getPointFromContour(index, contourType)) {
						goPrevious();
					}
				}
			}
			return getPointFromContour(tmpIndex, tmpContourType);
		}

		/**
		 * Moves the iterator to the next point, moving clockwise in a
		 * cartesian coordinate system.
		 */
		@Override
		public void next() {
			if (!isEmpty() && !hasOnePoint()) {
				tmpIndex = index;
				tmpContourType = contourType;
				goNext();
				while (getPointFromContour(tmpIndex, tmpContourType) == getPointFromContour(index, contourType)) {
					goNext();
				}
				index = tmpIndex;
				contourType = tmpContourType;
			}
		}
		
		/**
		 * Calculates the next temporary index and the next 
		 * temporary contour type for a movement in clockwise 
		 * direction. It has to be checked, if this new temporary iterator
		 * position references a new point.
		 */
		private void goNext(){
			switch(tmpContourType) {
				case UPPERLEFT : {
					if(tmpIndex + 1 < getSizeOfContour(tmpContourType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfContour(ContourType.UPPERRIGHT) - 1;
					    tmpContourType = ContourType.UPPERRIGHT;
					}
					break;
				}
				case UPPERRIGHT: {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
						tmpContourType = ContourType.LOWERRIGHT;
					}
					break;
				}
				case LOWERRIGHT: {
					if(tmpIndex + 1 < getSizeOfContour(tmpContourType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfContour(ContourType.LOWERLEFT) - 1;
						tmpContourType = ContourType.LOWERLEFT;
					}
					break;
				}
				case LOWERLEFT : {
					if(tmpIndex > 0) {
						tmpIndex--;
					}
					else {
						tmpIndex = 0;
						tmpContourType = ContourType.UPPERLEFT;
					}
					break;
				}
			}
		}
		
		/**
		 * Moves the iterator to the previous point, moving counterclockwise in a
		 * cartesian coordinate system.
		 */
		@Override
		public void previous() {
			if(!isEmpty() && !hasOnePoint()) {
				tmpIndex = index;
				tmpContourType = contourType;
				goPrevious();
				while(getPointFromContour(tmpIndex, tmpContourType) == getPointFromContour(index, contourType)) {
					goPrevious();
				}
				index = tmpIndex;
				contourType = tmpContourType;	
			}
		}
		
		/**
		 * Calculates the next temporary index and the next 
		 * temporary contour type for a movement in counterclockwise 
		 * direction. It has to be checked, if this new temporary iterator
		 * position references a new point.
		 */
		private void goPrevious() {
			switch(tmpContourType) {
				case UPPERLEFT : {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
					    tmpContourType = ContourType.LOWERLEFT;
					}
					break;
				}
				case UPPERRIGHT: {
					if(tmpIndex + 1 < getSizeOfContour(tmpContourType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfContour(ContourType.UPPERLEFT) - 1;
						tmpContourType = ContourType.UPPERLEFT;
					}
					break;
				}
				case LOWERRIGHT: {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
						tmpContourType = ContourType.UPPERRIGHT;
					}
					break;
				}
				case LOWERLEFT : {
					if(tmpIndex + 1< getSizeOfContour(tmpContourType)) {
						tmpIndex++;
					}
					else {
						tmpIndex = getSizeOfContour(ContourType.LOWERRIGHT) - 1;
						tmpContourType = ContourType.LOWERRIGHT;
					}
					break;
				}
			}
		}
	}



}
