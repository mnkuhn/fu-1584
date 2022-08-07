package propra22.q8493367.util;


/**
 * The Enum RandomPointsEventType represents the type
 * of an event which occurs when the user wants to
 * insert 10, 50, 100, 500 oder 1000 randomly generated
 * points into the point set.
 */
public enum RandomPointsEventType {
	
	/** Ten points are inserted. */
	TEN(10), 
	/** Fifty points are inserted. */
	FIFTY(50), 
	/** Hundred points are inserted */
	HUNDRED(100), 
	/** Five hundred points are inserted. */
	FIVEHUNDRED(500), 
	/** Thousand points are inserted. */
	THOUSAND(1000);
	
	/** The number of points to be inserted */
	private final int number;
	
	/**
	 * The private constructor for
	 * RandomPointsEventType
	 *
	 * @param number the number of points
	 */
	private RandomPointsEventType(int number) {
		this.number = number;
	}
	
	/**
	 * Gets the number of points to be randomly generated.
	 *
	 * @return the number of points to be randomly generated.
	 */
	public int getNumber() {
		return number;
	}
}
