package propra22.q8493367.main;

import java.awt.event.WindowEvent;

import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.point.GenerationEvent;

public interface IMainWindowListener {

	void FileEventOccured(FileEvent e);
	void commandEventOccured(CommandEvent commandEvent);
	void editEventOccured();
	void insertRandomPointsEventOccured(GenerationEvent generationEvent);
	void showManualEventOccured();
	
}
