package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.point.IPoint;

public class Hull implements IHull {
    
	private List<IPoint> lowerLeftSection = new ArrayList<>();
    private List<IPoint> upperLeftSection = new ArrayList<>();
    private List<IPoint> lowerRightSection = new ArrayList<>();
    private List<IPoint> upperRightSection = new ArrayList<>();
	private boolean lowerSectionsMeet;
	private boolean upperSectionsMeet;
	
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
		
		
		/*
		IPoint point = null;
		int index = 0;
		int i = 0;
		int[][]  array = new int[numberOfRows()][2];
		while(index < numberOfRows() && i < getSizeOfSection(SectionType.LOWERLEFT)) {
			point = getPointFromSection(i++, SectionType.LOWERLEFT);
			array[index][0] = point.getX();
			array[index][1] = point.getY();
			index++;
		}
		
		
		int gap = 1;
		if(lowerSectionsMeet) {gap = 2;}
		i = getSizeOfSection(SectionType.LOWERRIGHT) - gap;
		
	
		while(index < numberOfRows() && i >= 0){
			point = getPointFromSection(i--, SectionType.LOWERRIGHT);
			array[index][0] = point.getX();
			array[index][1] = point.getY();
			index++;
		}
		gap = 1;
		
		rightMost = index - 1;
		
		i = 1;
		while(index < numberOfRows() && i < getSizeOfSection(SectionType.UPPERRIGHT)) {
			point = getPointFromSection(i++, SectionType.UPPERRIGHT);
			array[index][0] = point.getX();
			array[index][1] = point.getY();
			index++;
		}
		
		if(upperSectionsMeet) {gap = 2;}
		i = getSizeOfSection(SectionType.UPPERLEFT) - gap;
		while(index < numberOfRows() && i > 0) {
			point = getPointFromSection(i--, SectionType.UPPERLEFT);
			array[index][0] = point.getX();
			array[index][1] = point.getY();
			index++;
		}
		return array;
		*/
		List<IPoint> list = this.toList();
		int[][]  array = new int[list.size()][2];
		for(int i = 0; i < list.size(); i++) {
			array[i][0] = list.get(i).getX();
			array[i][1] = list.get(i).getY();
		}
		return array;
	}
    
	@Override
	public int numberOfRows() {
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
	
	
	public List<IPoint> toList() {
		List<IPoint> pointList = new ArrayList<IPoint>();
		IPoint point = null;
		int index = 0;
		int i = 0;
		while(index < numberOfRows() && i < getSizeOfSection(SectionType.LOWERLEFT)) {
			point = getPointFromSection(i++, SectionType.LOWERLEFT);
			pointList.add(point);
			index++;
		}
		
		
		int gap = 1;
		if(lowerSectionsMeet) {gap = 2;}
		i = getSizeOfSection(SectionType.LOWERRIGHT) - gap;
		
	
		while(index < numberOfRows() && i >= 0){
			point = getPointFromSection(i--, SectionType.LOWERRIGHT);
			pointList.add(point);
			index++;
		}
		gap = 1;
		
		rightMost = index - 1;
		
		i = 1;
		while(index < numberOfRows() && i < getSizeOfSection(SectionType.UPPERRIGHT)) {
			point = getPointFromSection(i++, SectionType.UPPERRIGHT);
			pointList.add(point);
			index++;
		}
		
		if(upperSectionsMeet) {gap = 2;}
		i = getSizeOfSection(SectionType.UPPERLEFT) - gap;
		while(index < numberOfRows() && i > 0) {
			point = getPointFromSection(i--, SectionType.UPPERLEFT);
			pointList.add(point);
			index++;
		}
		return pointList;
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

	
}
