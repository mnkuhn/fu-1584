package propra22.q8493367.convex;

import propra22.q8493367.contour.ISectionCalculator;
import propra22.q8493367.contour.SectionType;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;


// TODO: Auto-generated Javadoc
/**
 * The Class ConvexHullCalculator is considered to be part of the controller of
 * the draw panel. It calculates the convex hull outgoing from the points of 
 * the contour polygon.
 */
public class ConvexHullCalculator implements ISectionCalculator {
	
	
	private IHull hull;



	/**
	 * Instantiates a new convex hull calculator.
	 *
	 * @param model - the contour polygon
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
			if(size >= 3) {
				int base = 0;
				int next = 2;
				while(next < size) {
					//next is on the inner side of the line through base and base + 1
					if(DFV(base, next, sectionType)  > 0){
						base++;
						next++;	
					}
					else {
						//next is on the outer side of the line through base and base + 1
						if(base > 0) {
							hull.removePointFromSection(base + 1, sectionType);
							size--;
							base--;
							next--;
							if(next < size) {
								while(base > 0 && DFV(base, next, sectionType) < 0) {
									hull.removePointFromSection(base + 1, sectionType);
									size--;
									base--;
									next--;	
								}
							}
						}
						//base == 0
						else {
							hull.removePointFromSection(base + 1, sectionType);
							size--;
						}
					}
				}
			}
		}
	}
		
	
	
	/**
	 * This is the DFV function which calculates the signed area of a triangle. Depending
	 * on the calculated section, area is positive or negative, if the tip of the triangle is
	 * above or below line which goes through the baseline of the triangle. 
	 *
	 * @param base - base point of the triangle which induces the other base point indexed by base + 1
	 * @param next - tip of the triangle
	 * @param sectionType - the section type which determines the sign for the calculated area
	 * @return signed area of the triangle
	 */
	
	
	private long DFV(int base, int next, SectionType sectionType) {
		
		IPoint a = hull.getPointFromSection(base, sectionType);
		IPoint b = hull.getPointFromSection(base + 1, sectionType);
		IPoint c = hull.getPointFromSection(next, sectionType);	
		
		long summand1 = (long)a.getX()*((long)b.getY() - (long)c.getY());
		long summand2 = (long)b.getX()*((long)c.getY() - (long)a.getY());
		long summand3 = (long)c.getX()*((long)a.getY() - (long)b.getY());
		
		return sectionType.getSign() * (summand1 + summand2 + summand3);
	}

	
	
	/**
	 * updates all four sections of the draw panel model.
	 */
	
	public void calculateConvexHull() {
		for(SectionType sectionType : SectionType.values()) {
			calculateSection(sectionType);
		}
		hull.createList();
	}
}
