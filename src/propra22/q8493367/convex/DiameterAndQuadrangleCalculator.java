package propra22.q8493367.convex;

import java.util.List;

import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class DiameterAndQuadrangleCalculator {

	private IHull hull;

	public DiameterAndQuadrangleCalculator(IHull hull) {
		this.hull = hull;
	}

	private long DFV(IPoint a, IPoint b, IPoint c) {

		long summand1 = (long) a.getX() * ((long) b.getY() - (long) c.getY());
		long summand2 = (long) b.getX() * ((long) c.getY() - (long) a.getY());
		long summand3 = (long) c.getX() * ((long) a.getY() - (long) b.getY());
		// change the sign because (0, 0) is in the upper left corner
		return -(summand1 + summand2 + summand3);
	}

	private long qaudraticDistance(IPoint a, IPoint b) {
		long dx = (long) a.getX() - (long) b.getX();
		long dy = (long) a.getY() - (long) b.getY();
		return dx * dx + dy * dy;
	}

	private boolean isShorter(IPoint a, IPoint b, IPoint c, IPoint d) {
		return qaudraticDistance(a, b) < qaudraticDistance(c, d);
	}

	private boolean isLonger(IPoint a, IPoint b, IPoint c, IPoint d) {
		return qaudraticDistance(a, b) > qaudraticDistance(c, d);
	}

	private  long AngleComparisonTest(IHullIterator aIterator, IHullIterator bIterator) {
		long xTip = (long) aIterator.getPoint().getX() + (long) bIterator.getPoint().getX() - (long) bIterator.getNextPoint().getX();
		long yTip = (long) aIterator.getPoint().getY() + (long) bIterator.getPoint().getY() - (long) bIterator.getNextPoint().getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return DFV(aIterator.getPoint(), aIterator.getNextPoint(), tip);
	}

	public void calculate(Diameter diameter, Quadrangle quadrangle) {

		IPoint maxDiameterA = null;
		IPoint maxDiameterB = null;
		
		Quadrangle maxQuadrangle = null;
		
		// Set iterators with limits
		IHullIterator aIt = hull.getIterator(0, hull.getIndexOfRightMostPoint() + 1);
		IHullIterator cIt = hull.getIterator(hull.getIndexOfRightMostPoint(), 1);
		
		// Hull has only one point
		if (hull.size() == 1 ) {
			
			// first diameter
			maxDiameterA = aIt.getPoint();
			maxDiameterB = aIt.getPoint();
			
			maxQuadrangle = new Quadrangle(aIt.getPoint(), aIt.getPoint(), aIt.getPoint(), aIt.getPoint());
		// Hull has two points
		} else if (hull.size() == 2) {
			
			// first diameter
			maxDiameterA = aIt.getPoint();
			maxDiameterB = cIt.getPoint();
			
			maxQuadrangle = new Quadrangle(aIt.getPoint(), aIt.getPoint(), cIt.getPoint(), cIt.getPoint());
			
		} else if (hull.size() >= 3) {
		    
			// first diameter
		    maxDiameterA = aIt.getPoint();
			maxDiameterB = cIt.getPoint();

			maxQuadrangle = calculateRectangle(aIt, cIt);

			while ((!aIt.hasReachedLimit()) || (!cIt.hasReachedLimit())) {
				
               
				if (isLonger(aIt.getPoint(), cIt.getPoint(), maxDiameterA, maxDiameterB)) {
					maxDiameterA = aIt.getPoint();
					maxDiameterB = cIt.getPoint();
					
				}
				
				Quadrangle rectangle = calculateRectangle(aIt, cIt);
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
							
							IHullIterator tmp = hull.getIterator(aIt.getIndex() + 1, cIt.getIndex() + 2);
							rectangle = calculateRectangle(tmp, cIt);
							if(rectangle.area() > maxQuadrangle.area()) {
								maxQuadrangle = rectangle;
							}
						}
						cIt.next();
						
					} else {
						if (isLonger(aIt.getPoint(), cIt.getNextPoint(), maxDiameterA, maxDiameterB)) {
							maxDiameterA = aIt.getPoint();
							maxDiameterB = cIt.getNextPoint();
							
							IHullIterator tmp = hull.getIterator(cIt.getIndex() + 1, aIt.getIndex() + 1);
							rectangle = calculateRectangle(aIt, tmp);
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
	
	
	

	private boolean isHigher(IPoint a, IPoint c, IPoint d, IPoint dDash) {
		long x = (long)a.getX() + (long)dDash.getX() - (long)c.getX();
		long y = (long)a.getY() + (long)dDash.getY() - (long)c.getY();
		IPoint sum = new Point((int)x, (int)y);
		return DFV(sum, dDash, d) > 0;
		
	}
	
	private Quadrangle calculateRectangle(IHullIterator aIterator, IHullIterator cIterator) {
	
		IHullIterator bIterator = hull.getIterator(aIterator.getIndex(), cIterator.getIndex() + 1);
		IHullIterator dIterator = hull.getIterator(cIterator.getIndex(), aIterator.getIndex() + 1);
		
		while(isHigher(cIterator.getPoint(), aIterator.getPoint(), bIterator.getNextPoint(), bIterator.getPoint())) {
			bIterator.next();
		}
		while(isHigher(aIterator.getPoint(), cIterator.getPoint(), dIterator.getNextPoint(), dIterator.getPoint())) {
			dIterator.next();
		}
		return new Quadrangle(aIterator.getPoint(), bIterator.getPoint(), cIterator.getPoint(), dIterator.getPoint());    
	}
}
