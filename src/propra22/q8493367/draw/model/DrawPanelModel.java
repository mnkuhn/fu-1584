package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import propra22.q8493367.contour.ContourPolygonCalculator;
import propra22.q8493367.contour.SectionType;
import propra22.q8493367.convex.ConvexHullCalculator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * This class implements the interface IDrawPanelModel. It provides methods to 
 */
public class DrawPanelModel implements IDrawPanelModel {
	
	/** The points. */
	private List<IPoint> points = new ArrayList<>();
	
	/** The left lower. */
	private List<IPoint> leftLower = new ArrayList<>();
	
	/** The right lower. */
	private List<IPoint> rightLower = new ArrayList<>();
	
	/** The right upper. */
	private List<IPoint> rightUpper = new ArrayList<>();
	
	/** The left upper. */
	private List<IPoint> leftUpper = new ArrayList<>();
	
	
	/**
	 * Checks for point.
	 *
	 * @param point the point
	 * @return true, if successful
	 */
	@Override
	public  boolean hasPoint(IPoint point) {
		return searchPoint(point) >= 0;
	}
	
	
	/**
	 * Adds a point to the draw panel.
	 *
	 * @param point - point which is added.
	 */
	@Override
	public void addPoint(IPoint point) {
		if(!hasPoint(point)) {points.add(point);}
		lexSort();
	}
	
	/**
	 * Removes a point from the draw panel model.
	 *
	 * @param point - the point which is removed.
	 */
	@Override
	public void removePoint(IPoint point) {
		points.remove(point);	
	}
	
	/**
	 * Removes the point with x coordinate x and y
	 * coordinate y if it is in the draw panel model.
	 *
	 * @param x the x
	 * @param y the y
	 */
	@Override
	public void removePoint(int x, int y) {
		int index = searchPoint(new Point(x, y));
		if(index >= 0) {points.remove(index);}	
	}
	
	/**
	 * Sorts the set of points in the model lexicographically.
	 */
	@Override
	public void lexSort() {
		Collections.sort(points);
	}

	/**
	 * Searches a point in the set of points in the
	 * draw panel model. If the point is in the draw panel 
	 * model, the index is returned, if it is not 
	 * in the draw panel otherwise -1.
	 *
	 * @param point - the point that is searched
	 * @return the int
	 */
	private int searchPoint(IPoint point) {
		return Collections.binarySearch(points, point);
	}
	
	/**
	 * Gets the number of points which are in the 
	 * draw panel model.
	 *
	 * @return the number of points
	 */
	@Override
	public int getNumberOfPoints() {
		return points.size();
	}
	
	/**
	 * Gets the point at the specified index.
	 *
	 * @param index the index
	 * @return the point at
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	@Override
	public IPoint getPointAt(int index) throws IndexOutOfBoundsException {
		return points.get(index);
	}
	
	/**
	 * Returns true if the point set is empty, false otherwise.
	 *
	 * @return true, if is empty
	 */
	@Override
	public boolean isEmpty() {
		return points.isEmpty();
	}
	
	
	
	/**
	 * Clears the specified section
	 *
	 * @param sectionType the section type
	 */
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
	
	/**
	 * Adds the point to section.
	 *
	 * @param point the point
	 * @param sectionType the section type
	 */
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
	
	/**
	 * Removes the section point.
	 *
	 * @param i the i
	 * @param sectionType the section type
	 */
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
	
	
	/**
	 * Gets the point from section.
	 *
	 * @param i the i
	 * @param sectionType the section type
	 * @return the point from section
	 */
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
	
	/**
	 * Section is empty.
	 *
	 * @param sectionType the section type
	 * @return true, if successful
	 */
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
	
	/**
	 * Gets the size of section.
	 *
	 * @param sectionType the section type
	 * @return the size of section
	 */
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
		
	
	/**
	 * Returns a string with all the points of the 
	 * point set.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < getNumberOfPoints(); i++) {
			  stringBuilder.append(points.get(i).toString() + "\n");
		}
		return stringBuilder.toString();
	}


	

    /**
     * Gets the section point at.
     *
     * @param i the i
     * @param sectionType the section type
     * @return the section point at
     */
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

	/**
	 * Clears the point set.
	 */
	@Override
    public void clear() {
		points.clear();	
	}
}
