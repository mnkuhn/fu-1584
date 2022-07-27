package propra22.q8493367.contour;


import propra22.q8493367.point.Point;


// TODO: Auto-generated Javadoc
/**
 * The Class ConvexHullCalculator is considered to be part of the controller of
 * the draw panel. It calculates the convex hull outgoing from the points of 
 * the contour polygon.
 */
public class ConvexHullCalculator implements ISectionCalculator {
	
	
	/** The hull which is the contour polygon in the beginning.
	 * After the calculation of the convex hull calculator the
	 * hull is the convex hull.
	 */
	private IHull hull;

	/**
	 * Instantiates a new convex hull calculator.
	 *
	 * @param hull - the hull, which is the contour polygon.
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
	public void calculateSection(ContourType sectionType) {
		
		if(!hull.sectionIsEmpty(sectionType)) {
			int size = hull.getSizeOfSection(sectionType);
			if(size >= 3) {
				int base = 0;
				int next = 2;
				while(next < size) {
					//next is on the inner side of the line through base and base + 1
					if(signedTriangleArea(base, next, sectionType)  > 0){
						base++;
						next++;	
					}
					else {
						/*next is on the outer side of the line through base and base + 1 or 
						 * next is exactly on the line through base and base + 1
						 */
						if(base > 0) {
							hull.removePointFromSection(base + 1, sectionType);
							size--;
							base--;
							next--;
							if(next < size) {
								while(base > 0 && signedTriangleArea(base, next, sectionType) < 0) {
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
	 * Signed triangle area.
	 *
	 * @param base - the base point. This point is the first point of the bas line
	 * of the triangle, whose area is calculated.
	 * @param next - this points represents the tip of the triangle, whose
	 * area is calculated.
	 * @param sectionType the section type
	 * @return the long
	 */
	private long signedTriangleArea(int base, int next, ContourType sectionType) {
		return sectionType.getSign() * Point.signedTriangleArea(
				//the first point of the baseline
				hull.getPointFromSection(base, sectionType), 
				//The second point of the baseline following the first base point in 
				//the direction of increasing index.
				hull.getPointFromSection(base + 1, sectionType),  
				//The tip of the triangle
				hull.getPointFromSection(next, sectionType));
	}
	
	
	/**
	 * Calculates the convex hull outgoing from the 
	 * contour polygon.
	 */
	public void calculateConvexHull() {
		if (!hull.isEmpty()) {
			for (ContourType sectionType : ContourType.values()) {
				calculateSection(sectionType);
			}

			// puts the 4 sections into a list, following the points counterclockwise
			// starting at the leftmost point.
			hull.createList();
		}
	}
}
