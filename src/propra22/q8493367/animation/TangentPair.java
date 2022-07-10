package propra22.q8493367.animation;

import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.convex.IHull;

import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;


/**
 * The Class TangentPair represents a pair of tangents. 
 * These two tangents stand at a constant angle to each other. In this
 * application the constant angle is pi, so the tangents are parallel.
 */
public class TangentPair implements ITangentPair {
    /**
     * The angle of the tangent as radian with respect to 
     * the vertical line going through the connection point
     * measured counterclockwise.
     */
	private double angle;
	
	/**
	 * The value in radian, by which the angle is increased when
	 * performing a step.
	 */
    private final double diff = Math.PI / 500;
    
	
    /**
     * The first tangent.
     */
    private Tangent tangent1;
	
    /**
     * The second Tangent.
     */
    private Tangent tangent2;
	
    /** The initial length of the tangent in pixels. */
    private double length = 400;
    
    /**
     * The points of the convex
     * hull in a list.
     */
	private IHull convexHull;
	
	/**
	 * Iterator representing the one point 
	 * of the antipodal pair
	 */
	private IHullIterator aIt;
	
	/**
	 * Iterator representing the other point 
	 * of the antipodal pair
	 */
	private IHullIterator cIt;
	
	/**
	 * The constructor sets angle to 0 with respect to 
	 * the vertical line going through the 
	 * contact point. The angle increases, when the
	 * tangent turns counterclockwise.
	 */
	
	
	public TangentPair() {
		
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(IHull hull){
		this.convexHull = hull;
		
		aIt = hull.getLeftIt();
		cIt = hull.getRightIt();
		
		this.tangent1 = new Tangent(aIt, 0);
		this.tangent2 = new Tangent(cIt, Math.PI);
		
		// calculate angles
	}
	


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void step() {
		//System.out.println("center Tangent1: " + tangent1.getCenter()+ " center Tangent2: " + tangent2.getCenter());
		if(nextAngleIsValid(tangent1) && nextAngleIsValid(tangent2)){
			increaseAngle();
		}
		else {
			//Only one tangent center changes
			IPoint tangent1PreviousCentre = aIt.getPoint();
			setIteratorsToPreviousAntipodalPair();
			if(tangent1PreviousCentre != aIt.getPoint()) {
				angle = tangent1.calculateAngle();
			} else {
				angle = tangent2.calculateAngle();
			}
		}
	}
	
	private void setIteratorsToNextAntipodalPair() {

		long angleComparisonTestResult = Point.angleComparisonTest(aIt.getPoint(), aIt.getNextPoint(), cIt.getPoint(), cIt.getNextPoint());
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
				cIt.next();
			} else {
				aIt.next();	
			}
		}	
	}
	
    
	private void setIteratorsToPreviousAntipodalPair() {
		
		long angleComparisonTestResult = Point.angleComparisonTest(aIt.getPoint(), aIt.getPreviousPoint(), cIt.getPoint(), cIt.getPreviousPoint());
		// angleComparisonResult > 0
		if (angleComparisonTestResult < 0) {
			aIt.previous();
		// angleComparisonResult < 0
		} else if (angleComparisonTestResult > 0) {
			cIt.previous();
		}
		// angleComparisonResult == 0
		else {
			if (Point.isShorter(aIt.getPoint(), aIt.getNextPoint(), cIt.getPoint(), cIt.getNextPoint())) {
				cIt.previous();
			} else {
				aIt.previous();	
			}
		}	
	}

	
	

	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPoint[] getTangent1(){
		return new IPoint[]{tangent1.getA(), tangent1.getCenter(),  tangent1.getB()};
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPoint[] getTangent2() {
		return new IPoint[]{tangent2.getA(), tangent2.getCenter(),  tangent2.getB()};
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLength(float length) {
		this.length = length;
	}
	
	/**
	 * Returns true, if for the next angle (angle + diff)
	 * the tangent is still preserving the tangent invariant: 
	 * One or two points lie on the tangent, the rest of the
	 * points, if they exist, all together on one side of 
	 * the tangent. 
	 * @param tangent - The tangent
	 * @return - True, if the tangent invariant is maintained
	 * false otherwise
	 */
	private boolean nextAngleIsValid(Tangent tangent) {
		return tangent.nextAngleIsValid() < 0;
	}
	

	/**
	 * Increases the angle by the constant diff.
	 */
	private void increaseAngle() {
		angle += diff;
	}
	
	
	

    /**
     * This class represents one tangent. The angle is part
     * of the surrounding class TangentPair.
     */
	private class Tangent{
		/**
		 * This index represents the index of the point of the convex hull, 
		 * which is the point of contact.
		 */
		private IHullIterator it;
        
		/**
		 * The angleOffset is added to the angle. In our case this is PI, 
		 * so it is guaranteed that both tangents of the tangent pair are parallel.
		 */
		private double angleOffset;
		
		/**
		 * The constructor of a tangent takes the center index for
		 * the contact point and the offset for the angle.
		 * @param centerIndex - The index in the hull for the contact point
		 * @param angleOffset - The offset for the angle
		 */
		public Tangent(IHullIterator it, double angleOffset) {
		    this.it = it;
			this.angleOffset = angleOffset;
		}
		
		

	
		
		// vielleicht funktionales Interface für die Richtung?

		public double calculateAngle() {
			return getAngle(it.getPoint(), it.getNextPoint()) - angleOffset;
		}
		
		
		private  double  getAngle(IPoint center, IPoint previous) {
			double dx = center.getX() - previous.getX();
			double dy = previous.getY() - center.getY();
			double result =  Math.atan2(dx, dy);
			return result;
		}







		/**
		 * Moves the contact point to the previous point in
		 * the convex hull. We need to take the previous point 
		 * because we want the tangents to turn counterclockwise
		 * and we want to use a standard cartesian coordinate 
		 * system in the GUI.
		 */
		
		
		
        /**
         * Returns true if the characteristic of a tangent is maintained. 
         * That is, One or two points lie on the tangent and all other points, 
         * if they exist, lie on exactly one side of the tangent.
         * 
         * @return True, if the next angle is still maintaining the 
         * characteristic of the tangent described above.
         * False otherwise.
         */
		
		// Auch hier: funktionales Interface
		private long nextAngleIsValid() {
			IPoint center = getCenter();
			IPoint nextB = getNextB();
			
			long result = Point.signedTriangleArea(center, nextB, it.getPreviousPoint());
			return result;
		}

		/**
		 * Gets the contact point of the tangent.
		 *
		 * @return - The contact point
		 */
		public IPoint getCenter() {
			return it.getPoint();
		}
		
		

	
		/**
		 * Gets the length of the tangent in pixels.
		 *
		 * @return - The length
		 */
		public double getLength() {
			return length;
		}

		
		/**
		 * Gets end A of the tangent as a point.
		 * @return - One end of the tangent
		 */
		public IPoint getA() {
			int x = (int)Math.round(it.getPoint().getX() - length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(it.getPoint().getY() + length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		} 
		
		/**
		 * Gets end B of the tangent as a point.
		 * @return - One end of the tangent
		 */
		public IPoint getB() {
		
			int x = (int)Math.round(it.getPoint().getX() + length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(it.getPoint().getY() - length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		}
		
		/**
		 * Gets end B of the tangent as a point for angle + pDiff.
		 *
		 * @param pDiff the diff
		 * @return - One end of the tangent
		 */
		private IPoint getNextB() {
			
			int x = (int)Math.round(it.getPoint().getX() + length/2 * Math.sin(angle + angleOffset + diff));
			int y = (int)Math.round(it.getPoint().getY() - length/2 * Math.cos(angle + angleOffset + diff));
			return new Point(x, y);
		}
		
		/**
		 * Gets end A of the tangent as a point for angle + pDiff.
		 *
		 * @param pDiff the diff
		 * @return - One end of the tangent
		 */
		private IPoint getNextA(double pDiff) {
			int x = (int)Math.round(it.getPoint().getX() - length/2 * Math.sin(angle + angleOffset + pDiff));
			int y = (int)Math.round(it.getPoint().getY() + length/2 * Math.cos(angle + angleOffset + pDiff));
			return new Point(x, y);
		}
		
		/**
		 * Gets the next point in the convex hull turning clockwise 
		 * since the GUI is based on a standard cartesian coordinate system.
		 * @return - The next point in the convex hull
		 * 
		 */
		
		
		
	}	
}
