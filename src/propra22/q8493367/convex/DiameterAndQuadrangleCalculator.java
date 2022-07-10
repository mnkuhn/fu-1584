package propra22.q8493367.convex;

import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.draw.model.IQuadrangle;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * The Class DiameterAndQuadrangleCalculator provides a calculator, which takes
 * a convex hull as an argument and calculates the diameter and the quadrangle
 * with maximum area.
 */
public class DiameterAndQuadrangleCalculator implements IDiameterAndQuadrangleCalulator {

	/** The convex hull. */
	private IHull convexHull;

	/**
	 * The iterator which represents the point b in the quadrangle where a, b, c and
	 * d are the 4 points of the quadrangle listed counterclockwise.
	 */
	private IHullIterator bIt;

	/**
	 * The iterator which represents the point d in the quadrangle where a, b, c and
	 * d are the 4 points of the quadrangle listed counterclockwise.
	 */
	private IHullIterator dIt;

	/**
	 * Instantiates a new diameter and quadrangle calculator.
	 *
	 * @param convexHull           The convex hull
	 * @param quadrangleCalculator The calculator for the biggest quadrangle which
	 *                             can be used directly in the algorithm for the
	 *                             calculation of the diameter.
	 * 
	 */
	public DiameterAndQuadrangleCalculator(IHull convexHull) {
		this.convexHull = convexHull;
	}

	/**
	 * Calculate the diameter and the quadrangle with the biggest area.
	 * 
	 * @param diameter   the diameter object
	 * @param quadrangle the quadrangle object
	 */
	public void calculate(IDiameter diameter, IQuadrangle quadrangle) {

		// Set iterators for the calculation of the quadrangle
		bIt = convexHull.getLeftIt();
		dIt = convexHull.getRightIt();

		// Convex hull is empty
		if (convexHull.empty()) {
			diameter = null;
			quadrangle = null;
			return;
		}

		// Iterators for the diameter and quadrangel calculation
		IHullIterator aIt = convexHull.getLeftIt();
		IHullIterator cIt = convexHull.getRightIt();
		
		// Convex hull has one or two points
		if ((aIt.getPoint() == cIt.getPoint()) || (aIt.getNextPoint() == cIt.getPoint())) {
			diameter.setA(aIt.getPoint());
			diameter.setB(cIt.getPoint());
			quadrangle.setA(aIt.getPoint());
			quadrangle.setB(aIt.getPoint());
			quadrangle.setC(cIt.getPoint());
			quadrangle.setD(cIt.getPoint());

		// Convex hull has more than 2 points 
		} else {

			// first diameter
			IDiameter maxDiameter = new Diameter(aIt.getPoint(), cIt.getPoint());
			Diameter tmpDiameter = new Diameter(aIt.getPoint(), cIt.getPoint());
			
			// first  quadrangle
			IQuadrangle maxQuadrangle = new Quadrangle(aIt.getPoint(), bIt.getPoint(), cIt.getPoint(), dIt.getPoint());
			Quadrangle tmpQuadrangle = new Quadrangle(aIt.getPoint(), bIt.getPoint(), cIt.getPoint(), dIt.getPoint());
            
			// limits for the iterators
			IPoint left = aIt.getPoint();
			IPoint right = cIt.getPoint();
			while (!(aIt.getPoint() == right) || !(cIt.getPoint() == left)) {

				long angleComparisonTestResult = AngleComparisonTest(aIt, cIt);

				// angleComparisonResult > 0
				if (angleComparisonTestResult > 0) {
					aIt.next();
					tmpDiameter.setA(aIt.getPoint());
					tmpDiameter.setB(cIt.getPoint());
					tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getPoint(), dIt);

				// angleComparisonResult < 0
				} else if (angleComparisonTestResult < 0) {
					cIt.next();
					tmpDiameter.setA(aIt.getPoint());
					tmpDiameter.setB(cIt.getPoint());
					tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getPoint(), dIt);
				}

				// angleComparisonResult == 0
				else {
					if (Point.isShorter(aIt.getPoint(), aIt.getNextPoint(), cIt.getPoint(), cIt.getNextPoint())) {
						tmpDiameter.setA(aIt.getNextPoint());
						tmpDiameter.setB(cIt.getPoint());
						if (tmpDiameter.length() > maxDiameter.length()) {maxDiameter.copy(tmpDiameter);}

						tmpQuadrangle = calculateQuadrangle(aIt.getNextPoint(), bIt, cIt.getPoint(), dIt);
						if (tmpQuadrangle.area() > maxQuadrangle.area()) {maxQuadrangle.copy(tmpQuadrangle);}

						cIt.next();

					} else {

						tmpDiameter.setA(aIt.getPoint());
						tmpDiameter.setB(cIt.getNextPoint());
						if (tmpDiameter.length() > maxDiameter.length()) {maxDiameter.copy(tmpDiameter);}
						
						tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getNextPoint(), dIt);
						if (tmpQuadrangle.area() > maxQuadrangle.area()) {maxQuadrangle.copy(tmpQuadrangle);}

						aIt.next();
						
					}
				}
				if (tmpDiameter.length() > maxDiameter.length()) {
					maxDiameter.copy(tmpDiameter);
				}

				if (tmpQuadrangle.area() > maxQuadrangle.area()) {
					maxQuadrangle.copy(tmpQuadrangle);
				}
			}
            if(quadrangle != null) {
            	quadrangle.copy(maxQuadrangle);
            }
            else {
            	quadrangle = maxQuadrangle;
            }
            if(diameter != null) {
            	diameter.copy(maxDiameter);
            }
            else {
            	diameter = maxDiameter;
            }
		}
	}

	/**
	 * Angle comparison test.
	 *
	 * @param aIterator the a iterator
	 * @param bIterator the b iterator
	 * @return the long
	 */
	private long AngleComparisonTest(IHullIterator aIterator, IHullIterator bIterator) {
		long xTip = (long) aIterator.getPoint().getX() + (long) bIterator.getPoint().getX()
				- (long) bIterator.getNextPoint().getX();
		long yTip = (long) aIterator.getPoint().getY() + (long) bIterator.getPoint().getY()
				- (long) bIterator.getNextPoint().getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return  Point.signedTriangleArea(aIterator.getPoint(), aIterator.getNextPoint(), tip);
	}

	private Quadrangle calculateQuadrangle(IPoint a, IHullIterator bIt, IPoint c, IHullIterator dIt) {
		/*
		 * IPoint c = cIt.getPoint(); IPoint a = aIt.getPoint(); IPoint bNext =
		 * bIt.getNextPoint(); IPoint b = bIt.getPoint();
		 */

		while (Point.isHigher(c, a, bIt.getNextPoint(), bIt.getPoint())) {
			bIt.next();
		}

		while (Point.isHigher(a, c, dIt.getNextPoint(), dIt.getPoint())) {
			dIt.next();
		}

		return new Quadrangle(a, bIt.getPoint(), c, dIt.getPoint());
	}
}
