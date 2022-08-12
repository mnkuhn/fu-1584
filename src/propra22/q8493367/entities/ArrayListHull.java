package propra22.q8493367.entities;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class Hull represents a contour polygon or a convex hull.
 * The underlying data structure is an array list of points.
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
		 * Returns the next point with respect to the current iterator position,
		 * moving clockwise in standard cartesian coordinate system.
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
}
