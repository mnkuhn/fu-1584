package propra22.q8493367.contour;


import propra22.q8493367.point.Point;



/**
 * The Class ConvexHullCalculator is responsible for the 
 * the calculation of the convex hull.
 */
public class ConvexHullCalculator implements IContourCalculator {
	
	
	/** The hull which is the contour polygon before the calculation.
	 * 	It is the convex hull after the calculation is completed.
	 */
	private IHull hull;

	/**
	 * Instantiates a new convex hull calculator.
	 *
	 * @param contourPolygon the contour polygon
	 */
	public ConvexHullCalculator(IHull contourPolygon) {

		this.hull = contourPolygon;
	}
	
	/**
	 * Calculates one of the four contours.
	 *
	 * @param contourType the contour type
	 */
	@Override
	public void calculateContour(ContourType contourType) {
		
		if(!hull.sectionIsEmpty(contourType)) {
			int size = hull.getSizeOfSection(contourType);
			if(size >= 3) {
				int base = 0;
				int next = 2;
				while(next < size) {
					//next is on the inner side of the line through base and base + 1
					if(signedTriangleArea(base, next, contourType)  > 0){
						base++;
						next++;	
					}
					else {
						/*next is on the outer side of the line through base and base + 1 or 
						 * next is exactly on the line through base and base + 1
						 */
						if(base > 0) {
							hull.removePointFromContour(base + 1, contourType);
							size--;
							base--;
							next--;
							if(next < size) {
								while(base > 0 && signedTriangleArea(base, next, contourType) < 0) {
									hull.removePointFromContour(base + 1, contourType);
									size--;
									base--;
									next--;	
								}
							}
						}
						//base == 0
						else {
							hull.removePointFromContour(base + 1, contourType);
							size--;
						}
					}
				}
			}
		}
	}
		
	/**
	 * Signed triangle area. The DFV algorithm with adapted arguments.
	 *
	 * @param base  the base point. This point is the first point of the base line
	 * of the triangle, whose area is calculated.
	 * @param tip  this points represents the tip of the triangle, whose
	 * area is calculated.
	 * @param sectionType the section type
	 * @return the long
	 */
	private long signedTriangleArea(int base, int tip, ContourType sectionType) {
		return sectionType.getSign() * Point.signedTriangleArea(
				//the first point of the baseline
				hull.getPointFromSection(base, sectionType), 
				//The second point of the baseline following the first base point in 
				//the direction of increasing index.
				hull.getPointFromSection(base + 1, sectionType),  
				//The tip of the triangle
				hull.getPointFromSection(tip, sectionType));
	}
	
	
	/**
	 * Calculates the convex hull outgoing from the 
	 * contour polygon.
	 */
	public void calculateConvexHull() {
		if (!hull.isEmpty()) {
			for (ContourType sectionType : ContourType.values()) {
				calculateContour(sectionType);
			}
		}
	}
}
