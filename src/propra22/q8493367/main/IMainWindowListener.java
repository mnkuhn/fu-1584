package propra22.q8493367.main;



import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.point.RandomPointsEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving file events, command
 * events and random point events.
 *
 * 
 */
public interface IMainWindowListener {
     
	
	
	/**
	 * File event occured -> The user wants to open or save a file
	 * or exit the application.
	 *
	 * @param e - the file event
	 */
	void FileEventOccured(FileEvent e);
	
	/**
	 * Command event occured -> The user wants to 
	 * undo or redo the last command.
	 *
	 * @param commandEvent - the command event
	 */
	void commandEventOccured(CommandEvent commandEvent);
	
	/**
	 * Edits the event occured.
	 */
	void editEventOccured();
	
	/**
	 * Insert random points event occured.
	 *
	 * @param randomPointsEvent - the random points event.
	 */
	void insertRandomPointsEventOccured(RandomPointsEvent randomPointsEvent);
	
	/**
	 * Show manual event occured.
	 */
	void showManualEventOccured();

	
	/**
	 * A view event has occured.
	 * 
	 * @param viewEvent the view event which has occured. 
	 * This event determines, which shapes (contour polygon, diameter, quadrangle, triangle)
	 * are shown and if the animation should be running.
	 */
	void viewEventOccured(ViewEvent viewEvent);
    
	
	/**
	 * This method is called, when the user wants to center
	 * the representation in the view.
	 */
	void centerViewEventOccured();
	
}
