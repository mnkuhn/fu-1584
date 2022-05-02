package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.point.IPoint;

public class Hull implements IHull {
    
	private List<IPoint> lowerLeftSection = new ArrayList<>();
    private List<IPoint> upperLeftSection = new ArrayList<>();
    private List<IPoint> lowerRightSection = new ArrayList<>();
    private List<IPoint> upperRightSection = new ArrayList<>();
    
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
    
	/*
    @Override
	public void addPointToLowerLeftSection(IPoint p) {
		lowerLeftSection.add(p);	
	}

	@Override
	public void addPointToUpperLeftSection(IPoint p) {
		upperLeftSection.add(p);
		
	}

	@Override
	public void addPointToLowerRightSection(IPoint p) {
		lowerRightSection.add(p);
	}

	@Override
	public void addPointToUpperRightSection(IPoint p) {
		upperRightSection.add(p);	
	}
	
	*/
    
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
	
	
	/*
	
	//@Override
	public IPoint getPointFromLowerLeftSection(int index){
		return lowerLeftSection.get(index);
	}
	
	//@Override
	public IPoint getPointFromUpperLeftSection(int index){
		return upperLeftSection.get(index);
	}
	
	//@Override
	public IPoint getPointFromLowerRightSection(int index){
		return lowerRightSection.get(index);
	}
	
	//@Override
	public IPoint getPointUpperRightLeftSection(int index){
		return upperRightSection.get(index);
	}
	
	public void removePointFromLowerLeftSection(IPoint point) {
		lowerLeftSection.remove(point);
	}
	
	*/
	
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
	
	
	/*
	public void removePointFromUpperLeftSection(IPoint point) {
		upperLeftSection.remove(point);
	
	}
	
	public void removePointFromLowerRightSection(IPoint point) {
		lowerRightSection.remove(point);
	}
	
	public void removePointFromUpperRightSection(IPoint point) {
		upperRightSection.remove(point);
	}
	
	
	public boolean isEmpty() {
		return lowerLeftSection.isEmpty() && upperLeftSection.isEmpty() && lowerRightSection.isEmpty()
				&& upperRightSection.isEmpty();
	}
	
	*/
	
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
}
