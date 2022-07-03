package propra22.q8493367.animation;

import java.util.List;

import propra22.q8493367.convex.IHull;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

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
     * The first tangent
     * @see Tangent
     */
    private Tangent tangent1;
	
    /**
     * The second Tangent
     * @see Tangent
     */
    private Tangent tangent2;
	
    /**
     * The length of the tangent in pixels
     */
    private float length;
    
    /**
     * The points of the convex
     * hull in a list.
     */
	private List<IPoint> hullAsList;
	
	/**
	 * The constructor sets angle to 0 with respect to 
	 * the vertical line going through the 
	 * contact point. The angle increases, when the
	 * tangent turns counterclockwise.
	 */
	
	public TangentPair() {
		angle = 0;
		length = 400;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(IHull hull){
		this.angle = 0;
		this.hullAsList = hull.toList();
		tangent1 = new Tangent(0, 0);
		tangent2 = new Tangent(hull.getIndexOfRightMostPoint(), Math.PI);	
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
	private boolean nextAngleOfTangentIsValid(Tangent tangent) {
		return tangent.nextAngleIsValid();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void step() {
		
		if(nextAngleOfTangentIsValid(tangent1) && nextAngleOfTangentIsValid(tangent2)){
			increaseAngle();
		}
		else {
			while(!nextAngleOfTangentIsValid(tangent1)) {
				tangent1.stepToPreviousHullPoint();
				}
			
			while(!nextAngleOfTangentIsValid(tangent2)) {
				tangent2.stepToPreviousHullPoint();
			}
			increaseAngle();
		}

	}
	
	/**
	 * Increases the angle by the constant diff
	 */
	private void increaseAngle() {
		angle += diff;
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
     * This class represents one tangent. The angle is part
     * of the surrounding class TangentPair.
     */
	private class Tangent{
		/**
		 * This index represents the index of the point of the convex hull, 
		 * which is the point of contact.
		 */
		private int centerIndex;
        
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
		public Tangent(int centerIndex, double angleOffset) {
			
			this.centerIndex = centerIndex;
			this.angleOffset = angleOffset;
		}
		
		/**
		 * Moves the contact point to the previous point in
		 * the convex hull. We need to take the previous point 
		 * because we want the tangents to turn counterclockwise
		 * and we want to use a standard cartesian coordinate 
		 * system in the GUI.
		 */
		private void stepToPreviousHullPoint() {
			centerIndex = Math.floorMod(centerIndex - 1, hullAsList.size());
			
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
			long result = Point.signedTriangleArea(getCenter(), getNextB(diff), getPreviousHullPoint());
			// ist <=0 richtig?
			return result <= 0;
		}

		/**
		 * Gets the contact point of the tangent
		 * @return - The contact point
		 */
		public IPoint getCenter() {
			return hullAsList.get(centerIndex % hullAsList.size());
		}

	
		/**
		 * Gets the length of the tangent in pixels
		 * @return - The length
		 */
		public float getLength() {
			return length;
		}

		
		/**
		 * Gets end A of the tangent as a point.
		 * @return - One end of the tangent
		 */
		public IPoint getA() {
			int x = (int)Math.round(getCenter().getX() - length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(getCenter().getY() + length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		} 
		
		/**
		 * Gets end B of the tangent as a point.
		 * @return - One end of the tangent
		 */
		public IPoint getB() {
		
			int x = (int)Math.round(getCenter().getX() + length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(getCenter().getY() - length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		}
		
		/**
		 * Gets end B of the tangent as a point for angle + pDiff.
		 * @return - One end of the tangent
		 */
		private IPoint getNextB(double pDiff) {
			int x = (int)Math.round(getCenter().getX() + length/2 * Math.sin(angle + angleOffset + pDiff));
			int y = (int)Math.round(getCenter().getY() - length/2 * Math.cos(angle + angleOffset + pDiff));
			return new Point(x, y);
		}
		
		/**
		 * Gets end A of the tangent as a point for angle + pDiff.
		 * @return - One end of the tangent
		 */
		private IPoint getNextA(double pDiff) {
			int x = (int)Math.round(getCenter().getX() - length/2 * Math.sin(angle + angleOffset + pDiff));
			int y = (int)Math.round(getCenter().getY() + length/2 * Math.cos(angle + angleOffset + pDiff));
			return new Point(x, y);
		}
		
		/**
		 * Gets the next point in the convex hull turning clockwise 
		 * since the GUI is based on a standard cartesian coordinate system.
		 * @return - The next point in the convex hull
		 * 
		 */
		private IPoint getNextHullPoint() {
			
			return hullAsList.get(Math.floorMod(centerIndex + 1, hullAsList.size()));
		}
		
		/**
		 * Gets the next point in the convex hull turning
		 * counterclockwise, since the GUI is based on a standard
		 * cartesian coordinate system.
		 *
		 * @return - The next point in the convex hull
		 */
		private IPoint getPreviousHullPoint() {
			int index = Math.floorMod(centerIndex - 1, hullAsList.size());
			return hullAsList.get(index);
		}	
	}
}
