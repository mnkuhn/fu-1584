package propra22.q8493367.contour;

import propra22.q8493367.animation.QuadrangleSequence;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.shape.Quadrangle;

// TODO: Auto-generated Javadoc
/**
 * The Class DiameterAndQuadrangleCalculator provides a calculator, which takes
 * a convex hull as an argument and calculates the diameter and the quadrangle
 * with maximum area.
 */
public class DiameterAndQuadrangleCalculator  {

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
	public void calculate(IDiameter diameter, IQuadrangle quadrangle, QuadrangleSequence quadrangleSequence) {
        if(!convexHull.isEmpty()) {
        	calculateQuadrangleSequence(quadrangleSequence);
            diameter.copy(quadrangleSequence.getBiggestDiameter());
            quadrangle.copy(quadrangleSequence.getBiggestQuadrangle());
        }
	}

	private void addQuadrangleToQuadrangleSequence(QuadrangleSequence quadrangleSequence, Quadrangle tmpQuadrangle) {
		if(quadrangleSequence != null) {
			quadrangleSequence.add(new Quadrangle(tmpQuadrangle.getA(), tmpQuadrangle.getB(), 
					tmpQuadrangle.getC(), tmpQuadrangle.getD()));
		}
	}
	
	private void calculateQuadrangleSequence(QuadrangleSequence quadrangleSequence) {
		quadrangleSequence.clear();
		if (!convexHull.isEmpty()) {
			IHullIterator aIt = convexHull.getLeftIt();
			IHullIterator cIt = convexHull.getRightIt();

			// Convex hull has one point
			if (aIt.getPoint() == cIt.getPoint()) {
				quadrangleSequence.add(new Quadrangle(aIt.getPoint(), aIt.getPoint(), aIt.getPoint(), aIt.getPoint()));
			// Convex hull has two points	
			} else if(aIt.getNextPoint() == cIt.getPoint() && aIt.getPoint() == cIt.getNextPoint()){
				quadrangleSequence.add(new Quadrangle(aIt.getPoint(), aIt.getPoint(), cIt.getPoint(), cIt.getPoint()));
				quadrangleSequence.add(new Quadrangle(cIt.getPoint(), cIt.getPoint(), aIt.getPoint(), aIt.getPoint()));
			// Convex hull has more than two points
			} else {

				bIt = convexHull.getLeftIt();
				dIt = convexHull.getRightIt();

				Quadrangle tmpQuadrangle;
				// limits for the iterators
				IPoint left = aIt.getPoint();
				IPoint right = cIt.getPoint();
				do {

					tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getPoint(), dIt);
					addQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);

					// long angleComparisonTestResult = convexHull.AngleComparisonTest(aIt, cIt);
					long angleComparisonTestResult = Point.angleComparisonTest(aIt.getPoint(), aIt.getNextPoint(),
							cIt.getPoint(), cIt.getNextPoint());

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
							tmpQuadrangle = calculateQuadrangle(aIt.getNextPoint(), bIt, cIt.getPoint(), dIt);
							addQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);
							cIt.next();

						} else {
							tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getNextPoint(), dIt);
							addQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);
							aIt.next();
						}
					}
				} while (!(aIt.getPoint() == left) || !(cIt.getPoint() == right));
			}
		}
	}
	

	/**
	 * Calculates the quadrangle over the antipodal pair given by the points a and c.
	 *
	 * @param a the point a
	 * @param bIt the hull iterator representing the point b
	 * @param c the point c
	 * @param dIt the hull iterator representing the point d
	 * @return the quadrangle
	 */
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
