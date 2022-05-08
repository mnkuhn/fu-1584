package propra22.q8493367.main;

import java.awt.event.WindowEvent;

import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.point.RandomPointsEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving IMainWindow events.
 * The class that is interested in processing a IMainWindow
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIMainWindowListener<code> method. When
 * the IMainWindow event occurs, that object's appropriate
 * method is invoked.
 *
 * @see IMainWindowEvent
 */
public interface IMainWindowListener {
     
	
	
	/**
	 * File event occured.
	 *
	 * @param e the e
	 */
	void FileEventOccured(FileEvent e);
	
	/**
	 * Command event occured.
	 *
	 * @param commandEvent the command event
	 */
	void commandEventOccured(CommandEvent commandEvent);
	
	/**
	 * Edits the event occured.
	 */
	void editEventOccured();
	
	/**
	 * Insert random points event occured.
	 *
	 * @param generationEvent the generation event
	 */
	void insertRandomPointsEventOccured(RandomPointsEvent generationEvent);
	
	/**
	 * Show manual event occured.
	 */
	void showManualEventOccured();
	
}
