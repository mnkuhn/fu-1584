package propra22.q8493367.main;



import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.point.RandomPointsEvent;


/**
 * The listener for a main window. It receives all the events generated
 * by the users selections in the file menu, the edit menu, the view menu and 
 * the help menu.
 */
public interface IMainWindowListener {
     
	
	
	/**
	 * The user wants to open or save a file
	 * or exit the application.
	 *
	 * @param e the file event
	 */
	void FileEventOccured(FileEvent e);
	
	/**
	 * The user wants to undo or redo the last command.
	 *
	 * @param commandEvent the command event
	 */
	void commandEventOccured(CommandEvent commandEvent);
	
	/**
	 * This method checks, if there are commands that can 
	 * be undone or redone. It activates the corresponding
	 * menu items.
	 */
	void editEventOccured();
	
	/**
	 * The user wants to insert a certain number of randomly generated
	 * points into the point set.
	 *
	 * @param randomPointsEvent the random points event.
	 */
	void insertRandomPointsEventOccured(RandomPointsEvent randomPointsEvent);
	
	/**
	 * The user wants to look through the operating instructions.
	 */
	void showManualEventOccured();

	
	/**
	 * The user wants to make a selection among the shapes that can be displayed.
	 * 
	 * @param viewEvent the view event which has occurred. 
	 * This event determines, which shapes (contour polygon, diameter, quadrangle, triangle)
	 * are shown and if the animation should be displayed.
	 */
	void viewEventOccured(ViewEvent viewEvent);
    
	
	/**
	 * This method is called, when the user wants to center
	 * the representation in the view.
	 */
	void centerViewEventOccured();
	
}
