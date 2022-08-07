package propra22.q8493367.usecases;

import propra22.q8493367.util.FileEvent;

/**
 * The Interface for a file manager. It provides 
 * a method which handles a file event.
 */
public interface IFileManager {
	
	/**
	 * Handles a file event.
	 *
	 * @param e the file event
	 */
	void handleFileEvent(FileEvent e);	
}
