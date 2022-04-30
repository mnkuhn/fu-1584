package propra22.q8493367.file;

public class FileEvent implements IFileEvent {
	private FileEventType fileEventType;
	
	public FileEvent(FileEventType fileEventType) {
		this.fileEventType = fileEventType;
	}
	
	@Override
	public FileEventType getFileEventType() {
		return fileEventType;
	}
}
