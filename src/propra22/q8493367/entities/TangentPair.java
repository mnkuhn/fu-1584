package propra22.q8493367.entities;

/**
 * The Class TangentPair represents a pair of tangents. 
 * These two tangents stand at a constant angle (in radians) to each other. In this
 * application the constant angle is pi, so the tangents are parallel.
 */
public class TangentPair {
    /**
     * The angle of the tangent in radians with respect to 
     * the vertical line going through the contact point
     * measured counterclockwise.
     */
	private double angle;
	
	/**
	 * The value in radians, by which the angle is increased when
	 * performing a step.
	 */
    private final double delta = Math.PI / 500;
    
	//The two tangents of the tangent pair
    /** The first tangent. */
    private Tangent tangent1;
    
    /** The second tangent. */
    private Tangent tangent2;
    
    
	
    /** The initial length of the tangent in pixels. 
     * The length refers to the coordinate system of the view.
     * */
    private double length = 400;
    
    

	/** The quadrangle sequence. */
	private QuadrangleSequence quadrangleSequence;
	
	/**
	 * The constructor sets the angle to 0. The angle refers to 
	 * the vertical line going through the 
	 * contact point. The angle increases, when the
	 * tangent turns counterclockwise.
	 * 
	 * @param quadrangleSequence the sequence of quadrangles 
	 * used for the animation. The tangent pair needs this sequence
	 * to find the fitting quadrangle for a given angle.
	 */
	
	
	public TangentPair(QuadrangleSequence quadrangleSequence) {
		
		this.quadrangleSequence = quadrangleSequence;
		tangent1 = new Tangent(0, QuadranglePoint.A);
		tangent2 = new Tangent(Math.PI, QuadranglePoint.C);
	}
	

	/**
     * Calculates the new slope of the tangents 
     * and the points of contact.
     */
	public void step() {
		if(nextAngleIsValid()) {
			increaseAngle();
		}
		else {
			quadrangleSequence.previous();
			calculateAngle();	
		}
	}
	
	
	/**
	 * Returns true, if for the next angle for both tangents all points of the convex hull except the 
	 * tangential points lie on the same side of the respective tangent. Returns false 
	 * otherwise.
	 *
	 * @return true, if for the next angle the tangent characteristics of both tangents are ensured.
	 * Returns false otherwise.
	 */
	private boolean nextAngleIsValid() {
		return tangent1.nextAngleIsValid() && tangent2.nextAngleIsValid();
	}

	
	/**
	 * Finds the antipodal pair for a given angle.
	 */
	public void fitToAngle() {
		/*If diameter it not zero, it might be necessary to calulcate a new antipodal pair
		 * given by the points A and C of one of the quadrangles of the quadrangle sequence.
		 * If diameter is zero, there is only one point, so there is no need for searching.
		 */
		if(!quadrangleSequence.longestDiameterIsZero()) {
			while(!angleIsValid()){
				quadrangleSequence.next();
			}
		}
	}
	
	/**
	 * Returns true, if for the next angle for both tangents all points of the convex hull 
	 * except the tangential points lie on the same side of the respective tangent.
	 * Returns false otherwise.
	 *
	 * @return true, if the tangent characteristics of both tangents ensured. False otherwise.
	 */
	public boolean angleIsValid() {
		return tangent1.angleIsValid() && tangent2.angleIsValid();
	}
	
	/**
	 * Calculates the smallest angle for a given antipodal pair for the tangent pair.
	 *
	 * @return the angle in radians
	 */
	public double calculateAngle() {
		double angle1 = tangent1.calculateAngle();
		double angle2 = tangent2.calculateAngle();
		return angle1 < angle2 ? angle1 : angle2;
	}
	


	/**
	 * Gets the first tangent.
	 *
	 * @return Array of the two end points (index 0 and 2) and the contact point 
	 * (index 1) of the tangent.
	 */

	public Point[] getTangent1(){
		return new Point[]{tangent1.getA(), tangent1.getContactPoint(),  tangent1.getB()};
	}
	
	/**
	 * Gets the second tangent.
	 *
	 * @return Array of the two end points (index 0 and 2) and the contact point 
	 * (index 1) of the tangent.
	 */

	public Point[] getTangent2() {
		return new Point[]{tangent2.getA(), tangent2.getContactPoint(),  tangent2.getB()};
	}
	
	
	/**
	 * Gets the number of antipodal pairs with distinguishable end points.
	 *
	 * @return the number of antipodal pairs with distinguishable end points.
	 */
	public int getNumberOfAntipodalPairs() {
		return quadrangleSequence.size();
	}
	
	

	/**
	 * Increases the angle by the constant delta.
	 */
	private void increaseAngle() {
		angle += delta;
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
		QuadranglePoint quadranglePoint;
        
		/**
		 * The angleOffset is added to the angle. In our case this is PI, 
		 * so it is guaranteed that both tangents of the tangent pair are parallel.
		 */
		private double angleOffset;
		
		/**
		 * The constructor of a tangent takes the angle 
		 * offset and the quadrangle point the contact point
		 * is identified with.
		 *
		 * @param angleOffset the offset for the angle
		 * @param quadranglePoint the quadrangle point the
		 * contact point is identified with.
		 */
		public Tangent(double angleOffset, QuadranglePoint quadranglePoint) {
			this.angleOffset = angleOffset;
			this.quadranglePoint = quadranglePoint;
		}
		
		
		/**
	     *
		 * Calculates the angle of the tangent if it
		 * touches the contact point and the next hull point.
		 *
		 * @return the angle in radians.
		 */
		
		
		public double calculateAngle() {
			return getAngle(getContactPoint(), getNextHullPoint()) - angleOffset;
		}
		

		/**
		 * Gets the angle of a tangent with given
		 * contact point that touches 
		 * another point p.
		 *
		 * @param contact the contact point of the tangent
		 * @param p another point which is touched by the tangent
		 * @return the angle
		 */
		private  double  getAngle(Point contact, Point p) {
			double dx = contact.getX() - p.getX();
			double dy = p.getY() - contact.getY();
			double result =  Math.atan2(dx, dy);
			return result;
		}


		
		
        /**
         * Returns true if the characteristic of a tangent is maintained. 
         * That is, one or two points lie on the tangent and all other points, 
         * if they exist, lie on exactly one side of the tangent.
         * 
         * @return True, if the next angle is still maintaining the 
         * characteristic of the tangent described above.
         * False otherwise.
         */
		private boolean nextAngleIsValid() {
			if(quadrangleSequence.longestDiameterIsZero()) {
				return true;
			}
			else {
				Point contact = getContactPoint();
				Point nextB = getNextB();
				Point previousHullPoint = quadrangleSequence.getHullPointBefore(quadranglePoint);

				long result = Point.signedTriangleArea(contact, nextB, previousHullPoint);
				return result <= 0;
			}
		}
		
		/**
		 * Returns true, if the current angle is valid for 
		 * the given contact point.
		 *
		 * @return true, if successful
		 */
		private boolean angleIsValid() {
			Point contact = getContactPoint();
			
			Point a = getA();
			Point b = getB();
			
			Point previousHullPoint = quadrangleSequence.getHullPointBefore(quadranglePoint);
			Point nextHullPoint = quadrangleSequence.getHullPointAfter(quadranglePoint);
			
			
			long result1 = Point.signedTriangleArea(contact, b, previousHullPoint);
			long result2 = Point.signedTriangleArea(nextHullPoint, a, contact);
			
			return (result1 <= 0) && (result2 <= 0);
		}
		
		
		
		

		/**
		 * Gets the contact point of the tangent.
		 *
		 * @return The contact point
		 */
		public Point getContactPoint() {
			return quadrangleSequence.getHullPoint(quadranglePoint);
		}
		
		/**
		 * Returns the hull point which follows the 
		 * contact point moving clockwise.
		 *
		 * @return the next hull point
		 */
		public Point getNextHullPoint() {
			return quadrangleSequence.getHullPointAfter(quadranglePoint);
		}
		
		

	
		/**
		 * Gets the length of the tangent
		 *
		 * @return the length of the tangent
		 */
		
		@SuppressWarnings("unused")
		public double getLength() {
			return length;
		}

		
		/**
		 * Gets end point A of the tangent for the current angle.
		 * @return end point a of the tangent
		 */
		public Point getA() {
			int x = (int)Math.round(getContactPoint().getX() - length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(getContactPoint().getY() + length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		}
		
		/**
		 * Gets end point A of the tangent for angle + delta.
		 *
		 * @param delta the delta, by which the angle is increased.
		 * @return One end of the tangent
		 */
		@SuppressWarnings("unused")
		private Point getNextA(double delta) {
			int x = (int)Math.round(getContactPoint().getX() - length/2 * Math.sin(angle + angleOffset + delta));
			int y = (int)Math.round(getContactPoint().getY() + length/2 * Math.cos(angle + angleOffset + delta));
			return new Point(x, y);
		}
		
		/**
		 * Gets end point B of the tangent for the current angle.
		 * @return One end of the tangent
		 */
		public Point getB() {
		
			int x = (int)Math.round(getContactPoint().getX() + length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(getContactPoint().getY() - length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		}
		
		/**
		 * Gets end point B of the tangent for angle + delta.
		 *
		 * @return One end of the tangent
		 */
		private Point getNextB() {
			
			int x = (int)Math.round(getContactPoint().getX() + length/2 * Math.sin(angle + angleOffset + delta));
			int y = (int)Math.round(getContactPoint().getY() - length/2 * Math.cos(angle + angleOffset + delta));
			return new Point(x, y);
		}
	}
}
