package propra22.q8493367.shape;

import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.contour.ContourType;
import propra22.q8493367.contour.IHull;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;



// TODO: Auto-generated Javadoc
/**
 * The Class Hull represents a contour polygon or a convex hull.
 */
public class Hull implements IHull {

	/**  The points of the hull in clockwise direction in a standard cartesian coordinate system. */
	//private  List<IPoint> pointList = new ArrayList<>();

	/** The lower left section. */
	private  List<IPoint> newUpperLeft = new ArrayList<>();
	
	/** The upper left section. */
	private  List<IPoint> newLowerLeft = new ArrayList<>();
	
	/** The lower right section. */
	private  List<IPoint> newUpperRight = new ArrayList<>();
	
	/** The upper right section. */
	private  List<IPoint> newLowerRight = new ArrayList<>();

	/** The index of the right most point in the point list. */
	//private int rightMost;
	

	/**
	 * Adds a point to a section.
	 *
	 * @param point the point added
	 * @param sectionType the type of the section
	 */
	@Override
	public void addPointToSection(IPoint point, ContourType sectionType) {
		switch (sectionType) {
		case UPPERLEFT: {
			newUpperLeft.add(point);
			break;
		}
		case LOWERLEFT: {
			newLowerLeft.add(point);
			break;
		}
		case UPPERRIGHT: {
			newUpperRight.add(point);
			break;
		}
		case LOWERRIGHT: {
			newLowerRight.add(point);
			break;
		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPoint getPointFromSection(int index, ContourType sectionType) {
		switch (sectionType) {
			case UPPERLEFT: {
				return newUpperLeft.get(index);
			}
			case LOWERLEFT: {
				return newLowerLeft.get(index);
			}
			case UPPERRIGHT: {
				return newUpperRight.get(index);
			}
			case LOWERRIGHT: {
				return newLowerRight.get(index);
			}
			default: {
				return null;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePointFromSection(int index, ContourType sectionType) {
		switch (sectionType) {
		case UPPERLEFT: {
			newUpperLeft.remove(index);
			break;
		}
		case LOWERLEFT: {
			newLowerLeft.remove(index);
			break;
		}
		case UPPERRIGHT: {
			newUpperRight.remove(index);
			break;
		}
		case LOWERRIGHT: {
			newLowerRight.remove(index);
			break;
		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean sectionIsEmpty(ContourType sectionType) {
		switch (sectionType) {
		case UPPERLEFT: {
			return newUpperLeft.isEmpty();
			}
		case LOWERLEFT: {
			return newLowerLeft.isEmpty();
			}
		case UPPERRIGHT: {
			return newUpperRight.isEmpty();
			}
		case LOWERRIGHT: {
			return newLowerRight.isEmpty();

			}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSizeOfSection(ContourType sectionType) {
		switch (sectionType) {
		case UPPERLEFT: {
			return newUpperLeft.size();
			}
		case LOWERLEFT: {
			return newLowerLeft.size();
			}
		case UPPERRIGHT: {
			return newUpperRight.size();
			}
		case LOWERRIGHT: {
			return newLowerRight.size();

			}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		newUpperLeft.clear();
		newLowerLeft.clear();
		newUpperRight.clear();
		newLowerRight.clear();
		//pointList.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePointFromSection(IPoint point, ContourType sectionType) {
		switch (sectionType) {
		case UPPERLEFT: {
			newUpperLeft.remove(point);
		}
		case LOWERLEFT: {
			newLowerLeft.remove(point);
		}
		case UPPERRIGHT: {
			newUpperRight.remove(point);
		}
		case LOWERRIGHT: {
			newLowerRight.remove(point);

		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[][] toArray() {
		 List<IPoint> pointList = toList();
		 int[][] pointArr = new int[pointList.size()][2];
		 for(int i = 0; i < pointList.size(); i++) {
			 pointArr[i][0] = pointList.get(i).getX();
			 pointArr[i][1] = pointList.get(i).getY(); 
		 }
		 return pointArr;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IPoint> toList() {
		List<IPoint> pointList = new ArrayList<>();
		IHullIterator it = getLeftIt();
		IPoint start = it.getPoint();
		do {
			pointList.add(it.getPoint());
			it.next();
		} while (it.getPoint() != start);
		return pointList;
	}

	
	/*
	@Override
	public void createList() {
		IHullIterator it = getLeftIt();
		IPoint start = it.getPoint();
		it.next();
		while(it.getPoint() != start) {
			pointList.add(it.getPoint());
			it.next();
		}
	}
	*/

	/**
	 * {@inheritDoc}
	 */
	/*
	@Override
	public int getIndexOfRightMostPoint() {
		return rightMost;
	}
	*/

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return getSizeOfSection(ContourType.UPPERLEFT) == 0;
	}
    
	/**
	 *{@inheritDoc}
	 */
	
	/*
	@Override
	public IPoint get(int index) {
		return pointList.get(index);
	}
	*/
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IHullIterator getLeftIt() {
		return new HullIterator(0);
	}
	
	/**
	 * Gets the right it.
	 *
	 * @return the right it
	 */
	public IHullIterator getRightIt() {
		return new HullIterator(1);
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public boolean contains(IPoint p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContourType getSection(IPoint p) {
		// TODO Auto-generated method stub
		return null;
	}
	

    
	
	/**
	 * The Class HullIterator provides an Iterator for the 
	 * hull. A limit can be set and it can iterate in both
	 * directions (clockwise and counterclockwise) on the hull.
	 */
	private class HullIterator implements IHullIterator {
		
		/**  The index of the current element. */
		int index;
		
		
		/** The temporary index is used for the iterator functionality*/
		int tmpIndex;
		
		/** The section type. */
		ContourType sectionType;
		
		/** The temporary section type is used for the iterator functionality*/
		ContourType tmpSectionType;

		/**
		 * Instantiates a new hull iterator.
		 *
		 * @param type represents the type of the iterator.
		 * 0 stands for the iterator starting on the left most point
		 * 1 stands for the iterator starting on the right most point
		 */
		public HullIterator(int type) {
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
		 * Gets the point which is represented
		 * by the actual position of the iterator.
		 *
		 * @return the point
		 */
		@Override
		public IPoint getPoint() {
			return getPointFromSection(index, sectionType);
		}

		/**
		 * Gets the point after the actual 
		 * position of the iterator.
		 *
		 * @return the point after the actual position
		 * of the iterator
		 */
		
		// clockwise!!
		@Override
		public IPoint getNextPoint() {
			tmpIndex = index;
			tmpSectionType = sectionType; 
			goNext();
		     while(getPointFromSection(tmpIndex, tmpSectionType) == getPointFromSection(index, sectionType)) {
		    	 goNext();
		     }
		     return getPointFromSection(tmpIndex, tmpSectionType);
		}
		
		/**
		 * Gets the previous point.
		 *
		 * @return the previous point
		 */
		@Override
		public IPoint getPreviousPoint() {
			tmpIndex = index;
			tmpSectionType = sectionType; 
			goPrevious();
		     while(getPointFromSection(tmpIndex, tmpSectionType) == getPointFromSection(index, sectionType)) {
		    	 goPrevious();
		     }
		     return getPointFromSection(tmpIndex, tmpSectionType);
		}

		/**
		 * Moves the iterator to the next position.
		 */
		@Override
		public void next() {
			if(getPointFromSection(0, ContourType.UPPERLEFT) != getPointFromSection(0, ContourType.LOWERRIGHT)) {
				tmpIndex = index;
				tmpSectionType = sectionType; 
				goNext();
				while(getPointFromSection(tmpIndex, tmpSectionType) == getPointFromSection(index, sectionType)) {
					goNext();
				}
				index = tmpIndex;
				sectionType = tmpSectionType;
			}	
		}
		
		/**
		 * Go next.
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
		 * Moves the iterator to the previous position.
		 */
		@Override
		public void previous() {
			if(getPointFromSection(0, ContourType.UPPERLEFT) != getPointFromSection(0, ContourType.LOWERRIGHT)) {
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
		 * Go previous.
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

		/**
		 * Checks for reached limit.
		 *
		 * @return true, if if the iterator has reached 
		 * the limit, false otherwise.
		 */
	}

	/*
	@Override
	public  long AngleComparisonTest(IHullIterator aIterator, IHullIterator bIterator) {
		long xTip = (long) aIterator.getPoint().getX() + (long) bIterator.getPoint().getX()
				- (long) bIterator.getNextPoint().getX();
		long yTip = (long) aIterator.getPoint().getY() + (long) bIterator.getPoint().getY()
				- (long) bIterator.getNextPoint().getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return  Point.signedTriangleArea(aIterator.getPoint(), aIterator.getNextPoint(), tip);
	}
	*/
	
	
}
