package entities;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class ArrayListHull represents a contour polygon 
 * or a convex hull. The underlying data structure is an 
 * array list of points. It provides a method which 
 * returns a HullIterator for cyclic reading.
 * 
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
	 * hull in both directions. The hull can not be modified with this iterator,
	 * it is for reading only.
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
		 * of the convex hull.
		 * 1 stands for the iterator starting on the right most point 
		 * of the convex hull.
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
		 * Returns the next point with respect to the current iterator position,
		 * moving clockwise in a standard cartesian coordinate system.
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
		 * Gets the previous point with respect to the current iterator position,
		 * moving counterclockwise in a standard cartesian coordinate system.
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
		 * Moves the iterator to the next point moving clockwise 
		 * in a standard cartesian coordinate system.
		 * 
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
		 * direction in a standard cartesian coordinate system. 
		 * It has to be checked, if this new temporary iterator
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
		 * standard cartesian coordinate system.
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
		 * direction in a standard cartesian coordinate system. 
		 * It has to be checked, if this new temporary iterator
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
	
    @Override
    /** 
     * Another version of the cleanContour method. It works without
     * iterators and was about 2ms faster than the method in the inheriting
     * class in a test row of 50 times all the 26 tests.
     *  
     */
	protected void cleanContour(ContourType contourType) {
		
		if(!contourIsEmpty(contourType)) {
			int size = getSizeOfContour(contourType);
			if(size >= 3) {
				int base = 0;
				int tip = 2;
				while(tip < size) {
					/*tip lies on side of the line base--base + 1 which is facing 
					 * towards the contour polygon
					 */
					if(signedTriangleArea(base, tip, contourType)  > 0){
						base++;
						tip++;	
					}
					else {
						/*tip lies on side of the line base--base + 1 which is facing 
						 * away from the contour polygon
						 */
						if(base > 0) {
							removePointFromContour(base + 1, contourType);
							size--;
							base--;
							tip--;
							if(tip < size) {
								// Stepping backwards
								while(base > 0 && signedTriangleArea(base, tip, contourType) < 0) {
									removePointFromContour(base + 1, contourType);
									size--;
									base--;
									tip--;	
								}
							}
						}
						// Stepping backwards not possible
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
	 * @param contourType the type of the contour
	 * @return the twice the area of the triangle with sign
	 */
	private long signedTriangleArea(int base, int tip, ContourType contourType) {
		return contourType.getSign() * Point.signedTriangleArea(
				//the first point of the baseline
				getPointFromContour(base, contourType), 
				//The second point of the baseline following the first base point in 
				//the direction of increasing index.
				getPointFromContour(base + 1, contourType),  
				//The tip of the triangle
				getPointFromContour(tip, contourType));
	}
	
	/**
	 * Removes the point with the index i from
	 * the contour specified by the contour type.
	 *
	 * @param i the index of the point in the contour.
	 * @param contourType the type of the contour
	 */
	private void removePointFromContour(int i, ContourType contourType) {
		switch (contourType) {
		case UPPERLEFT: {
			upperLeft.remove(i);
			break;
		}
		case LOWERLEFT: {
			lowerLeft.remove(i);
			break;
		}
		case UPPERRIGHT: {
			upperRight.remove(i);
			break;
		}
		case LOWERRIGHT: {
			lowerRight.remove(i);
			break;
		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + contourType);
		}
		}
	}
}
