package propra22.q8493367.usecases;


/**
 * The Enum RandomPointsEventType represents the type
 * of an event which occurs when the user wants to
 * insert 10, 50, 100, 500 oder 1000 randomly generated
 * points into the point set.
 */
public enum RandomPointsEventType {
	
	/** Ten points are to be generated. */
	TEN(10), 
	/** Fifty points are to be generated. */
	FIFTY(50), 
	/** Hundred points are to be generated */
	HUNDRED(100), 
	/** Five hundred points are to be generated. */
	FIVEHUNDRED(500), 
	/** Thousand points are to be generated. */
	THOUSAND(1000);
	
	/** The number of points to be generated */
	private final int number;
	
	/**
	 * The private constructor for
	 * RandomPointsEventType
	 *
	 * @param number the number of points to be generated.
	 */
	private RandomPointsEventType(int number) {
		this.number = number;
	}
	
	/**
	 * Returns the number of points to be randomly generated.
	 *
	 * @return the number of points to be randomly generated.
	 */
	public int getNumber() {
		return number;
	}
}
