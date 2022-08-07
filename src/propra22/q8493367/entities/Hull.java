package propra22.q8493367.entities;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class Hull represents a contour polygon or a convex hull.
 */
public class Hull implements IHull {

	/** The upper left contour. */
	private  List<Point> upperLeft = new ArrayList<>();
	
	/** The lower left contour. */
	private  List<Point> lowerLeft = new ArrayList<>();
	
	/** The lower right contour. */
	private  List<Point> upperRight = new ArrayList<>();
	
	/** The upper right contour. */
	private  List<Point> lowerRight = new ArrayList<>();
	
	/** The highest Y found. */
	private int biggestYFound;
	
	/** The lowest Y found. */
	private int smallestYFound;

	
	/**
	 * Adds a point to a contour.
	 *
	 * @param point the point to be added.
	 * @param contourType the type of the contour
	 */
	@Override
	public void addPointToContour(Point point, ContourType contourType) {
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
	
	/**
	 * Calculates the contour polygon from the point set.
	 *
	 * @param pointSet the point set
	 */
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
	 * Calculates the upper left section. This function has to be called before
	 * calculateUpperRight because the biggest y value found has to be set. 
	 * {@link Hull#biggestYFound}
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
     * Calculates the lower left section. This function has to be called before
     * calculateLowerRight because the smalles y value found has to be set.
     * {@link Hull#smallestYFound}
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
	 * Calculates the upper right section. This function has to be called after
	 * calculateUpperLeft() because the biggest y found has to be set before.
	 * {@link Hull#biggestYFound}
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
	 * Calculate the lower right section. This function has to be called after 
	 * calculateLowerLeft() because the smallest y found has to be set before.
	 * 
	 * {@link Hull#smallestYFound}
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
	 * Gets a point from a contour.
	 *
	 * @param index the index of the point in the contour.
	 * @param contourType the contour type
	 * @return the point from the contour
	 */
	@Override
	public Point getPointFromContour(int index, ContourType contourType) {
		switch (contourType) {
			case UPPERLEFT: {
				return upperLeft.get(index);
			}
			case LOWERLEFT: {
				return lowerLeft.get(index);
			}
			case UPPERRIGHT: {
				return upperRight.get(index);
			}
			case LOWERRIGHT: {
				return lowerRight.get(index);
			}
			default: {
				return null;
			}
		}
	}

	/**
	 * Removes a point from a contour.
	 *
	 * @param index the index of the point in the contour.
	 * @param contourType the type of the contour
	 */
	@Override
	public void removePointFromContour(int index, ContourType contourType) {
		switch (contourType) {
		case UPPERLEFT: {
			upperLeft.remove(index);
			break;
		}
		case LOWERLEFT: {
			lowerLeft.remove(index);
			break;
		}
		case UPPERRIGHT: {
			upperRight.remove(index);
			break;
		}
		case LOWERRIGHT: {
			lowerRight.remove(index);
			break;
		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + contourType);
		}
		}
	}

	/**
	 * Returns true, if in the contour specified by the contour
	 * type are no points. Returns false otherwise.
	 *
	 * @param contourType the type of the contour
	 * @return true, if no points are in the contour, false
	 * otherwise.
	 */
	@Override
	public boolean contourIsEmpty(ContourType contourType) {
		switch (contourType) {
		case UPPERLEFT: {
			return upperLeft.isEmpty();
			}
		case LOWERLEFT: {
			return lowerLeft.isEmpty();
			}
		case UPPERRIGHT: {
			return upperRight.isEmpty();
			}
		case LOWERRIGHT: {
			return lowerRight.isEmpty();

			}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + contourType);
			}
		}
	}

	/**
	 * Gets the size of the contour specified by the
	 * contour type.
	 * @param contourType the section type
	 * @return the number of points in the contour
	 */
	@Override
	public int getSizeOfContour(ContourType contourType) {
		switch (contourType) {
		case UPPERLEFT: {
			return upperLeft.size();
			}
		case LOWERLEFT: {
			return lowerLeft.size();
			}
		case UPPERRIGHT: {
			return upperRight.size();
			}
		case LOWERRIGHT: {
			return lowerRight.size();

			}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + contourType);
			}
		}
	}

	/**
	 * Removes all points from the hull.
	 */
	@Override
	public void clear() {
		upperLeft.clear();
		lowerLeft.clear();
		upperRight.clear();
		lowerRight.clear();
	}

	/**
	 * Removes a point from the contour specified
	 * by the contour type.
	 *
	 * @param point the point
	 * @param contourType the type of the contour
	 */
	@Override
	public void removePointFromContour(Point point, ContourType contourType) {
		switch (contourType) {
		case UPPERLEFT: {
			upperLeft.remove(point);
		}
		case LOWERLEFT: {
			lowerLeft.remove(point);
		}
		case UPPERRIGHT: {
			upperRight.remove(point);
		}
		case LOWERRIGHT: {
			lowerRight.remove(point);

		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + contourType);
		}
		}
	}

	/**
	 * Returns the points of the hull as an array of integers, following
	 * the points clockwise in a cartesian coordinate system. The first point
	 * is the point with the biggest y coordinate from all the points with the
	 * smallest x coordinate.
	 *
	 * @return the int[][]
	 */
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
	
	/**
	 * Returns the points of the hull as a list of 
	 * points, following the points clockwise in a cartesian
	 * coordinate system. The first point
	 * is the point with the biggest y coordinate from all the points with the
	 * smallest x coordinate.
	 *
	 * @return the elements of the hull as a list. The elements
	 * follow clockwise direction.
	 */
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

	
	/**
	 * Returns true, if there are no points in the hull,
	 * returns false otherwise.
	 *
	 * @return true, if is empty
	 */
	@Override
	public boolean isEmpty() {
		return getSizeOfContour(ContourType.UPPERLEFT) == 0;
	}
	
	/**
	 * Returns true, if there is exactly one point in the hull.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean hasOnePoint() {
		return upperLeft.get(0) == lowerRight.get(0);
	}
	
	/**
	 * Returns an iterator which starts at the point
	 * with the biggest y coordinate from all the
	 * points with the smallest x coordinate.
	 *
	 * @return the returned iterator
	 */
	@Override
	public IHullIterator leftIterator() {
		if(!isEmpty()) {
			return new HullIterator(0);
		}
		return null;
		
	}
	

	/**
	 * Returns an iterator which starts at the point
	 * with the smallest y coordinate from all the
	 * points with the biggest x coordinate.
	 *
	 * @return the i hull iterator
	 */
	@Override
	public IHullIterator rightIterator() {
		if(!isEmpty()) {
			return new HullIterator(1);
		}
		return null;
		
	}
	
	/**
	 * Calculates the convex hull outgoing from
	 * the contour polygon.
	 */
	@Override
	public void clean() {
		if (!isEmpty()) {
			for (ContourType sectionType : ContourType.values()) {
				cleanContour(sectionType);
			}
		}
	}
	
	/**
	 * Calculates the contour specified by the contour type
	 * of the convex hull.
	 *
	 * @param contourType the contour type
	 */
	public void cleanContour(ContourType contourType) {
		
		if(!contourIsEmpty(contourType)) {
			int size = getSizeOfContour(contourType);
			if(size >= 3) {
				int base = 0;
				int next = 2;
				while(next < size) {
					//next is on the inner side of the line through base and base + 1
					if(signedTriangleArea(base, next, contourType)  > 0){
						base++;
						next++;	
					}
					else {
						/*next is on the outer side of the line through base and base + 1 or 
						 * next is exactly on the line through base and base + 1
						 */
						if(base > 0) {
							removePointFromContour(base + 1, contourType);
							size--;
							base--;
							next--;
							if(next < size) {
								while(base > 0 && signedTriangleArea(base, next, contourType) < 0) {
									removePointFromContour(base + 1, contourType);
									size--;
									base--;
									next--;	
								}
							}
						}
						//base == 0
						else {
							removePointFromContour(base + 1, contourType);
							size--;
						}
					}
				}
			}
		}
	}


	/**
	 * Signed triangle area. The DFV algorithm with adapted arguments.
	 *
	 * @param base  the base point. This point is the first point of the base line
	 * of the triangle, whose area is calculated.
	 * @param tip  this points represents the tip of the triangle, whose
	 * area is calculated.
	 * @param sectionType the section type
	 * @return the long
	 */
	private long signedTriangleArea(int base, int tip, ContourType sectionType) {
		return sectionType.getSign() * Point.signedTriangleArea(
				//the first point of the baseline
				getPointFromContour(base, sectionType), 
				//The second point of the baseline following the first base point in 
				//the direction of increasing index.
				getPointFromContour(base + 1, sectionType),  
				//The tip of the triangle
				getPointFromContour(tip, sectionType));
	}

	
	

	/**
	 * The Class HullIterator provides an Iterator for the 
	 * hull. It can iterate in both directions (clockwise and counterclockwise) on the hull.
	 * It can not modify the hull and is only thought for iterating through the points
	 * of the hull.
	 */
	private class HullIterator implements IHullIterator {
		
		/** The contour type of the current element. */
		private ContourType contourType;
		
		/**  The index of the current element. */
		private int index;
		
		
		/** 
		 * The temporary index is used for moving the iterator to the previous
		 *  or the next hull point or for reading the previous or next hull point. 
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
