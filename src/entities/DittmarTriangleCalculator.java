package entities;

/**
 * The Class TriangleCalculator calculates
 * the biggest triangle of a convex hull.
 * It implements the algorithm of J. Dittmar (2022).
 */

public class DittmarTriangleCalculator implements ITriangleCalculator {

	/** The convex hull. */
	private IHull convexHull;

	/**
	 * Instantiates a new triangle calculator.
	 *
	 * @param convexHull the convex hull
	 */
	public DittmarTriangleCalculator(IHull convexHull) {
		this.convexHull = convexHull;
	}
	
	@Override
	public void calculate(Triangle triangle) {
	    if(!convexHull.isEmpty()) {
	    	IHullIterator aIt = convexHull.leftIterator();
			IHullIterator bIt = convexHull.leftIterator();
			IHullIterator cIt = convexHull.leftIterator();
			
			bIt.next();
			
			cIt.next();
			cIt.next();
			
			Triangle baseTriangle  = new Triangle(aIt.getPoint(), bIt.getPoint(), cIt.getPoint());
			
			if(aIt.getPoint() == cIt.getPoint()) {
				triangle.copy(baseTriangle);
				return;
			}
			
			Triangle biggestTriangle = new Triangle(baseTriangle);
			
			Triangle nextBTriangle =  new Triangle(aIt.getPoint(), bIt.getNextPoint(), cIt.getPoint());
			Triangle nextCTriangle =  new Triangle(aIt.getPoint(), bIt.getPoint(), cIt.getNextPoint());
			Triangle nextBnextCTriangle = new Triangle(aIt.getPoint(), bIt.getNextPoint(), cIt.getNextPoint());
			
			Point r = aIt.getPoint();
			boolean changed;
			do {
				do {
					while (nextCTriangle.area() > baseTriangle.area()) {
						cIt.next();
						baseTriangle.setC(cIt.getPoint());
						nextCTriangle.setC(cIt.getNextPoint());
					}
					
					if (baseTriangle.area() > biggestTriangle.area()) {
						biggestTriangle.copy(baseTriangle);
					}
					
					changed = false;
					
					nextBTriangle.setC(cIt.getPoint());
					nextBnextCTriangle.setC(cIt.getNextPoint());
					
					if (nextBTriangle.area() > baseTriangle.area()
							|| nextBnextCTriangle.area() > baseTriangle.area()) {
						bIt.next();
						baseTriangle.setB(bIt.getPoint());
						nextBTriangle.setB(bIt.getNextPoint());
						nextCTriangle.setB(bIt.getPoint());
						nextBnextCTriangle.setB(bIt.getNextPoint());
						
						changed = true;
					}
				} while (changed == true);
				
				aIt.next();
				baseTriangle.setA(aIt.getPoint());
				nextBTriangle.setA(aIt.getPoint());
				nextCTriangle.setA(aIt.getPoint());
				nextBnextCTriangle.setA(aIt.getPoint());

			} while (aIt.getPoint() != r);
				
			triangle.copy(biggestTriangle);		
		}

	}
}
