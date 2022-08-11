package propra22.q8493367.entities;



/**
 * The Class TriangleCalculator calculates
 * the biggest triangle from a convex hull.
 * It implements the algorithm from Dobkin.
 */
public class TriangleCalculator {
	


	/** The convex hull. */
	private IHull convexHull;

	/**
	 * Instantiates a new triangle calculator.
	 *
	 * @param convexHull the convex hull
	 */
	public TriangleCalculator(IHull convexHull) {
		this.convexHull = convexHull;
	}
	
	/**
	 * Calculates the biggest triangle of the 
	 * convex hull.
	 *
	 * @param triangle the triangle whose attributes are
	 * set to those of the biggest triangle.
	 */
	public void calculate(Triangle triangle) {
		
		IHullIterator aIt = convexHull.leftIterator();
		IHullIterator bIt = convexHull.leftIterator();
		IHullIterator cIt = convexHull.leftIterator();
		
		bIt.next();
		
		cIt.next();
		cIt.next();
		
		Triangle baseTriangle  = new Triangle(aIt.getPoint(), bIt.getPoint(), cIt.getPoint());
		Triangle biggestTriangle = new Triangle(baseTriangle);
		if(aIt.getPoint() == cIt.getPoint()) {
			triangle.copy(biggestTriangle);
			return;
		}
		
		
		Triangle nextBTriangle =  new Triangle(aIt.getPoint(), bIt.getNextPoint(), cIt.getPoint());
		Triangle nextCTriangle =  new Triangle(aIt.getPoint(), bIt.getPoint(), cIt.getNextPoint());
		
		Point r = aIt.getPoint();
		do {
			while(nextCTriangle.doubleArea() >= baseTriangle.doubleArea() || nextBTriangle.doubleArea() >= baseTriangle.doubleArea()) {
				if(nextCTriangle.doubleArea() >= baseTriangle.doubleArea()) {
					cIt.next();
					setC(cIt, baseTriangle, nextBTriangle, nextCTriangle);
				}
				else {
					bIt.next();
					setB(bIt, baseTriangle, nextBTriangle, nextCTriangle);
				}
			}
			if(baseTriangle.doubleArea() > biggestTriangle.doubleArea()) {
				biggestTriangle.copy(baseTriangle);
			}
			
			aIt.next();
			if(bIt.getPoint() == aIt.getPoint()) {
				bIt.next();
			}
			if(cIt.getPoint() == bIt.getPoint()) {
				cIt.next();
			}
			setA(aIt, baseTriangle, nextBTriangle, nextCTriangle);
			setB(bIt, baseTriangle, nextBTriangle, nextCTriangle);
			setC(cIt, baseTriangle, nextBTriangle, nextCTriangle);
				
		}while(aIt.getPoint() != r);
		
		triangle.copy(biggestTriangle);		
	}
	
	/**
	 * Sets the the point A of the base triangle
	 * the next B triangle and the next C triangle.
	 *
	 * @param aIt the iterator which represents point A
	 * @param baseTriangle the base triangle
	 * @param nextBTriangle the next B triangle
	 * @param nextCTriangle the next C triangle
	 */
	private void setA(IHullIterator aIt, Triangle baseTriangle, Triangle nextBTriangle, Triangle nextCTriangle) {
		baseTriangle.setA(aIt.getPoint());
		nextBTriangle.setA(aIt.getPoint());
		nextCTriangle.setA(aIt.getPoint());
	}

	/**
	 * Sets the the point B of the base triangle
	 * the next B triangle and the next C triangle.
	 *
	 * @param bIt the iterator which represents point B
	 * @param baseTriangle the base triangle
	 * @param nextBTriangle the next B triangle
	 * @param nextCTriangle the next C triangle
	 */
	private void setB(IHullIterator bIt, Triangle baseTriangle, Triangle nextBTriangle, Triangle nextCTriangle) {
		baseTriangle.setB(bIt.getPoint());
		nextBTriangle.setB(bIt.getNextPoint());
		nextCTriangle.setB(bIt.getPoint());
	}

	/**
	 * Sets the the point C of the base triangle
	 * the next B triangle and the next C triangle.
	 *
	 * @param cIt the iterator which represents point C
	 * @param baseTriangle the base triangle
	 * @param nextBTriangle the next B triangle
	 * @param nextCTriangle the next C triangle
	 */
	private void setC(IHullIterator cIt, Triangle baseTriangle, Triangle nextBTriangle, Triangle nextCTriangle) {
		baseTriangle.setC(cIt.getPoint());
		nextBTriangle.setC(cIt.getPoint());
		nextCTriangle.setC(cIt.getNextPoint());
	}
}
