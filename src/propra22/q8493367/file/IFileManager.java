package propra22.q8493367.file;


/**
 * The Interface IFileManager provides a method
 * which handles a FileEvent.
 * 
 */
public interface IFileManager {
	
	/**
	 * Handle the file event.
	 *
	 * @param the handled file event
	 */
	void handleFileEvent(FileEvent e);



	void addObserver(IFileManagerObserver observer);


	void removeObserver(IFileManagerObserver observer);


	void notifyObservers();

	
}
