package propra22.q8493367.convex;

import propra22.q8493367.contour.IHullCalculator;
import propra22.q8493367.contour.SectionType;
import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.point.IPoint;


// TODO: Auto-generated Javadoc
/**
 * The Class ConvexHullCalculator is considered to be part of the controller of
 * the draw panel. It calcultes the convex hull outgoing from the points of 
 * the contour polygon.
 */
public class ConvexHullCalculator implements IHullCalculator {
	
	/** The model. */
	private IHull hull;
	
	/**
	 * Instantiates a new convex hull calculator.
	 *
	 * @param model - the draw panel model.
	 */
	public ConvexHullCalculator(IHull hull) {
		this.hull = hull;
	}
	
	/**
	 * Calculates one of the four sections.
	 *
	 * @param sectionType the section type
	 */
	@Override
	public void calculateSection(SectionType sectionType) {
		if(!hull.sectionIsEmpty(sectionType)) {
			int size = hull.getSizeOfSection(sectionType);
			System.out.println("ConveXHullCalculator calculateSection size of section: "+ sectionType.toString() + " " + size) ;
			if(size >= 3) {
				int next = 2;
				int leadingBase = 1;
				int followingBase = 0;
				
				while(next < size) {
					IPoint a = hull.getPointFromSection(followingBase, sectionType);
					IPoint b = hull.getPointFromSection(leadingBase, sectionType);
					IPoint c = hull.getPointFromSection(next, sectionType);
					
					
					if( DFV(a, b, c, sectionType)  < 0){
						followingBase++;
						leadingBase++;
						next++;
					}
					
					else {
						hull.removePointFromSection(leadingBase, sectionType);
		                size--;
						if(followingBase > 0) {
							next--;
							leadingBase--;
							followingBase--;
							while(followingBase > 0 && DFV(hull.getPointFromSection(followingBase, sectionType), hull.getPointFromSection(leadingBase, sectionType), 
								hull.getPointFromSection(next, sectionType), sectionType) > 0){
								hull.removePointFromSection(leadingBase, sectionType);
								size--;
								followingBase--;
								leadingBase--;
								
								// nicht so schön..
								if(next == size) {break;}
							}	
						}			
					}	
				}
			}
		}
	}
		
	
	
	/**
	 * This is the DFV function which calculates the signed area of a triangle. Depending
	 * of the calculates section, area is positive or negative, if the tip of the triangle is
	 * above or below line which goes through the baseline of the triangle. 
	 *
	 * @param a - base point of the triangle
	 * @param b - base point of the triangle
	 * @param c - tip of the triangle
	 * @param sectionType - the section type which determines the sign for the calculated area
	 * @return signed area of the triangle
	 */
	private  long DFV(IPoint a, IPoint b, IPoint c, SectionType sectionType) {
		long summand1 = (long)a.getX()*(long)(b.getY() - (long)c.getY());
		long summand2 = (long)b.getX()*(long)(c.getY() - (long)a.getY());
		long summand3 = (long)c.getX()*(long)(a.getY() - (long)b.getY());
		
		return sectionType.getSign() * (summand1 + summand2 + summand3);
	}
	
	/**
	 * updates all four sections of the draw panel model.
	 */
	
	public void calculateConvexHull() {
		for(SectionType sectionType : SectionType.values()) {
			calculateSection(sectionType);
		}
	}
}
