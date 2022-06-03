package propra22.q8493367.convex;

import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.draw.model.IQuadrangle;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;



/**
 * The Class DiameterAndQuadrangleCalculator provides a calculator, which takes a convex hull
 * as an argument and calculates the diameter and the quadrangle with maximum area.
 */
public class DiameterAndQuadrangleCalculator implements IDiameterAndQuadrangleCalculator {

	/** The hull. */
	private IHull convexHull;
	
	/** The iterator which represents the point b in the quadrangle  where a, b, c and d are the 4 points of the quadrangle listed counterclockwise. */
	private IHullIterator bIt;
	
	/** The iterator which represents the point d in the quadrangle  where a, b, c and d are the 4 points of the quadrangle listed counterclockwise. */
	private IHullIterator dIt;

	/**
	 * Instantiates a new diameter and quadrangle calculator.
	 *
	 * @param The convex hull
	 */
	public DiameterAndQuadrangleCalculator(IHull convexHull) {
		this.convexHull = convexHull;
	}

	

	/**
	 * Calculate the diameter and the quadrangle with the biggest area.
	 *
	 * @param diameter the diameter
	 * @param inQuadrangle the quadrangle
	 */
	public void calculate(IDiameter diameter, IQuadrangle inQuadrangle) {
		IPoint diameterPoint1 = null;
		IPoint diameterPoint2 = null;
		
		Quadrangle maxQuadrangle = null;
		
		// Set iterators with limits
		IHullIterator aIt = convexHull.getIterator(0);
		aIt.setLimit(convexHull.getIndexOfRightMostPoint() + 1);
		
		IHullIterator cIt = convexHull.getIterator(convexHull.getIndexOfRightMostPoint());
		cIt.setLimit(1);
		
		bIt = convexHull.getIterator(1);
		bIt.setLimit(convexHull.getIndexOfRightMostPoint() + 2);
		
		dIt = convexHull.getIterator(convexHull.getIndexOfRightMostPoint() + 1);
		dIt.setLimit(2);
		
		
		// Hull has only one point
		if (convexHull.size() == 1 ) {
			
			diameterPoint1 = aIt.getPoint();
			diameterPoint2 = aIt.getPoint();
			maxQuadrangle = new Quadrangle(aIt.getPoint(), aIt.getPoint(), aIt.getPoint(), aIt.getPoint());
		
		// Hull has two points
		} else if (convexHull.size() == 2) {
			
			diameterPoint1 = aIt.getPoint();
			diameterPoint2 = cIt.getPoint();
			maxQuadrangle = new Quadrangle(aIt.getPoint(), aIt.getPoint(), cIt.getPoint(), cIt.getPoint());
		
		// Hull has more than 2 points
		} else if (convexHull.size() >= 3) {
		    
			// first diameter
		    diameterPoint1 = aIt.getPoint();
			diameterPoint2 = cIt.getPoint();
            
			// first biggest quadrangle
			maxQuadrangle = calculateQuadrangle(aIt, cIt);
			
			aIt.next();
			cIt.next();
            
			while ((!aIt.hasReachedLimit()) || (!cIt.hasReachedLimit())) {
				
				if (Point.isLonger(aIt.getPoint(), cIt.getPoint(), diameterPoint1, diameterPoint2)) {
					diameterPoint1 = aIt.getPoint();
					diameterPoint2 = cIt.getPoint();	
				}
				
				Quadrangle quadrangle = calculateQuadrangle(aIt, cIt);
				if(quadrangle.area() > maxQuadrangle.area()) {
					maxQuadrangle = quadrangle;
				}
				
				long angleComparisonTestResult = AngleComparisonTest(aIt, cIt);

				// angleComparisonResult > 0
				if (angleComparisonTestResult > 0) {
					aIt.next();

				// angleComparisonResult < 0
				} else if (angleComparisonTestResult < 0) {
					cIt.next();
				}

				// angleComparisonResult == 0
				else {
					if (Point.isShorter(aIt.getPoint(), aIt.getNextPoint(), cIt.getPoint(), cIt.getNextPoint())) {
						if (Point.isLonger(aIt.getNextPoint(), cIt.getPoint(), diameterPoint1, diameterPoint2)) {
							diameterPoint1 = aIt.getNextPoint();
							diameterPoint2 = cIt.getPoint();

							aIt.next();
							quadrangle = calculateQuadrangle(aIt, cIt);
							aIt.previous();
							
							if(quadrangle.area() > maxQuadrangle.area()) {
								maxQuadrangle = quadrangle;
							}	
						}
						cIt.next();
						
					} else {
						if (Point.isLonger(aIt.getPoint(), cIt.getNextPoint(), diameterPoint1, diameterPoint2)) {
							diameterPoint1 = aIt.getPoint();
							diameterPoint2 = cIt.getNextPoint();
							
							cIt.next();
							quadrangle = calculateQuadrangle(aIt, cIt);
							cIt.previous();
							
							if(quadrangle.area() > maxQuadrangle.area()) {
								maxQuadrangle = quadrangle;
							}		
						}
						aIt.next();
					}
				}
			}
		}
		inQuadrangle.setA(maxQuadrangle.getA());
		inQuadrangle.setB(maxQuadrangle.getB());
		inQuadrangle.setC(maxQuadrangle.getC());
		inQuadrangle.setD(maxQuadrangle.getD());
		
		diameter.setA(diameterPoint1);
		diameter.setB(diameterPoint2);
	}
	
	
	/**
	 * Calculate quadrangle.
	 *
	 * @param aIterator - the iterator which represents the point a in the quadrangle  where a, b, c and d are the 4 points of the quadrangle listed counterclockwise.
	 * @param cIterator - the iterator which represents the point c in the quadrangle  where a, b, c and d are the 4 points of the quadrangle listed counterclockwise.
	 * @return the quadrangle
	 */
	private Quadrangle calculateQuadrangle(IHullIterator aIterator, IHullIterator cIterator) {
		
		while(Point.isHigher(cIterator.getPoint(), aIterator.getPoint(), bIt.getNextPoint(), bIt.getPoint())) {
			bIt.next();	
		}
		
		while(Point.isHigher(aIterator.getPoint(), cIterator.getPoint(), dIt.getNextPoint(), dIt.getPoint())) {
			dIt.next();	
		}
	
		return new Quadrangle(aIterator.getPoint(), bIt.getPoint(), cIterator.getPoint(), dIt.getPoint());    
	}
	
	// The angle comparison test
	// TODO Javadoc
	/**
	 * Angle comparison test.
	 *
	 * @param aIterator the a iterator
	 * @param bIterator the b iterator
	 * @return the long
	 */
	private  long AngleComparisonTest(IHullIterator aIterator, IHullIterator bIterator) {
		long xTip = (long) aIterator.getPoint().getX() + (long) bIterator.getPoint().getX() - (long) bIterator.getNextPoint().getX();
		long yTip = (long) aIterator.getPoint().getY() + (long) bIterator.getPoint().getY() - (long) bIterator.getNextPoint().getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return Point.signedTriangleArea(aIterator.getPoint(), aIterator.getNextPoint(), tip);
	}
}
