package propra22.q8493367.convex;

import java.util.List;

import propra22.q8493367.draw.model.Hull.HullIterator;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class BiggestRectangleCalculator {

	private IHull hull;

	public BiggestRectangleCalculator(IHull hull) {
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

	private  long AngleComparisonTest(HullIterator aIterator, HullIterator bIterator) {
		long xTip = (long) aIterator.getPoint().getX() + (long) bIterator.getPoint().getX() - (long) bIterator.getNextPoint().getX();
		long yTip = (long) aIterator.getPoint().getY() + (long) bIterator.getPoint().getY() - (long) bIterator.getNextPoint().getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return DFV(aIterator.getPoint(), aIterator.getNextPoint(), tip);
	}

	public void calculate() {

		IPoint maxDiameterA = null;
		IPoint maxDiameterB = null;
		
		Quadrangle maxRectangle = null;
		
		HullIterator aIt = hull.getIterator(0, hull.getIndexOfRightMostPoint() + 1);
		HullIterator cIt = hull.getIterator(hull.getIndexOfRightMostPoint(), 1);
		
		if (hull.size() == 1 || hull.size() == 2) {
			maxDiameterA = aIt.getPoint();
			maxDiameterB = cIt.getPoint();
		
		} else if (hull.size() >= 3) {
			
			// first diameter
			maxDiameterA = aIt.getPoint();
			maxDiameterB = cIt.getPoint();
			
	
		    maxRectangle = calculateRectangle(aIt, cIt);

			while (!(aIt.getIndex() > hull.getIndexOfRightMostPoint()) || !(cIt.getIndex() < hull.getIndexOfRightMostPoint())) {
                /*
				aPoint = hull.get(aIndex);
				afterAPoint = hull.get((aIndex + 1) % hull.size());
				cPoint = hull.get(cIndex);
				afterCPoint = hull.get((cIndex + 1) % hull.size());
				*/

				if (isLonger(aIt.getPoint(), cIt.getPoint(), maxDiameterA, maxDiameterB)) {
					maxDiameterA = aIt.getPoint();
					maxDiameterB = cIt.getPoint();
					
				}
				
				Quadrangle rectangle = calculateRectangle(aIt, cIt);
				if(rectangle.area() > maxRectangle.area()) {
					maxRectangle = rectangle;
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
							
						    HullIterator tmp = hull.getIterator(aIt.getIndex() + 1, cIt.getIndex() + 2);
							rectangle = calculateRectangle(tmp, cIt);
							if(rectangle.area() > maxRectangle.area()) {
								maxRectangle = rectangle;
							}
						}
						cIt.next();
						
					} else {
						if (isLonger(aIt.getPoint(), cIt.getNextPoint(), maxDiameterA, maxDiameterB)) {
							maxDiameterA = aIt.getPoint();
							maxDiameterB = cIt.getNextPoint();
							
							HullIterator tmp = hull.getIterator(cIt.getIndex() + 1, aIt.getIndex() + 1);
							rectangle = calculateRectangle(aIt, tmp);
							if(rectangle.area() > maxRectangle.area()) {
								maxRectangle = rectangle;
							}	
						}
						aIt.next();
					}
				}
			}
		}

		IPoint[] diameter = new IPoint[2];
		diameter[0] = maxDiameterA;
		diameter[1] = maxDiameterB;
		hull.setDiameter(diameter);
		hull.setRectangle(maxRectangle);
	}
	
	
	

	private boolean isHigher(IPoint a, IPoint c, IPoint d, IPoint dDash) {
		long x = (long)a.getX() + (long)dDash.getX() - (long)c.getX();
		long y = (long)a.getY() + (long)dDash.getY() - (long)c.getY();
		IPoint sum = new Point((int)x, (int)y);
		return DFV(sum, dDash, d) > 0;
		
	}
	
	private Quadrangle calculateRectangle(HullIterator aIterator, HullIterator cIterator) {
	
		HullIterator bIterator = hull.getIterator(aIterator.getIndex(), cIterator.getIndex() + 1);
		HullIterator dIterator = hull.getIterator(cIterator.getIndex(), aIterator.getIndex() + 1);
		
		while(isHigher(cIterator.getPoint(), aIterator.getPoint(), bIterator.getNextPoint(), bIterator.getPoint())) {
			bIterator.next();
		}
		while(isHigher(aIterator.getPoint(), cIterator.getPoint(), dIterator.getNextPoint(), dIterator.getPoint())) {
			dIterator.next();
		}
		return new Quadrangle(aIterator.getPoint(), bIterator.getPoint(), cIterator.getPoint(), dIterator.getPoint());    
	}
}
