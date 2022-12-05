package usecases;


/**
 * The Enum FileEventType specifies the 
 * type of a file event.
 */
public enum FileEventType {
	
	/** Create a new draw panel and remove 
	 * all points from the point set
	 */
	NEW, 
	/** Load the points from the file into the point set*/
	OPEN, 
	/** Save points of the point set to a file*/
	SAVE, 
	/** Save the points of the point 
	 * set to a new file 
	 */
	SAVE_AS, 
	/** Exit the application */
	EXIT;
}
