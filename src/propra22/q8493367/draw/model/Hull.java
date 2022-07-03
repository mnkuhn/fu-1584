package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.List;
import propra22.q8493367.contour.SectionType;
import propra22.q8493367.convex.IHull;
import propra22.q8493367.point.IPoint;

public class Hull implements IHull {

	private  List<IPoint> pointList = new ArrayList<>();

	private  List<IPoint> lowerLeftSection = new ArrayList<>();
	private  List<IPoint> upperLeftSection = new ArrayList<>();
	private  List<IPoint> lowerRightSection = new ArrayList<>();
	private  List<IPoint> upperRightSection = new ArrayList<>();

	private int rightMost;
	

	@Override
	public void addPointToSection(IPoint point, SectionType sectionType) {
		switch (sectionType) {
		case LOWERLEFT: {
			lowerLeftSection.add(point);
			break;
		}
		case UPPERLEFT: {
			upperLeftSection.add(point);
			break;
		}
		case LOWERRIGHT: {
			lowerRightSection.add(point);
			break;
		}
		case UPPERRIGHT: {
			upperRightSection.add(point);
			break;
		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}

	@Override
	public IPoint getPointFromSection(int index, SectionType sectionType) {
		switch (sectionType) {
			case LOWERLEFT: {
				return lowerLeftSection.get(index);
			}
			case UPPERLEFT: {
				return upperLeftSection.get(index);
			}
			case LOWERRIGHT: {
				return lowerRightSection.get(index);
			}
			case UPPERRIGHT: {
				return upperRightSection.get(index);
			}
			default: {
				return null;
			}
		}
	}

	@Override
	public void removePointFromSection(int index, SectionType sectionType) {
		switch (sectionType) {
		case LOWERLEFT: {
			lowerLeftSection.remove(index);
			break;
		}
		case UPPERLEFT: {
			upperLeftSection.remove(index);
			break;
		}
		case LOWERRIGHT: {
			lowerRightSection.remove(index);
			break;
		}
		case UPPERRIGHT: {
			upperRightSection.remove(index);
			break;
		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}

	@Override
	public boolean sectionIsEmpty(SectionType sectionType) {
		switch (sectionType) {
		case LOWERLEFT: {
			return lowerLeftSection.isEmpty();
			}
		case UPPERLEFT: {
			return upperLeftSection.isEmpty();
			}
		case LOWERRIGHT: {
			return lowerRightSection.isEmpty();
			}
		case UPPERRIGHT: {
			return upperRightSection.isEmpty();

			}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
			}
		}
	}

	@Override
	public int getSizeOfSection(SectionType sectionType) {
		switch (sectionType) {
		case LOWERLEFT: {
			return lowerLeftSection.size();
			}
		case UPPERLEFT: {
			return upperLeftSection.size();
			}
		case LOWERRIGHT: {
			return lowerRightSection.size();
			}
		case UPPERRIGHT: {
			return upperRightSection.size();

			}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
			}
		}
	}

	@Override
	public void clear() {
		lowerLeftSection.clear();
		upperLeftSection.clear();
		lowerRightSection.clear();
		upperRightSection.clear();
		pointList.clear();
	}

	@Override
	public void removePointFromSection(IPoint point, SectionType sectionType) {
		switch (sectionType) {
		case LOWERLEFT: {
			lowerLeftSection.remove(point);
		}
		case UPPERLEFT: {
			upperLeftSection.remove(point);
		}
		case LOWERRIGHT: {
			lowerRightSection.remove(point);
		}
		case UPPERRIGHT: {
			upperRightSection.remove(point);

		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + sectionType);
		}
		}
	}

	@Override
	public int[][] toArray() {
		int[][] array = new int[pointList.size()][2];
		for (int i = 0; i < pointList.size(); i++) {
			array[i][0] = pointList.get(i).getX();
			array[i][1] = pointList.get(i).getY();
		}
		return array;
	}
	
	@Override
	public List<IPoint> toList(){
		return pointList;
	}


	@Override
	public void createList() {
		//System.out.println("Hull -> createList()");
		pointList.clear();
		IPoint lastPoint = null;
		int index = 0;
		int i = 0;
		// One point only
		if (getPointFromSection(0, SectionType.LOWERLEFT) == getPointFromSection(0, SectionType.UPPERRIGHT)) {
			pointList.add(getPointFromSection(0, SectionType.LOWERLEFT));
			// More than one point
		} else {
			while (i < getSizeOfSection(SectionType.LOWERLEFT)) {
				lastPoint = getPointFromSection(i++, SectionType.LOWERLEFT);
				pointList.add(lastPoint);
				index++;
			}

			int gap = 1;
			int sizeOfLowerRight = getSizeOfSection(SectionType.LOWERRIGHT);
			IPoint nextPoint = getPointFromSection(sizeOfLowerRight - 1, SectionType.LOWERRIGHT);
			if (lastPoint.equals(nextPoint)) {
				gap = 2;
			}
			i = sizeOfLowerRight - gap;
			while (i >= 0) {
				lastPoint = getPointFromSection(i--, SectionType.LOWERRIGHT);
				pointList.add(lastPoint);
				index++;
			}

			rightMost = index - 1;

			i = 1;

			while (i < getSizeOfSection(SectionType.UPPERRIGHT)) {
				lastPoint = getPointFromSection(i++, SectionType.UPPERRIGHT);
				pointList.add(lastPoint);
				index++;
			}

			gap = 1;
			int sizeOfUpperLeft = getSizeOfSection(SectionType.UPPERLEFT);
			nextPoint = getPointFromSection(sizeOfUpperLeft - 1, SectionType.UPPERLEFT);
			if (lastPoint.equals(nextPoint)) {
				gap = 2;
			}
			i = sizeOfUpperLeft - gap;
			while (i > 0) {
				lastPoint = getPointFromSection(i--, SectionType.UPPERLEFT);
				pointList.add(lastPoint);
				index++;
			}
			IPoint firstPoint = getPointFromSection(0, SectionType.UPPERLEFT);
			if (lastPoint.equals(firstPoint)) {
				pointList.remove(index - 1);
			}
		}
	}


	@Override
	public int getIndexOfRightMostPoint() {
		return rightMost;
	}

	@Override
	public int size() {
		return pointList.size();
	}

	@Override
	public IPoint get(int index) {
		return pointList.get(index);
	}

	@Override
	public IHullIterator getIterator(int index) {
		return new HullIterator(index);
	}
	
	@Override
	public boolean contains(IPoint p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public SectionType getSection(IPoint p) {
		// TODO Auto-generated method stub
		return null;
	}
	

    
	
	private class HullIterator implements IHullIterator {
		int index;
		int limit;

		public HullIterator(int index) {
			this.index = index;
		}
		
		@Override
		public void setLimit(int limit) {
			while (index > limit) {
				limit = limit + pointList.size();
			}
			this.limit = limit;
		}

		@Override
		public IPoint getPoint() {
			int size = pointList.size();
			return pointList.get(index % size);
		}

		@Override
		public IPoint getNextPoint() {
			int size = pointList.size();
			return pointList.get((index + 1) % size);
		}

		@Override
		public void next() {
			index++;
		}
		
		@Override
		public void previous() {
			index--;
		}

		@Override
		public boolean hasReachedLimit() {
			return index >= limit;
		}

		@Override
		public int getIndex() {
			int size = pointList.size();
			return index % size;
		}
	}



	


	
}
