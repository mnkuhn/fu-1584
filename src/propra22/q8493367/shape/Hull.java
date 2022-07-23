package propra22.q8493367.shape;

import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.contour.ContourType;
import propra22.q8493367.contour.IHull;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;


// TODO: Auto-generated Javadoc
/**
 * The Class Hull represents a contour polygon or a convex hull.
 */
public class Hull implements IHull {

	/**  The points of the hull in clockwise direction in a standard cartesian coordinate system. */
	private  List<IPoint> pointList = new ArrayList<>();

	/** The lower left section. */
	private  List<IPoint> newUpperLeft = new ArrayList<>();
	
	/** The upper left section. */
	private  List<IPoint> newLowerLeft = new ArrayList<>();
	
	/** The lower right section. */
	private  List<IPoint> newUpperRight = new ArrayList<>();
	
	/** The upper right section. */
	private  List<IPoint> newLowerRight = new ArrayList<>();

	/** The index of the right most point in the point list. */
	private int rightMost;
	

	/**
	 * Adds a point to a section.
	 *
	 * @param point the point added
	 * @param sectionType the type of the section
	 */
	@Override
	public void addPointToSection(IPoint point, ContourType sectionType) {
		switch (sectionType) {
		case NEWUPPERLEFT: {
			newUpperLeft.add(point);
			break;
		}
		case NEWLOWERLEFT: {
			newLowerLeft.add(point);
			break;
		}
		case NEWUPPERRIGHT: {
			newUpperRight.add(point);
			break;
		}
		case NEWLOWERRIGHT: {
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
			case NEWUPPERLEFT: {
				return newUpperLeft.get(index);
			}
			case NEWLOWERLEFT: {
				return newLowerLeft.get(index);
			}
			case NEWUPPERRIGHT: {
				return newUpperRight.get(index);
			}
			case NEWLOWERRIGHT: {
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
		case NEWUPPERLEFT: {
			newUpperLeft.remove(index);
			break;
		}
		case NEWLOWERLEFT: {
			newLowerLeft.remove(index);
			break;
		}
		case NEWUPPERRIGHT: {
			newUpperRight.remove(index);
			break;
		}
		case NEWLOWERRIGHT: {
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
		case NEWUPPERLEFT: {
			return newUpperLeft.isEmpty();
			}
		case NEWLOWERLEFT: {
			return newLowerLeft.isEmpty();
			}
		case NEWUPPERRIGHT: {
			return newUpperRight.isEmpty();
			}
		case NEWLOWERRIGHT: {
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
		case NEWUPPERLEFT: {
			return newUpperLeft.size();
			}
		case NEWLOWERLEFT: {
			return newLowerLeft.size();
			}
		case NEWUPPERRIGHT: {
			return newUpperRight.size();
			}
		case NEWLOWERRIGHT: {
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
		pointList.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePointFromSection(IPoint point, ContourType sectionType) {
		switch (sectionType) {
		case NEWUPPERLEFT: {
			newUpperLeft.remove(point);
		}
		case NEWLOWERLEFT: {
			newLowerLeft.remove(point);
		}
		case NEWUPPERRIGHT: {
			newUpperRight.remove(point);
		}
		case NEWLOWERRIGHT: {
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

	/**
	 * {@inheritDoc}
	 */
	
	/*
	@Override
	public void createList() {
		//System.out.println("Hull -> createList()");
		pointList.clear();
		IPoint lastPoint = null;
		int index = 0;
		int i = 0;
		// One point only
		if (getPointFromSection(0, SectionType.NEWUPPERLEFT) == getPointFromSection(0, SectionType.NEWLOWERRIGHT)) {
			pointList.add(getPointFromSection(0, SectionType.NEWUPPERLEFT));
			// More than one point
		} else {
			while (i < getSizeOfSection(SectionType.NEWUPPERLEFT)) {
				lastPoint = getPointFromSection(i++, SectionType.NEWUPPERLEFT);
				pointList.add(lastPoint);
				index++;
			}

			int gap = 1;
			int sizeOfLowerRight = getSizeOfSection(SectionType.NEWUPPERRIGHT);
			IPoint nextPoint = getPointFromSection(sizeOfLowerRight - 1, SectionType.NEWUPPERRIGHT);
			if (lastPoint.equals(nextPoint)) {
				gap = 2;
			}
			i = sizeOfLowerRight - gap;
			while (i >= 0) {
				lastPoint = getPointFromSection(i--, SectionType.NEWUPPERRIGHT);
				pointList.add(lastPoint);
				index++;
			}

			rightMost = index - 1;

			i = 1;

			while (i < getSizeOfSection(SectionType.NEWLOWERRIGHT)) {
				lastPoint = getPointFromSection(i++, SectionType.NEWLOWERRIGHT);
				pointList.add(lastPoint);
				index++;
			}

			gap = 1;
			int sizeOfUpperLeft = getSizeOfSection(SectionType.NEWLOWERLEFT);
			nextPoint = getPointFromSection(sizeOfUpperLeft - 1, SectionType.NEWLOWERLEFT);
			if (lastPoint.equals(nextPoint)) {
				gap = 2;
			}
			i = sizeOfUpperLeft - gap;
			while (i > 0) {
				lastPoint = getPointFromSection(i--, SectionType.NEWLOWERLEFT);
				pointList.add(lastPoint);
				index++;
			}
			IPoint firstPoint = getPointFromSection(0, SectionType.NEWLOWERLEFT);
			if (lastPoint.equals(firstPoint)) {
				pointList.remove(index - 1);
			}
		}
	}
    */
	
	@Override
	public void createList() {
		IHullIterator it = getLeftIt();
		IPoint start = it.getPoint();
		while(it.getPoint() != start) {
			pointList.add(it.getPoint());
			it.next();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getIndexOfRightMostPoint() {
		return rightMost;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean empty() {
		return getSizeOfSection(ContourType.NEWUPPERLEFT) == 0;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public IPoint get(int index) {
		return pointList.get(index);
	}

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
				sectionType = ContourType.NEWUPPERLEFT;
				index = 0;
			}
			if(type == 1) {
				sectionType = ContourType.NEWLOWERRIGHT;
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
			if(getPointFromSection(0, ContourType.NEWUPPERLEFT) != getPointFromSection(0, ContourType.NEWLOWERRIGHT)) {
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
				case NEWUPPERLEFT : {
					if(tmpIndex + 1 < getSizeOfSection(tmpSectionType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfSection(ContourType.NEWUPPERRIGHT) - 1;
					    tmpSectionType = ContourType.NEWUPPERRIGHT;
					}
					break;
				}
				case NEWUPPERRIGHT: {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
						tmpSectionType = ContourType.NEWLOWERRIGHT;
					}
					break;
				}
				case NEWLOWERRIGHT: {
					if(tmpIndex + 1 < getSizeOfSection(tmpSectionType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfSection(ContourType.NEWLOWERLEFT) - 1;
						tmpSectionType = ContourType.NEWLOWERLEFT;
					}
					break;
				}
				case NEWLOWERLEFT : {
					if(tmpIndex > 0) {
						tmpIndex--;
					}
					else {
						tmpIndex = 0;
						tmpSectionType = ContourType.NEWUPPERLEFT;
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
			if(getPointFromSection(0, ContourType.NEWUPPERLEFT) != getPointFromSection(0, ContourType.NEWLOWERRIGHT)) {
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
				case NEWUPPERLEFT : {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
					    tmpSectionType = ContourType.NEWLOWERLEFT;
					}
					break;
				}
				case NEWUPPERRIGHT: {
					if(tmpIndex + 1 < getSizeOfSection(tmpSectionType)) {
						tmpIndex++;
					} else {
						tmpIndex = getSizeOfSection(ContourType.NEWUPPERLEFT) - 1;
						tmpSectionType = ContourType.NEWUPPERLEFT;
					}
					break;
				}
				case NEWLOWERRIGHT: {
					if(tmpIndex > 0) {
						tmpIndex--;
					} else {
						tmpIndex = 0;
						tmpSectionType = ContourType.NEWUPPERRIGHT;
					}
					break;
				}
				case NEWLOWERLEFT : {
					if(tmpIndex + 1< getSizeOfSection(tmpSectionType)) {
						tmpIndex++;
					}
					else {
						tmpIndex = getSizeOfSection(ContourType.NEWLOWERRIGHT) - 1;
						tmpSectionType = ContourType.NEWLOWERRIGHT;
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
