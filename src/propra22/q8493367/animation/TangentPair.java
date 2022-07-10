package propra22.q8493367.animation;

import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.convex.IHull;
import propra22.q8493367.draw.model.Diameter;
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
    
    //private final double offSet = Math.PI;
    
    private int index;
	
    /**
     * The first tangent.
     */
    private Tangent tangent1;
	
    /**
     * The second Tangent.
     */
    private Tangent tangent2;
	
    /** The length of the tangent in pixels. */
    private double length = 400;
    
    /**
     * The points of the convex
     * hull in a list.
     */
	private IHull hull;
	
	
	IHullIterator aIt;
	IHullIterator cIt;
	
	/**
	 * The constructor sets angle to 0 with respect to 
	 * the vertical line going through the 
	 * contact point. The angle increases, when the
	 * tangent turns counterclockwise.
	 */
	
	private List<IPoint[]> antipodalPairs = new ArrayList<>(); 
	
	public TangentPair() {
		
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(IHull hull){
		this.hull = hull;
		
		aIt = hull.getLeftIt();
		cIt = hull.getRightIt();
		
		// calculate angles
	}
	
	
	
	// als static in die Hülle?
	private  long AngleComparisonTest(IHullIterator aIterator, IHullIterator bIterator) {
		long xTip = (long) aIterator.getPoint().getX() + (long) bIterator.getPoint().getX() - (long) bIterator.getNextPoint().getX();
		long yTip = (long) aIterator.getPoint().getY() + (long) bIterator.getPoint().getY() - (long) bIterator.getNextPoint().getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return Point.signedTriangleArea(aIterator.getPoint(), aIterator.getNextPoint(), tip);
	}

	

	private  double  getAngle(IPoint a, IPoint b) {
		double dx = b.getX() - a.getX();
		double dy = a.getY() - b.getY();
		return Math.atan2(dx, dy);
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
			// calculate next antipodal pair
			// calculate angle
		}
		

	}
	
    
	private void findPreviousAntipodalPair() {
		
	}

	private void calculateAngle() {
		double angle1 = tangent1.calculateAngle();
		double angle2 = tangent2.calculateAngle();
		if(angle1 > angle2) {
			angle = angle1;
		}
		else {
			angle = angle2;
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
		int id;
        
		/**
		 * The angleOffset is added to the angle. In our case this is PI, 
		 * so it is guaranteed that both tangents of the tangent pair are parallel.
		 */
		double angleOffset;
		
		/**
		 * The constructor of a tangent takes the center index for
		 * the contact point and the offset for the angle.
		 * @param centerIndex - The index in the hull for the contact point
		 * @param angleOffset - The offset for the angle
		 */
		public Tangent(int id, double angleOffset) {
			
			this.id = id;
			this.angleOffset = angleOffset;
		}
		
		

		public double calculateAngle() {
			return getAngle(getCenter(), getPreviousHullPoint()) - angleOffset;	
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
		private long nextAngleIsValid() {
			IPoint center = getCenter();
			IPoint nextB = getNextB();
			IPoint previousHullPoint = getPreviousHullPoint();
			long result = Point.signedTriangleArea(center, nextB, previousHullPoint);
			return result;
		}

		/**
		 * Gets the contact point of the tangent.
		 *
		 * @return - The contact point
		 */
		public IPoint getCenter() {
			return antipodalPairs.get(Math.floorMod(index, antipodalPairs.size()) )[id];
		}
		
		public IPoint getPreviousHullPoint() {
			int i = 1;
			int lindex = Math.floorMod(index - i, antipodalPairs.size()) ;
			while(antipodalPairs.get( lindex )[id] == getCenter()) {
				i--;
				lindex = Math.floorMod(index - i, antipodalPairs.size()) ;
			}
			return  antipodalPairs.get(Math.floorMod(index - i, antipodalPairs.size()) )[id];
		}
		
		public IPoint getNextHullPoint() {
			int i = 1;
			while(antipodalPairs.get(Math.floorMod(index + i, antipodalPairs.size()) )[id] == getCenter()) {
				i++;
			}
			return  antipodalPairs.get(Math.floorMod(index + i, antipodalPairs.size()) )[id];
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
			int x = (int)Math.round(antipodalPairs.get(index)[id].getX() - length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(antipodalPairs.get(index)[id].getY() + length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		} 
		
		/**
		 * Gets end B of the tangent as a point.
		 * @return - One end of the tangent
		 */
		public IPoint getB() {
		
			int x = (int)Math.round(antipodalPairs.get(index)[id].getX() + length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(antipodalPairs.get(index)[id].getY() - length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		}
		
		/**
		 * Gets end B of the tangent as a point for angle + pDiff.
		 *
		 * @param pDiff the diff
		 * @return - One end of the tangent
		 */
		private IPoint getNextB() {
			int xc = antipodalPairs.get(index)[id].getX();
			double sin =  Math.sin(angle + angleOffset + diff);
			int x = (int)Math.round(antipodalPairs.get(index)[id].getX() + length/2 * Math.sin(angle + angleOffset + diff));
			int y = (int)Math.round(antipodalPairs.get(index)[id].getY() - length/2 * Math.cos(angle + angleOffset + diff));
			return new Point(x, y);
		}
		
		/**
		 * Gets end A of the tangent as a point for angle + pDiff.
		 *
		 * @param pDiff the diff
		 * @return - One end of the tangent
		 */
		private IPoint getNextA(double pDiff) {
			int x = (int)Math.round(antipodalPairs.get(index)[id].getX() - length/2 * Math.sin(angle + angleOffset + pDiff));
			int y = (int)Math.round(antipodalPairs.get(index)[id].getY() + length/2 * Math.cos(angle + angleOffset + pDiff));
			return new Point(x, y);
		}
		
		/**
		 * Gets the next point in the convex hull turning clockwise 
		 * since the GUI is based on a standard cartesian coordinate system.
		 * @return - The next point in the convex hull
		 * 
		 */
		
		
		
		private long angeComparisonTest(IPoint a, IPoint afterA, IPoint c, IPoint afterC) {
			long xTip = (long) a.getX() + (long) c.getX() - (long) afterC.getX();
			long yTip = (long) a.getY() + (long) c.getY() - (long) afterC.getY();
			IPoint tip = new Point((int)xTip, (int)yTip);
			return Point.signedTriangleArea(a, afterA, tip);
		}
	}	
}
