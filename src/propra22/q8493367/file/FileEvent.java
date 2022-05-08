package propra22.q8493367.file;


// TODO: Auto-generated Javadoc
/**
 * The Class FileEvent specifies an
 * event induced by the user by creating a new file,
 * by opening or saving a file or by exciting the
 * application.
 */
public class FileEvent implements IFileEvent {
	
	/** The file event type. */
	private FileEventType fileEventType;
	
	/**
	 * Instantiates a new file event.
	 *
	 * @param fileEventType - type of the file event
	 */
	public FileEvent(FileEventType fileEventType) {
		this.fileEventType = fileEventType;
	}
	
	/**
	 * Gets the file event type.
	 *
	 * @return the file event type
	 */
	@Override
	public FileEventType getFileEventType() {
		return fileEventType;
	}
}
