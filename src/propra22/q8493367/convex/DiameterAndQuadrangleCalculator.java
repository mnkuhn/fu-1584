package propra22.q8493367.convex;

import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;



/**
 * The Class DiameterAndQuadrangleCalculator provides a calculator, which takes a convex hull
 * as an argument and calculates the diameter and the quadrangle with maximum area.
 */
public class DiameterAndQuadrangleCalculator {

	/** The hull. */
	private IHull convexHull;
	
	/** The iterator which represents the point b in the quadrangle  where a, b, c and d are the 4 points of the quadrangle listed counterclockwise. */
	private IHullIterator bIterator;
	
	/** The iterator which represents the point d in the quadrangle  where a, b, c and d are the 4 points of the quadrangle listed counterclockwise. */
	private IHullIterator dIterator;

	/**
	 * Instantiates a new diameter and quadrangle calculator.
	 *
	 * @param The convex hull
	 */
	public DiameterAndQuadrangleCalculator(IHull convexHull) {
		this.convexHull = convexHull;
	}

	
	private long DFV(IPoint a, IPoint b, IPoint c) {

		long summand1 = (long) a.getX() * ((long) b.getY() - (long) c.getY());
		long summand2 = (long) b.getX() * ((long) c.getY() - (long) a.getY());
		long summand3 = (long) c.getX() * ((long) a.getY() - (long) b.getY());
		// change the sign because (0, 0) is in the upper left corner
		return -(summand1 + summand2 + summand3);
	}

	// quadratic distance between two points
	private long qaudraticDistance(IPoint a, IPoint b) {
		long dx = (long) a.getX() - (long) b.getX();
		long dy = (long) a.getY() - (long) b.getY();
		return dx * dx + dy * dy;
	}

	// Returns true, if quadratic distance between a and b is smaller than between c and d
	// Returns false otherwise
	private boolean isShorter(IPoint a, IPoint b, IPoint c, IPoint d) {
		return qaudraticDistance(a, b) < qaudraticDistance(c, d);
	}


	// Returns true, if quadratic distance between a and b is bigger than between c and d.
	// Returns false otherwise
	private boolean isLonger(IPoint a, IPoint b, IPoint c, IPoint d) {
		return qaudraticDistance(a, b) > qaudraticDistance(c, d);
	}

	// The angle comparison test
	private  long AngleComparisonTest(IHullIterator aIterator, IHullIterator bIterator) {
		long xTip = (long) aIterator.getPoint().getX() + (long) bIterator.getPoint().getX() - (long) bIterator.getNextPoint().getX();
		long yTip = (long) aIterator.getPoint().getY() + (long) bIterator.getPoint().getY() - (long) bIterator.getNextPoint().getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return DFV(aIterator.getPoint(), aIterator.getNextPoint(), tip);
	}

	/**
	 * Calculate the diameter and the quadrangle with the biggest area.
	 *
	 * @param diameter the diameter
	 * @param quadrangle the quadrangle
	 */
	public void calculate(Diameter diameter, Quadrangle quadrangle) {
        System.out.println("size of hull: " + convexHull.size());
		IPoint maxDiameterA = null;
		IPoint maxDiameterB = null;
		
		Quadrangle maxQuadrangle = null;
		
		// Set iterators with limits
		IHullIterator aIt = convexHull.getIterator(0, convexHull.getIndexOfRightMostPoint() + 1);
		IHullIterator cIt = convexHull.getIterator(convexHull.getIndexOfRightMostPoint(), 1);
		bIterator = convexHull.getIterator(1, convexHull.getIndexOfRightMostPoint() + 2);
		dIterator = convexHull.getIterator(convexHull.getIndexOfRightMostPoint() + 1, 2);
		
		
		// Hull has only one point
		if (convexHull.size() == 1 ) {
			
			// first diameter
			maxDiameterA = aIt.getPoint();
			maxDiameterB = aIt.getPoint();
			
			maxQuadrangle = new Quadrangle(aIt.getPoint(), aIt.getPoint(), aIt.getPoint(), aIt.getPoint());
		// Hull has two points
		} else if (convexHull.size() == 2) {
			
			// first diameter
			maxDiameterA = aIt.getPoint();
			maxDiameterB = cIt.getPoint();
			
			maxQuadrangle = new Quadrangle(aIt.getPoint(), aIt.getPoint(), cIt.getPoint(), cIt.getPoint());
			
		} else if (convexHull.size() >= 3) {
		    
			// first diameter
		    maxDiameterA = aIt.getPoint();
			maxDiameterB = cIt.getPoint();
            
			
			maxQuadrangle = calculateQuadrangle(aIt, cIt);
            
			
			while ((!aIt.hasReachedLimit()) || (!cIt.hasReachedLimit())) {
				
               
				if (isLonger(aIt.getPoint(), cIt.getPoint(), maxDiameterA, maxDiameterB)) {
					maxDiameterA = aIt.getPoint();
					maxDiameterB = cIt.getPoint();
					
				}
				
				Quadrangle rectangle = calculateQuadrangle(aIt, cIt);
				if(rectangle.area() > maxQuadrangle.area()) {
					maxQuadrangle = rectangle;
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
					if (isShorter(aIt.getPoint(), aIt.getNextPoint(), cIt.getPoint(), cIt.getNextPoint())) {
						if (isLonger(aIt.getNextPoint(), cIt.getPoint(), maxDiameterA, maxDiameterB)) {
							maxDiameterA = aIt.getNextPoint();
							maxDiameterB = cIt.getPoint();
							
							IHullIterator tmp = convexHull.getIterator(aIt.getIndex() + 1, cIt.getIndex() + 2);
							
							rectangle = calculateQuadrangle(tmp, cIt);
							if(rectangle.area() > maxQuadrangle.area()) {
								maxQuadrangle = rectangle;
							}
							
						}
						cIt.next();
						
					} else {
						if (isLonger(aIt.getPoint(), cIt.getNextPoint(), maxDiameterA, maxDiameterB)) {
							maxDiameterA = aIt.getPoint();
							maxDiameterB = cIt.getNextPoint();
							
							IHullIterator tmp = convexHull.getIterator(cIt.getIndex() + 1, aIt.getIndex() + 1);
							
							
							rectangle = calculateQuadrangle(aIt, tmp);
							if(rectangle.area() > maxQuadrangle.area()) {
								maxQuadrangle = rectangle;
							}
							
						}
						aIt.next();
					}
				}
			}
		}
		
		quadrangle.setA(maxQuadrangle.getA());
		quadrangle.setB(maxQuadrangle.getB());
		quadrangle.setC(maxQuadrangle.getC());
		quadrangle.setD(maxQuadrangle.getD());
		
		diameter.setA(maxDiameterA);
		diameter.setB(maxDiameterB);
	}
	
	
	
    // Returns true, if the triangle a - c - d with baseline a - c is higher
	// than the triangle a - c - dDash, with baseline a - c.
	private boolean isHigher(IPoint a, IPoint c, IPoint d, IPoint dDash) {
		long x = (long)a.getX() + (long)dDash.getX() - (long)c.getX();
		long y = (long)a.getY() + (long)dDash.getY() - (long)c.getY();
		IPoint sum = new Point((int)x, (int)y);
		return DFV(sum, dDash, d) > 0;
		
	}
	
	/**
	 * Calculate quadrangle.
	 *
	 * @param aIterator - the iterator which represents the point a in the quadrangle  where a, b, c and d are the 4 points of the quadrangle listed counterclockwise.
	 * @param cIterator - the iterator which represents the point c in the quadrangle  where a, b, c and d are the 4 points of the quadrangle listed counterclockwise.
	 * @return the quadrangle
	 */
	private Quadrangle calculateQuadrangle(IHullIterator aIterator, IHullIterator cIterator) {
		
		//IHullIterator bIterator = hull.getIterator(aIterator.getIndex() + 1, cIterator.getIndex() + 2);
		//IHullIterator dIterator = hull.getIterator(cIterator.getIndex() + 1, aIterator.getIndex() + 2);
		System.out.println("aIt: " + aIterator.getIndex() + "  cIt: " + cIterator.getIndex());
		System.out.println("bIt started: " + bIterator.getIndex() );
		while(isHigher(cIterator.getPoint(), aIterator.getPoint(), bIterator.getNextPoint(), bIterator.getPoint())) {
			bIterator.next();
			
		}
		System.out.println("bIt stopped: " + bIterator.getIndex() );
		System.out.println("dIt started: " + dIterator.getIndex());
		while(isHigher(aIterator.getPoint(), cIterator.getPoint(), dIterator.getNextPoint(), dIterator.getPoint())) {
			dIterator.next();
			
		}
		System.out.println("dIt stopped: " + dIterator.getIndex() );
		System.out.println(" ");
		return new Quadrangle(aIterator.getPoint(), bIterator.getPoint(), cIterator.getPoint(), dIterator.getPoint());    
	}
}
