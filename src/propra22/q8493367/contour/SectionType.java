package propra22.q8493367.contour;

// TODO: Auto-generated Javadoc
/**
 * SectionType identifies the 4 sections of the contour polygon i.e. the convex hull.
 * It also provides a sign method which is used to determine the sign of the result of the DFV algorithm
 */

public enum SectionType {
	
	/** points starting at point with lowest x coordinate and highest y coordinate ordered by rising x coordinate and rising y coordinate */
	LOWERLEFT(-1L), 
	
	/** points starting at point with lowest x coordinate and highest y coordinate ordered by rising x coordinate and rising y coordinate*/
	LOWERRIGHT(1L), 
	
	/** points starting at point with lowest x coordinate and highest y coordinate ordered by rising x coordinate and falling y coordinate. */
	UPPERRIGHT(-1L), 
	
	/** points starting at point with lowest x coordinate and highest y coordinate ordered by rising x coordinate and falling y coordinate */
	UPPERLEFT(1L);
	
	/** The sign of the SectionType which is -1 or 1
	 */
	private long sign;
	
	/**
	 * Private constructor for SectionType.
	 *
	 * @param sign - the sign which is -1 or 1
	 */
	private SectionType(long sign) {
		this.sign = sign;
	}
	
	/**
	 * Gets the sign of the SectionType
	 *
	 * @return the sign which is -1 or 1
	 */
	public long getSign() {
		return sign;
	}
}
