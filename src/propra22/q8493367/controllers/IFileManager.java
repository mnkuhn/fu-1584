package propra22.q8493367.controllers;

import propra22.q8493367.usecases.FileEvent;

/**
 * The Interface for the file manager. It provides 
 * a method which handles all file events like 
 * opening a file, saving a file and saving a file 
 * before terminating the
 * program.
 * 
 */
public interface IFileManager {
	
	/**
	 * Handles a file event.
	 *
	 * @param e the file event
	 */
	void handleFileEvent(FileEvent e);	
}
