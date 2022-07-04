package propra22.q8493367.file;


// TODO: Auto-generated Javadoc
/**
 * The Interface IFileManager provides a method
 * which handles a FileEvent.
 * 
 */
public interface IFileManager {
	
	/**
	 * Handles a file event.
	 *
	 * @param e the file event
	 */
	void handleFileEvent(FileEvent e);


    
	/**
	 * Adds a file manager observer.
	 *
	 * @param observer the observer which is added.
	 */
	void addObserver(IFileManagerObserver observer);


	/**
	 * Removes a file manager observer.
	 *
	 * @param observer the observer which is removed.
	 */
	void removeObserver(IFileManagerObserver observer);


	/**
	 * Notifies all file manager observers.
	 */
	void notifyObservers();

	
}
