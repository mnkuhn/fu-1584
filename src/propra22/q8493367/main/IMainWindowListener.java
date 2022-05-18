package propra22.q8493367.main;

import java.awt.event.WindowEvent;

import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.point.RandomPointsEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving file events, command
 * events and random point events.
 *
 * @see IFileEvent
 * @see ICommandEvent
 * @see IRandomPointsEvent
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
	void insertRandomPointsEventOccured(RandomPointsEvent randomPointsEvent);
	
	/**
	 * Show manual event occured.
	 */
	void showManualEventOccured();

	void viewEventOccured(IViewEvent viewEvent);
	
}
