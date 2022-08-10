package propra22.q8493367.entities;

import java.util.List;
import java.util.ListIterator;


/**
 * The Class ListHull is an abstract class for a Hull
 * based on a List data structure.
 */
public abstract class ListHull implements IHull{
	
	/** The upper left contour. */
	protected  List<Point> upperLeft;
	
	/** The lower left contour. */
	protected  List<Point> lowerLeft;
	
	/** The lower right contour. */
	protected  List<Point> upperRight;
	
	/** The upper right contour. */
	protected  List<Point> lowerRight;
	
	/** The biggest y coordinate of a point 
	 * found during the scan from
	 * left to right.
	 */
	protected int biggestYFound;
	
	/** 
	 * The smallest y coordinate of a point 
	 * found found during the scan
	 * from left to right. 
	 * 
	 */
	protected int smallestYFound;
	

	@Override
	public  void clear() {
		upperLeft.clear();
		lowerLeft.clear();
		upperRight.clear();
		lowerRight.clear();
	}

	@Override
	public  boolean isEmpty() {
		return upperLeft.size() == 0;
	}

	
	@Override
	public abstract int[][] toArray();

	
	@Override
	public abstract List<Point> toList();

	
	@Override
	public abstract IHullIterator leftIterator();

	
	@Override
	public abstract IHullIterator rightIterator();

	
	@Override
	public  boolean hasOnePoint() {
		return upperLeft.get(0) == lowerRight.get(0);
	}
	
	
	/**
	 * Calculates the contour specified by the contour type
	 * of the convex hull.
	 *
	 * @param contourType the contour type
	 */
	//protected abstract void cleanContour(ContourType contourType);

	
	@Override
	public abstract void set(PointSet pointSet);

	
	@Override
	public void clean() {
		if (!isEmpty()) {
			for (ContourType contourType : ContourType.values()) {
				cleanContour(contourType);
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
	protected boolean contourIsEmpty(ContourType contourType) {
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
	 * @param contourType the type of the contour
	 * @return the number of points in the contour
	 */
	
	protected int getSizeOfContour(ContourType contourType) {
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
	 * Calculates the specified contour of the convex hull
	 * using the DFV algorithm.
	 * @param contourType the type of the contour
	 */
	
	protected void cleanContour(ContourType contourType) {
		if (!contourIsEmpty(contourType)) {
			if (getSizeOfContour(contourType) >= 3) {
				ListIterator<Point> it = getIterator(contourType);

				Point baseA;
				Point baseB;
				Point tip;

				while (it.hasNext()) {
                    // Get baseA, baseB and tip
					baseA = it.next();

					if (it.hasNext()) baseB = it.next();
					else break;

					if (it.hasNext()) tip = it.next();
					else break;

					it.previous();
					it.previous();

					// tip is on the outer  side of the line through baseA and baseB or on the 
					//line through baseA and baseB
					if ((contourType.getSign() * Point.signedTriangleArea(baseA, baseB, tip) <= 0)) {
						
						it.remove();
						baseB = it.previous();

						if (it.hasPrevious()) {
							baseA = it.previous();
							it.next();
							it.next();
							it.previous();
							while (contourType.getSign() * Point.signedTriangleArea(baseA, baseB, tip) <= 0) {
								it.remove();
								baseB = it.previous();
								if(it.hasPrevious()) {
									baseA = it.previous();
								}else {
									break;
								}
								it.next();
								it.next();
								it.previous();
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns the iterator
	 * for the specified contour.
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
}
