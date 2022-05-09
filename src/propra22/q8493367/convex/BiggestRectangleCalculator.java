package propra22.q8493367.convex;

import java.util.List;

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

	private static long AngleComparisonTest(IPoint aPoint, IPoint afterAPoint, IPoint bPoint, IPoint afterBPoint) {
		long xTip = (long) aPoint.getX() + (long) bPoint.getX() - (long) afterBPoint.getX();
		long yTip = (long) aPoint.getY() + (long) bPoint.getY() - (long) afterBPoint.getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return DFV(aPoint, afterAPoint, tip);
	}

	public void calculate() {

		IPoint maxAPoint = null;
		IPoint maxBPoint = null;

		List<IPoint> list = hull.toList();
		if (list.size() == 1) {
			maxAPoint = list.get(0);
			maxBPoint = list.get(0);
		} else if (list.size() == 2) {
			maxAPoint = list.get(0);
			maxBPoint = list.get(1);
		} else if (list.size() >= 3) {
			int aIndex = 0;
			int bIndex = hull.getIndexOfRightMostPoint();

			IPoint aPoint = list.get(aIndex);
			IPoint afterAPoint = list.get(aIndex + 1);
			IPoint bPoint = list.get(bIndex);
			IPoint afterBPoint = list.get(bIndex + 1);

			maxAPoint = aPoint;
			maxBPoint = bPoint;

			while (aIndex < hull.getIndexOfRightMostPoint() || bIndex >= hull.getIndexOfRightMostPoint()) {

				aPoint = list.get(aIndex);
				afterAPoint = list.get((aIndex + 1) % list.size());
				bPoint = list.get(bIndex);
				afterBPoint = list.get((bIndex + 1) % list.size());

				if (isLonger(aPoint, bPoint, maxAPoint, maxBPoint)) {
					maxAPoint = aPoint;
					maxBPoint = bPoint;
				}

				long angleComparisonTestResult = AngleComparisonTest(aPoint, afterAPoint, bPoint, afterBPoint);

				// angleComparisonResult > 0
				if (angleComparisonTestResult > 0) {
					aIndex = (aIndex + 1) % list.size();

					// angleComparisonResult < 0
				} else if (angleComparisonTestResult < 0) {
					bIndex = (bIndex + 1) % list.size();
				}

				// angleComparisonResult == 0
				else {
					if (isShorter(aPoint, afterAPoint, bPoint, afterBPoint)) {
						if (isLonger(afterAPoint, bPoint, maxAPoint, maxBPoint)) {
							maxAPoint = afterAPoint;
							maxBPoint = bPoint;
						}
						bIndex = (bIndex + 1) % list.size();
					} else {
						if (isLonger(aPoint, afterBPoint, maxAPoint, maxBPoint)) {
							maxAPoint = aPoint;
							maxBPoint = afterBPoint;
						}
						aIndex = (aIndex + 1) % list.size();
					}
				}
			}
		}

		IPoint[] diameter = new IPoint[2];
		diameter[0] = maxAPoint;
		diameter[1] = maxBPoint;
		hull.setDiameter(diameter);
	}

	public static void main(String[] args) {

		IPoint aP = new Point(1, 10);
		IPoint b = new Point(11, 10);
		IPoint afterA = new Point(2, 15);
		IPoint afterB = new Point(11, 5);

		System.out.println(AngleComparisonTest(aP, afterA, b, afterB));
	}
}
