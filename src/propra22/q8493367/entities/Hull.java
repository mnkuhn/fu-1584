package propra22.q8493367.entities;

import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
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
	private int highestYFound;
	
	/** The lowest Y found. */
	private int lowestYFound;

	
	/**
	 * Adds the point to section.
	 *
	 * @param point the point
	 * @param sectionType the section type
	 */
	@Override
	public void addPointToSection(Point point, ContourType sectionType) {
		switch (sectionType) {
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
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}
	
	/**
	 * Sets the.
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
	 * calculateUpperRight because HighestYFound has to be set.
	 *
	 * @param pointSet the point set
	 */
	private void calculateUpperLeft(PointSet pointSet) {
		Point point = pointSet.getPointAt(0);
		addPointToSection(point, ContourType.UPPERLEFT);
		
		int maxYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				addPointToSection(point, ContourType.UPPERLEFT);
			}
		}
		highestYFound = maxYSoFar;
	}
	
	
    /**
     * Calculates the lower left section. This function has to be called before
     * calculateLowerRight because the LowestYFound has to be set.
     *
     * @param pointSet the point set
     */
    private void calculateLowerLeft(PointSet pointSet) {
		Point point = pointSet.getPointAt(0);
		addPointToSection(point, ContourType.LOWERLEFT);
		
		int minYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				addPointToSection(point, ContourType.LOWERLEFT);
			}
		}
		lowestYFound = minYSoFar;
	}
	
	
	/**
	 * Calculates the upper right section. This function has to be called after
	 * calculateUpperLeft() because the highestYFound has to be set.
	 *
	 * @param pointSet the point set
	 */
	private void calculateUpperRight(PointSet pointSet) {
		Point point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		addPointToSection(point, ContourType.UPPERRIGHT);	
		
		int maxYSoFar = point.getY();
		int pointY;
	    int i = pointSet.getNumberOfPoints() - 2;
		while(maxYSoFar != highestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				addPointToSection(point, ContourType.UPPERRIGHT);
			}
		}
	}
	
	
	/**
	 * Calculate the lower right section. This function has to be called after 
	 * calculateLowerLeft() because the lowestYFound has to be set.
	 *
	 * @param pointSet the point set
	 */
	private void calculateLowerRight(PointSet pointSet) {
		Point point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		addPointToSection(point, ContourType.LOWERRIGHT);		
		
		int minYSoFar = point.getY();
		int pointY;
		int i = pointSet.getNumberOfPoints() - 2;
		while(minYSoFar != lowestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				addPointToSection(point, ContourType.LOWERRIGHT);
			}
		}
	}

	
	/**
	 * Gets the point from section.
	 *
	 * @param index the index
	 * @param contourType the contour type
	 * @return the point from section
	 */
	@Override
	public Point getPointFromSection(int index, ContourType contourType) {
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
	 * Removes the point from contour.
	 *
	 * @param index the index
	 * @param sectionType the section type
	 */
	@Override
	public void removePointFromContour(int index, ContourType sectionType) {
		switch (sectionType) {
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
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}

	/**
	 * Section is empty.
	 *
	 * @param sectionType the section type
	 * @return true, if successful
	 */
	@Override
	public boolean sectionIsEmpty(ContourType sectionType) {
		switch (sectionType) {
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
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
			}
		}
	}

	/**
	 * Gets the size of section.
	 *
	 * @param sectionType the section type
	 * @return the size of section
	 */
	@Override
	public int getSizeOfSection(ContourType sectionType) {
		switch (sectionType) {
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
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
			}
		}
	}

	/**
	 * Clear.
	 */
	@Override
	public void clear() {
		upperLeft.clear();
		lowerLeft.clear();
		upperRight.clear();
		lowerRight.clear();
	}

	/**
	 * Removes the point from contour.
	 *
	 * @param point the point
	 * @param sectionType the section type
	 */
	@Override
	public void removePointFromContour(Point point, ContourType sectionType) {
		switch (sectionType) {
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
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}

	/**
	 * To array.
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
	 * To list.
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
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	@Override
	public boolean isEmpty() {
		return getSizeOfSection(ContourType.UPPERLEFT) == 0;
	}
	
	/**
	 * Checks for one point.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean hasOnePoint() {
		return upperLeft.get(0) == lowerRight.get(0);
	}
	
	/**
	 * Left iterator.
	 *
	 * @return the i hull iterator
	 */
	@Override
	public IHullIterator leftIterator() {
		if(!isEmpty()) {
			return new HullIterator(0);
		}
		return null;
		
	}
	

	/**
	 * Right iterator.
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
	 * Clean.
	 */
	@Override
	public void clean() {
		if (!isEmpty()) {
			for (ContourType sectionType : ContourType.values()) {
				calculateContour(sectionType);
			}
		}
	}
	
/**
 * Calculate contour.
 *
 * @param contourType the contour type
 */
public void calculateContour(ContourType contourType) {
		
		if(!sectionIsEmpty(contourType)) {
			int size = getSizeOfSection(contourType);
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
			getPointFromSection(base, sectionType), 
			//The second point of the baseline following the first base point in 
			//the direction of increasing index.
			getPointFromSection(base + 1, sectionType),  
			//The tip of the triangle
			getPointFromSection(tip, sectionType));
}

	
	

	/**
	 * The Class HullIterator provides an Iterator for the 
	 * hull. A limit can be set and it can iterate in both
	 * directions (clockwise and counterclockwise) on the hull.
	 */
	private class HullIterator implements IHullIterator {
		
		/**  The index of the current element. */
		int index;
		
		
		/**  The temporary index is used for the iterator functionality. */
		int tmpIndex;
		
		/** The section type. */
		ContourType sectionType;
		
		/**  The temporary section type is used for the iterator functionality. */
		ContourType tmpSectionType;

		/**
		 * Instantiates a new hull iterator.
		 *
		 * @param type represents the type of the iterator.
		 * 0 stands for the iterator starting on the left most point
		 * 1 stands for the iterator starting on the right most point
		 */
		private HullIterator(int type) {
			if(type == 0) {
				sectionType = ContourType.UPPERLEFT;
				index = 0;
			}
			if(type == 1) {
				sectionType = ContourType.LOWERRIGHT;
				index = 0;
			}
		}
		
		/**
		 * Gets the point.
		 *
		 * @return the point
		 */
		@Override
		public Point getPoint() {
			return getPointFromSection(index, sectionType);
		}

		/**
		 * Gets the next point.
		 *
		 * @return the next point
		 */
		@Override
		public Point getNextPoint() {
			if (isEmpty()) {
				return null;
			} else {
				tmpIndex = index;
				tmpSectionType = sectionType;
				if (!hasOnePoint()) {
					goNext();
					while (getPointFromSection(tmpIndex, tmpSectionType) == getPointFromSection(index, sectionType)) {
						goNext();
					}
				}
				return getPointFromSection(tmpIndex, tmpSectionType);
			}
		}
		
		/**
		 * Gets the previous point.
		 *
		 * @return the previous point
		 */
		@Override
		public Point getPreviousPoint() {
			if (isEmpty()) {
				return null;
			} else {
				tmpIndex = index;
				tmpSectionType = sectionType;
				if (!hasOnePoint()) {
					goPrevious();
					while (getPointFromSection(tmpIndex, tmpSectionType) == getPointFromSection(index, sectionType)) {
						goPrevious();
					}
				}
			}
			return getPointFromSection(tmpIndex, tmpSectionType);
		}

		/**
		 * Next.
		 */
		@Override
		public void next() {
			if (!isEmpty() && !hasOnePoint()) {
				tmpIndex = index;
				tmpSectionType = sectionType;
				goNext();
				while (getPointFromSection(tmpIndex, tmpSectionType) == getPointFromSection(index, sectionType)) {
					goNext();
				}
				index = tmpIndex;
				sectionType = tmpSectionType;
			}
		}
		
		/**
		 * Go to the next element moving in clockwise
		 * direction.
		 */
		private void goNext(){
			switch(tmpSectionType) {
				case UPPERLEFT : {
					if(tmpIndex + 1 < getSizeOfSection(tmpSectionType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfSection(ContourType.UPPERRIGHT) - 1;
					    tmpSectionType = ContourType.UPPERRIGHT;
					}
					break;
				}
				case UPPERRIGHT: {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
						tmpSectionType = ContourType.LOWERRIGHT;
					}
					break;
				}
				case LOWERRIGHT: {
					if(tmpIndex + 1 < getSizeOfSection(tmpSectionType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfSection(ContourType.LOWERLEFT) - 1;
						tmpSectionType = ContourType.LOWERLEFT;
					}
					break;
				}
				case LOWERLEFT : {
					if(tmpIndex > 0) {
						tmpIndex--;
					}
					else {
						tmpIndex = 0;
						tmpSectionType = ContourType.UPPERLEFT;
					}
					break;
				}
			}
		}
		
		/**
		 * Previous.
		 */
		@Override
		public void previous() {
			if(!isEmpty() && !hasOnePoint()) {
				tmpIndex = index;
				tmpSectionType = sectionType;
				goPrevious();
				while(getPointFromSection(tmpIndex, tmpSectionType) == getPointFromSection(index, sectionType)) {
					goPrevious();
				}
				index = tmpIndex;
				sectionType = tmpSectionType;	
			}
		}
		
		/**
		 * Go previous to the previous element moving
		 * in counterclockwise direction.
		 */
		private void goPrevious() {
			switch(tmpSectionType) {
				case UPPERLEFT : {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
					    tmpSectionType = ContourType.LOWERLEFT;
					}
					break;
				}
				case UPPERRIGHT: {
					if(tmpIndex + 1 < getSizeOfSection(tmpSectionType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfSection(ContourType.UPPERLEFT) - 1;
						tmpSectionType = ContourType.UPPERLEFT;
					}
					break;
				}
				case LOWERRIGHT: {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
						tmpSectionType = ContourType.UPPERRIGHT;
					}
					break;
				}
				case LOWERLEFT : {
					if(tmpIndex + 1< getSizeOfSection(tmpSectionType)) {
						tmpIndex++;
					}
					else {
						tmpIndex = getSizeOfSection(ContourType.LOWERRIGHT) - 1;
						tmpSectionType = ContourType.LOWERRIGHT;
					}
					break;
				}
			}
		}
	}
}
