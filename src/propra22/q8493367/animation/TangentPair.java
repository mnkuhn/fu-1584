package propra22.q8493367.animation;


import propra22.q8493367.contour.IHull;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;


// TODO: Auto-generated Javadoc
/**
 * The Class TangentPair represents a pair of tangents. 
 * These two tangents stand at a constant angle to each other. In this
 * application the constant angle is pi, so the tangents are parallel.
 */
public class TangentPair {
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
     * The two tangents of the tangent pair
     */
    private Tangent tangent1;
    private Tangent tangent2;
	
    /** The initial length of the tangent in pixels. */
    private double length = 400;
    
    

	private AntipodalPairs antipodalPairs;
	
	/**
	 * The constructor sets angle to 0 with respect to 
	 * the vertical line going through the 
	 * contact point. The angle increases, when the
	 * tangent turns counterclockwise.
	 */
	
	
	public TangentPair() {
		
		antipodalPairs = new AntipodalPairs();
		tangent1 = new Tangent(0, 0);
		tangent2 = new Tangent(Math.PI, 1);
	}
	
	
	


	/**
     * Calculates the new slope of the tangent lines 
     * and the point of contact.
     */
	public void step() {
		if(nextAngleIsValid()) {
			increaseAngle();
		}
		else {
			antipodalPairs.previous();
			calculateAngle();	
		}
	}
	
	
	private boolean nextAngleIsValid() {
		return tangent1.nextAngleIsValid() && tangent2.nextAngleIsValid();
	}





	public void updateAntipodalPairs(IHull convexHull) {
		antipodalPairs.update(convexHull);
	}
	
	public void fitToAngle() {
		//If diameter it not zero, it might be necessary to calulcate a new antipodal pair.
		if(!antipodalPairs.diameterIsZero()) {
			while(!angleIsValid()){
				antipodalPairs.next();
			}
		}
		//If diameter is zero, angle can be kept anyway.
	}
	
	public boolean angleIsValid() {
		return tangent1.angleIsValid() && tangent2.angleIsValid();
	}
	
	public double calculateAngle() {
		double angle1 = tangent1.calculateAngle();
		double angle2 = tangent2.calculateAngle();
		
		return angle1 < angle2 ? angle1 : angle2;
	}
	


	/**
	 * Gets the first tangent
	 * @return Array of the two
	 * endpoints (index 0 and 2) and the contact point 
	 * (index 1) of the tangent.
	 */

	public IPoint[] getTangent1(){
		return new IPoint[]{tangent1.getA(), tangent1.getCenter(),  tangent1.getB()};
	}
	
	/**
	 * Gets the second tangent
	 * @return Array of the two
	 * endpoints (index 0 and 2) and the contact point 
	 * (index 1) of the tangent.
	 */

	public IPoint[] getTangent2() {
		return new IPoint[]{tangent2.getA(), tangent2.getCenter(),  tangent2.getB()};
	}
	
	/**
	 * Sets the length of the tangents
	 * @param lenght the length of the tangent in pixels
	 */

	public void setLength(float length) {
		this.length = length;
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
		int antipodalPoint;
        
		/**
		 * The angleOffset is added to the angle. In our case this is PI, 
		 * so it is guaranteed that both tangents of the tangent pair are parallel.
		 */
		private double angleOffset;
		
		/**
		 * The constructor of a tangent takes the center index for
		 * the contact point and the offset for the angle.
		 *
		 * @param it the it
		 * @param angleOffset - The offset for the angle
		 * @throws Exception 
		 */
		public Tangent(double angleOffset, int antipodalPoint) {
			this.angleOffset = angleOffset;
			this.antipodalPoint = antipodalPoint;
		}
		
		


		/**
		 * Calculate angle.
		 *
		 * @return the double
		 */
		
		
		public double calculateAngle() {
			return getAngle(getCenter(), getNextHullPoint()) - angleOffset;
		}
		
		
		
		/**
		 * Gets the angle.
		 *
		 * @param center the center
		 * @param previous the previous
		 * @return the angle
		 */
		private  double  getAngle(IPoint center, IPoint previous) {
			double dx = center.getX() - previous.getX();
			double dy = previous.getY() - center.getY();
			double result =  Math.atan2(dx, dy);
			return result;
		}


		
		
        /**
         * Returns true if the characteristic of a tangent is maintained. 
         * That is, One or two points lie on the tangent and all other points, 
         * if they exist, lie on exactly one side of the tangent.
         * 
         * @return True, if the next angle is still maintaining the 
         * characteristic of the tangent described above.
         * False otherwise.
         */
		
		
		private boolean nextAngleIsValid() {
			if(antipodalPairs.diameterIsZero()) {
				return true;
			}
			else {
				IPoint center = getCenter();
				IPoint nextB = getNextB();
				IPoint previousHullPoint = antipodalPairs.getHullPointBefore(antipodalPoint);

				long result = Point.signedTriangleArea(center, nextB, previousHullPoint);
				return result <= 0;
			}
		}
		
		private boolean angleIsValid() {
			IPoint center = getCenter();
			
			IPoint a = getA();
			IPoint b = getB();
			
			IPoint previousHullPoint = antipodalPairs.getHullPointBefore(antipodalPoint);
			IPoint nextHullPoint = antipodalPairs.getHullPointAfter(antipodalPoint);
			
			
			long result1 = Point.signedTriangleArea(center, b, previousHullPoint);
			long result2 = Point.signedTriangleArea(nextHullPoint, a, center);
			
			return (result1 <= 0) && (result2 <= 0);
		}
		
		
		
		

		/**
		 * Gets the contact point of the tangent.
		 *
		 * @return - The contact point
		 */
		public IPoint getCenter() {
			return antipodalPairs.getHullPoint(antipodalPoint);
		}
		
		public IPoint getNextHullPoint() {
			return antipodalPairs.getHullPointAfter(antipodalPoint);
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
		 * @return One end of the tangent
		 */
		public IPoint getA() {
			int x = (int)Math.round(getCenter().getX() - length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(getCenter().getY() + length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		}
		
		/**
		 * Gets end A of the tangent as a point for angle + pDiff.
		 *
		 * @param pDiff the diff
		 * @return One end of the tangent
		 */
		private IPoint getNextA(double pDiff) {
			int x = (int)Math.round(getCenter().getX() - length/2 * Math.sin(angle + angleOffset + pDiff));
			int y = (int)Math.round(getCenter().getY() + length/2 * Math.cos(angle + angleOffset + pDiff));
			return new Point(x, y);
		}
		
		/**
		 * Gets end B of the tangent as a point.
		 * @return One end of the tangent
		 */
		public IPoint getB() {
		
			int x = (int)Math.round(getCenter().getX() + length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(getCenter().getY() - length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		}
		
		/**
		 * Gets end B of the tangent as a point for angle + pDiff.
		 *
		 * @return One end of the tangent
		 */
		private IPoint getNextB() {
			
			int x = (int)Math.round(getCenter().getX() + length/2 * Math.sin(angle + angleOffset + diff));
			int y = (int)Math.round(getCenter().getY() - length/2 * Math.cos(angle + angleOffset + diff));
			return new Point(x, y);
		}
		
		
		
	}

}
