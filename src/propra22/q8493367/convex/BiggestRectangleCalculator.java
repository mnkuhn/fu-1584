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

	private static long DFV(IPoint a, IPoint b, IPoint c) {

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

	private static long AngleComparisonTest(HullIterator aIterator, HullIterator bIterator) {
		long xTip = (long) aIterator.getPoint().getX() + (long) bIterator.getPoint().getX() - (long) bIterator.getNextPoint().getX();
		long yTip = (long) aIterator.getPoint().getY() + (long) bIterator.getPoint().getY() - (long) bIterator.getNextPoint().getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return DFV(aIterator.getPoint(), aIterator.getNextPoint(), tip);
	}

	public void calculate() {

		IPoint maxDiameterA = null;
		IPoint maxDiameterB = null;

		HullIterator aIt = hull.getIterator(0, hull.getIndexOfRightMostPoint() + 1);
		HullIterator cIt = hull.getIterator(hull.getIndexOfRightMostPoint(), 1);
		
		if (hull.size() == 1 || hull.size() == 2) {
			maxDiameterA = aIt.getPoint();
			maxDiameterB = cIt.getPoint();
		
		} else if (hull.size() >= 3) {
			/*
			int aIndex = 0;
			int cIndex = hull.getIndexOfRightMostPoint();
            
			IPoint aPoint = hull.get(aIndex);;
			IPoint afterAPoint = hull.get((aIndex + 1) % hull.size());
			IPoint cPoint = hull.get(cIndex);
			IPoint afterCPoint = hull.get((cIndex + 1) % hull.size());
			*/
            
			// first diameter
			maxDiameterA = aIt.getPoint();
			maxDiameterB = cIt.getPoint();
			
			// first rectangle
			//Rectangle maxRectangle = calculateRectangle(aIndex, cIndex);

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
				
				/*Rectangle rectangle = calculateRectangle(aIndex, cIndex);
				if(areaIsBigger(rectangle, maxRectangle)) {
					maxRectangle = rectangle;
				}
				*/
				

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
							
						}
						cIt.next();
						
					} else {
						if (isLonger(aIt.getPoint(), cIt.getNextPoint(), maxDiameterA, maxDiameterB)) {
							maxDiameterA = aIt.getPoint();
							maxDiameterB = cIt.getNextPoint();
							
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
	}
	
	
	private boolean areaIsBigger(Rectangle rectangle, Rectangle maxRectangle) {
		long area = DFV(rectangle.getA(), rectangle.getB(), rectangle.getC()) + DFV(rectangle.getA(), rectangle.getC(), rectangle.getD());
		long maxArea = DFV(maxRectangle.getA(), maxRectangle.getB(), maxRectangle.getC()) + DFV(maxRectangle.getA(), maxRectangle.getC(), maxRectangle.getD());
		return area > maxArea;
	}

	private boolean isHigher(IPoint a, IPoint c, IPoint d, IPoint dDash) {
		long x = (long)a.getX() + (long)dDash.getX() - (long)c.getX();
		long y = (long)a.getY() + (long)dDash.getY() - (long)c.getY();
		IPoint sum = new Point((int)x, (int)y);
		return DFV(sum, dDash, d) > 0;
		
	}
	
	private Rectangle calculateRectangle(int aIndex, int cIndex) {
		IPoint a = hull.get(aIndex);
		IPoint c = hull.get(cIndex);
		IPoint b = a;
		IPoint d = c;
		int bIndex = aIndex;
		int dIndex = cIndex;
		IPoint afterB = hull.get((bIndex + 1) % hull.size());
		IPoint afterD = hull.get((dIndex + 1) % hull.size());
		
		while(isHigher(c, a, afterB, b)) {
			bIndex = (bIndex + 1) % hull.size();
			b = hull.get(bIndex);
			afterB = hull.get((bIndex + 1) % hull.size());
		}
		while(isHigher(a, c, afterD, d)) {
			dIndex = (dIndex + 1) % hull.size();
			d = hull.get(dIndex);
			afterD = hull.get((dIndex + 1) % hull.size());
		}
		return new Rectangle(a, b, c, d);
	    
	}
}
