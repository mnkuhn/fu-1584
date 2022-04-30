package propra22.q8493367.main;

import java.io.File;
import java.net.URL;

public interface IMainWindow {

	String showSaveFileChooser();
	void setMainWindowListener(IMainWindowListener mainWindowListener);
	File showOpenFileChooser();
	int showSaveToFileOptionPane();
	void setUndoEnabled(boolean b);
	void setRedoEnabled(boolean b);
	
	void showManual(URL url);
	int getFileChooserOption();

}
