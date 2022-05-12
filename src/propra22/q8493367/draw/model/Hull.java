package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.point.IPoint;

public class Hull implements IHull {
    
	private List<IPoint> pointList = new ArrayList<>();
	
	private List<IPoint> lowerLeftSection = new ArrayList<>();
    private List<IPoint> upperLeftSection = new ArrayList<>();
    private List<IPoint> lowerRightSection = new ArrayList<>();
    private List<IPoint> upperRightSection = new ArrayList<>();
	
	
	private int rightMost;
	IPoint[] diameter;
    
    @Override
    public  void addPointToSection(IPoint point, SectionType sectionType) {
    	switch (sectionType) {
			case LOWERLEFT: {
				lowerLeftSection.add(point);
				break;
			}
			case UPPERLEFT:{
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
			case UPPERLEFT:{
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
			case UPPERLEFT:{
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
			case UPPERLEFT:{
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
			case UPPERLEFT:{
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
	public void clearAllSections() {
		lowerLeftSection.clear();
		upperLeftSection.clear();
		lowerRightSection.clear();
		upperRightSection.clear();
	}

	@Override
	public void removePointFromSection(IPoint point, SectionType sectionType) {
		switch (sectionType) {
			case LOWERLEFT: {
				lowerLeftSection.remove(point);
			}
			case UPPERLEFT:{
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
		int[][]  array = new int[pointList.size()][2];
		for(int i = 0; i < pointList.size(); i++) {
			array[i][0] = pointList.get(i).getX();
			array[i][1] = pointList.get(i).getY();
		}
		return array;
	}
    
	/*
	public int numberOfElements() {
		if(lowerLeftSection.get(0) == upperRightSection.get(0)) {
			return 1;
		}
		int lower = lowerLeftSection.size();
		if(lowerLeftSection.get(lowerLeftSection.size() - 1) == lowerRightSection.get(lowerRightSection.size() - 1)) {
			lower = lower + lowerRightSection.size() - 1;
			lowerSectionsMeet = true;
		}
		else {
			lower = lower + lowerRightSection.size();
			lowerSectionsMeet = false;
		}
		
		int upper = upperRightSection.size();
		if(upperRightSection.get(upperRightSection.size() - 1) == upperLeftSection.get(upperLeftSection.size() - 1)) {
			upper = upper + upperLeftSection.size() - 1;
			upperSectionsMeet = true;	
		}
		else {
			upper = upper + upperLeftSection.size();
			upperSectionsMeet = false;
		}
		return lower + upper - 2;
	}
	*/
	
	@Override
	public void createList() {
		
		IPoint lastPoint = null;
		int index = 0;
		int i = 0;
		
		
	    while(i < getSizeOfSection(SectionType.LOWERLEFT)) {
			lastPoint = getPointFromSection(i++, SectionType.LOWERLEFT);
			pointList.add(lastPoint);
			index++;
		}
		
		
		int gap = 1;
		int sizeOfLowerRight = getSizeOfSection(SectionType.LOWERRIGHT);
		if(lastPoint == getPointFromSection(sizeOfLowerRight - 1, SectionType.LOWERRIGHT)) {
			gap = 2;
		}
		i = sizeOfLowerRight - gap;
		while(i >= 0){
			lastPoint = getPointFromSection(i--, SectionType.LOWERRIGHT);
			pointList.add(lastPoint);
			index++;
		}
		
		
		rightMost = index - 1;
		
		i = 1;
		while(i < getSizeOfSection(SectionType.UPPERRIGHT)) {
			lastPoint = getPointFromSection(i++, SectionType.UPPERRIGHT);
			pointList.add(lastPoint);
			index++;
		}
		
		gap = 1;
		int sizeOfUpperLeft = getSizeOfSection(SectionType.UPPERLEFT);
		if(lastPoint == getPointFromSection(sizeOfUpperLeft - 1, SectionType.UPPERLEFT)) {
			gap = 2;
		}
		i = sizeOfUpperLeft - gap;
		while(i > 0) {
			lastPoint = getPointFromSection(i--, SectionType.UPPERLEFT);
			pointList.add(lastPoint);
			index++;
		}
	}
	
	//only for testing
	@Override
	public void outArray() {
		System.out.println("hull as array");
		int[][] array = toArray();
		for(int i = 0; i < array.length; i++) {
			System.out.println(array[i][0] + "    y: " + array[i][1]);
		}
	}
	
	//test
	@Override
	public void outSections() {
		System.out.println("hull from the sections");
		System.out.println("lowerLeft:");
		for(IPoint point : lowerLeftSection) {
			System.out.println(point.toString());
		}
		
		System.out.println("lowerRight:");
		for(IPoint point : lowerRightSection) {
			System.out.println(point.toString());
		}
		
		System.out.println("upperRight:");
		for(IPoint point : upperRightSection) {
			System.out.println(point.toString());
		}
		System.out.println("upperLeft:");
		for(IPoint point : upperLeftSection) {
			System.out.println(point.toString());
		}
	}
	
	@Override
	public int getIndexOfRightMostPoint() {
		return rightMost;
	}

	@Override
	public IPoint[] getDiameter() {
		return diameter;
	}

	@Override
	public void setDiameter(IPoint[] diameter) {
		this.diameter = diameter;	
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
	public HullIterator getIterator(int index, int limit) {
		return new HullIterator(index, limit);
	}
	
	
	public class HullIterator {
		int index;
		int limit;
		public HullIterator(int index, int limit) {
			this.index = index;
			while(index > limit) {
				limit = limit + pointList.size();
			}
			this.limit = limit;
		}
		
		public IPoint getPoint() {
			return pointList.get(index);
		}
		
		public IPoint getNextPoint() {
			int size = pointList.size();
			return pointList.get((index + 1) % size);
		}
		
		public void next() {
			index = (index + 1) % pointList.size();
		}
		
		public int getIndex() {
			return index;
		}
	}
}
