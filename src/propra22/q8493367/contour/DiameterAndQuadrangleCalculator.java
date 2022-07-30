package propra22.q8493367.contour;

import propra22.q8493367.animation.QuadrangleSequence;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.shape.Quadrangle;


/**
 * The Class DiameterAndQuadrangleCalculator provides a calculator, which takes
 * a convex hull as an argument and calculates the longest diameter and the quadrangle
 * with maximum area. It also provides a sequence of quadrangles, which is 
 * used by the animation.
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
	 * @param convexHull The convex hull
	 * 
	 */
	public DiameterAndQuadrangleCalculator(IHull convexHull) {
		this.convexHull = convexHull;
	}

	/**
	 * Calculates the diameter, the quadrangle with the biggest area and a sequence
	 * of quadrangles with respect to the sequence of antipodal pairs. One quadrangle 
	 * refers to a antipodal pair and it is the biggest quadrangle which can be found 
	 * for this antipodal pair. This sequence is used by the animation.
	 * 
	 * 
	 * @param diameter   the diameter object whose new attributes are calculated.
	 * @param quadrangle the quadrangle object whose new attributes are calculated.
	 * @param quadrangleSequence sequence of largest rectangles with respect to a pair of antipodes.
	 */
	public void calculate(IDiameter diameter, IQuadrangle quadrangle, QuadrangleSequence quadrangleSequence) {
        if(!convexHull.isEmpty()) {
        	
        	// caclulates the quadrangle sequence
        	calculateQuadrangleSequence(quadrangleSequence);
        	
        	/* Gets the diameter i.e. the longest diagonal in a quadrangle from the 
        	quadrangle sequence
        	*/
            diameter.copy(quadrangleSequence.getLongestDiameter());
            
            //Gets the quadrangle with the biggest area from the quadrangle sequence.
            quadrangle.copy(quadrangleSequence.getBiggestQuadrangle());
        }
	}
    
	/**
	 * Adds a quadrangle to the sequence of quadrangles.
	 *
	 * @param quadrangleSequence the quadrangle sequence
	 * @param quadrangle the quadrangle which is added to the
	 * quadrangle sequence.
	 */
	private void addQuadrangleToQuadrangleSequence(QuadrangleSequence quadrangleSequence, Quadrangle quadrangle) {
		if(quadrangleSequence != null) {
			quadrangleSequence.add(new Quadrangle(quadrangle.getA(), quadrangle.getB(), 
					quadrangle.getC(), quadrangle.getD()));
		}
	}
	
	/**
	 * Calculates the sequence of quadrangles.
	 *
	 * @param quadrangleSequence the quadrangle sequence
	 */
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
		
		while (Point.isHigher(c, a, bIt.getNextPoint(), bIt.getPoint())) {
			bIt.next();
		}

		while (Point.isHigher(a, c, dIt.getNextPoint(), dIt.getPoint())) {
			dIt.next();
		}

		return new Quadrangle(a, bIt.getPoint(), c, dIt.getPoint());
	}
}
