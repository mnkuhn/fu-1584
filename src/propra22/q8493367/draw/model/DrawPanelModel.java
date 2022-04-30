package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import propra22.q8493367.contour.ContourPolygonCalculator;
import propra22.q8493367.contour.SectionType;
import propra22.q8493367.convex.ConvexHullCalculator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class DrawPanelModel implements IDrawPanelModel {
	private List<IPoint> points = new ArrayList<>();
	
	private List<IPoint> leftLower = new ArrayList<>();
	private List<IPoint> rightLower = new ArrayList<>();
	private List<IPoint> rightUpper = new ArrayList<>();
	private List<IPoint> leftUpper = new ArrayList<>();
	
	
	@Override
	public  boolean hasPoint(int x, int y) {
		return searchPoint(x, y) > 0;
	}
	
	@Override
	public void addPoint(int x, int y) {
		points.add(new Point(x, y));
	}
	
	@Override
	public void removePoint(IPoint point) {
		points.remove(point);
		
	}
	
	@Override
	public void removePoint(int x, int y) {
		int index = searchPoint(x, y);
		points.remove(index);	
	}
	
	@Override
	public void lexSort() {
		Collections.sort(points);
	}

	
	// das klappt vermutlich nicht.. oder doch?
	private int searchPoint(int x, int y) {
		return Collections.binarySearch(points, new Point(x, y));
	}
	
	@Override
	public int getNumberOfPoints() {
		return points.size();
	}
	
	@Override
	public IPoint getPointAt(int index) throws IndexOutOfBoundsException {
		return points.get(index);
	}
	
	@Override
	public boolean isEmpty() {
		return points.isEmpty();
	}
	
	
	
	@Override
	public void clearSection(SectionType sectionType) {
		switch(sectionType) {
		  case LOWERLEFT:
		    leftLower.clear();
		  case LOWERRIGHT:
		    rightLower.clear();
		  case UPPERRIGHT:
			 rightUpper.clear(); 
		  case UPPERLEFT:
			  leftUpper.clear();
		}	
	}
	
	@Override 
	public void addPointToSection(IPoint point, SectionType sectionType){
		switch(sectionType) {
		  case LOWERLEFT:
		    leftLower.add(point);
		    break;
		  case LOWERRIGHT:
		    rightLower.add(point);
		    break;
		  case UPPERRIGHT:
			 rightUpper.add(point);
			 break;
		  case UPPERLEFT:
			 leftUpper.add(point);
			 break;
			 
			  
		}	
	}
	
	@Override
	public void removeSectionPoint(int i, SectionType sectionType) {
		switch(sectionType) {
		  case LOWERLEFT:
		    leftLower.remove(i);
		    break;
		  case LOWERRIGHT:
		    rightLower.remove(i);
		    break;
		  case UPPERRIGHT:
			rightUpper.remove(i);
			break;
		  case UPPERLEFT:
			leftUpper.remove(i);
			break;
		}	
	}
	
	
	@Override
	public IPoint getPointFromSection(int i, SectionType sectionType) {
		switch(sectionType) {
		  case LOWERLEFT:
		    return leftLower.get(i);
		  case LOWERRIGHT:
		    return rightLower.get(i);
		  case UPPERRIGHT:
			 return rightUpper.get(i); 
		  case UPPERLEFT:
			  return leftUpper.get(i);
		  default:
			  return null;
		}	
	}
	
	@Override 
	public boolean sectionIsEmpty(SectionType sectionType) {
		switch(sectionType) {
		  case LOWERLEFT:
		    return leftLower.isEmpty();
		  case LOWERRIGHT:
		    return rightLower.isEmpty();
		  case UPPERRIGHT:
			 return rightUpper.isEmpty(); 
		  case UPPERLEFT:
			  return leftUpper.isEmpty();
		  default:
			  return false;
		}		  
	}
	
	@Override
	public int getSizeOfSection(SectionType sectionType) {
		switch(sectionType) {
		  case LOWERLEFT :
		    return leftLower.size();
		  case LOWERRIGHT:
			  return rightLower.size();
		  case UPPERRIGHT:
			  return rightUpper.size();
		  case UPPERLEFT:
			  return leftUpper.size();
		  default:
			  return -1;
		}
	}
		
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < getNumberOfPoints(); i++) {
			  stringBuilder.append(points.get(i).toString() + "\n");
		}
		return stringBuilder.toString();
	}


	

    @Override
	public IPoint getSectionPointAt(int i, SectionType sectionType) {
		switch(sectionType) {
		  case LOWERLEFT :
		    return leftLower.get(i);
		  case LOWERRIGHT:
			  return rightLower.get(i);
		  case UPPERRIGHT:
			  return rightUpper.get(i);
		  case UPPERLEFT:
			  return leftUpper.get(i);
		  default:
			  return null;
		}
	}

	@Override
    public void clear() {
		points.clear();	
	}

	@Override
	public void addPoint(IPoint point) {
		points.add(point);
		
	}	
}
