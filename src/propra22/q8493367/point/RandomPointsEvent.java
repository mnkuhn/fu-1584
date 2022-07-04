package propra22.q8493367.point;



/**
 * The Class RandomPointsEvent is used 
 * to describe an event invoked by user input
 * to insert a certain number of randomly 
 * generated points into the point set.
 */
public class RandomPointsEvent {
	
	/** The type of  the random event*/
	private final RandomPointsEventType type;
	
	/** The minimum x coordinate for the randomly 
	 * generated point in the model i.e. the point set
	 */
	private final int minX;
	
	/** The minimum y coordinate for the randomly
	 * generated point in the model i.e. the point set
	 */
	private final int minY; 
	
	/** The maximum x coordinate for the randomly
	 * generated point in the model i.e. the point set
	 */
	private final int maxX;
	
	/** The maximum y coordinate for the randomly
	 * generated point in the model i.e. the point set
	 */
	private final int maxY;
	
	/**
	 * Instantiates a new random points event.
	 *
	 * @param type the type of the random event
	 * @param minX the minimum x coordinate for all randomly generated points
	 * @param minY the minimum y coordinate for all randomly generated points
	 * @param maxX the maximum x coordinate for all randomly generated points
	 * @param maxY the maximum y coordinate for all randomly generated points
	 */
	public RandomPointsEvent(RandomPointsEventType type, 
			int minX, 
			int minY, 
			int maxX,
			int maxY) {
		this.type = type;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	

	/**
	 * Gets minimum x coordinate for all randomly generated points
	 *
	 * @return the minimum x coordinate
	 */
	public int getMinX() {
		return minX;
	}



	/**
	 * Gets the minimum y coordinate for all randomly generated points 
	 *
	 * @return the minimum y coordinate
	 */
	public int getMinY() {
		return minY;
	}



	/**
	 * Gets the maximum x coordinate for all randomly generated points 
	 *
	 * @return the maximum x coordinate
	 */
	public int getMaxX() {
		return maxX;
	}



	/**
	 *  Gets the maximum y coordinate for all randomly generated points 
	 *
	 * @return the maximum y coordinate
	 */
	public int getMaxY() {
		return maxY;
	}



	
	/**
	 * Gets the type of the random event.
	 *
	 * @return the type of the random event
	 */
	public RandomPointsEventType  getType(){
		return type;
	}
}
