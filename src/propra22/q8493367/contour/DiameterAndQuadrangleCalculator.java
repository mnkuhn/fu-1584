package propra22.q8493367.contour;

import propra22.q8493367.animation.QuadrangleSequence;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.shape.Diameter;
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
        if(quadrangleSequence != null) {
        	quadrangleSequence.clear();
        }
		
		if (!convexHull.empty()) {

			// Iterators for the diameter and quadrangel calculation
			IHullIterator aIt = convexHull.getLeftIt();
			IHullIterator cIt = convexHull.getRightIt();

			// Convex hull has one or two points
			if ((aIt.getPoint() == cIt.getPoint())
					|| ((aIt.getNextPoint() == cIt.getPoint()) && (aIt.getPoint() == cIt.getNextPoint()))) {
				diameter.setA(aIt.getPoint());
				diameter.setB(cIt.getPoint());
				quadrangle.setA(aIt.getPoint());
				quadrangle.setB(aIt.getPoint());
				quadrangle.setC(cIt.getPoint());
				quadrangle.setD(cIt.getPoint());
				
				if(quadrangleSequence != null) {
					quadrangleSequence.add(new Quadrangle(aIt.getPoint(), aIt.getPoint(), cIt.getPoint(), cIt.getPoint()));
				}
				

				// Convex hull has more than 2 points
			} else {

				// first diameter
				IDiameter maxDiameter = new Diameter(aIt.getPoint(), cIt.getPoint());
				Diameter tmpDiameter = new Diameter(aIt.getPoint(), cIt.getPoint());

				// Set iterators for the calculation of the quadrangle
				bIt = convexHull.getLeftIt();
				dIt = convexHull.getRightIt();

				// first quadrangle
				IQuadrangle maxQuadrangle = new Quadrangle(aIt.getPoint(), bIt.getPoint(), cIt.getPoint(),
						dIt.getPoint());
				Quadrangle tmpQuadrangle = new Quadrangle(aIt.getPoint(), bIt.getPoint(), cIt.getPoint(),
						dIt.getPoint());

				// limits for the iterators
				IPoint left = aIt.getPoint();
				IPoint right = cIt.getPoint();
				do {
					tmpDiameter.setA(aIt.getPoint());
					tmpDiameter.setB(cIt.getPoint());
					if(tmpDiameter.length() > maxDiameter.length()) {
						maxDiameter.copy(tmpDiameter);
					}
					
					tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getPoint(), dIt);
					if(tmpQuadrangle.area() > maxQuadrangle.area()) {
						maxQuadrangle.copy(tmpQuadrangle);
					}
					
					addTmpQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);
					
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
							tmpDiameter.setA(aIt.getNextPoint());
							
							if (tmpDiameter.length() > maxDiameter.length()) {
								maxDiameter.copy(tmpDiameter);
							}

							tmpQuadrangle = calculateQuadrangle(aIt.getNextPoint(), bIt, cIt.getPoint(), dIt);
							addTmpQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);
							
							if (tmpQuadrangle.area() > maxQuadrangle.area()) {
								maxQuadrangle.copy(tmpQuadrangle);
							}

							cIt.next();

						} else {

							tmpDiameter.setB(cIt.getNextPoint());
							if (tmpDiameter.length() > maxDiameter.length()) {
								maxDiameter.copy(tmpDiameter);
							}
							
							tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getNextPoint(), dIt);
							addTmpQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);
							
							if (tmpQuadrangle.area() > maxQuadrangle.area()) {
								maxQuadrangle.copy(tmpQuadrangle);
							}

							aIt.next();
						}
					}
					
				} while (!(aIt.getPoint() == right) || !(cIt.getPoint() == left));
				
				//continue for the animation
				
				do {
					
					tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getPoint(), dIt);
					addTmpQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);
					
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
							tmpDiameter.setA(aIt.getNextPoint());
							tmpQuadrangle = calculateQuadrangle(aIt.getNextPoint(), bIt, cIt.getPoint(), dIt);
							addTmpQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);
							cIt.next();

						} else {

							tmpDiameter.setB(cIt.getNextPoint());
							tmpQuadrangle = calculateQuadrangle(aIt.getPoint(), bIt, cIt.getNextPoint(), dIt);
							addTmpQuadrangleToQuadrangleSequence(quadrangleSequence, tmpQuadrangle);
							aIt.next();
						}
					}
					
				} while( !(aIt.getPoint() == left) || !(cIt.getPoint() == right)) ;
				

				if (quadrangle != null) {
					quadrangle.copy(maxQuadrangle);
				} 
				if (diameter != null) {
					diameter.copy(maxDiameter);
				}
			}
		}
	}

	private void addTmpQuadrangleToQuadrangleSequence(QuadrangleSequence quadrangleSequence, Quadrangle tmpQuadrangle) {
		if(quadrangleSequence != null) {
			quadrangleSequence.add(new Quadrangle(tmpQuadrangle.getA(), tmpQuadrangle.getB(), 
					tmpQuadrangle.getC(), tmpQuadrangle.getD()));
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
