package propra22.q8493367.main;

import java.io.File;
import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMainWindow for all main windows
 * of the application.
 */
public interface IMainWindow {

	/**
	 * Shows the file chooser to save to a file
	 *
	 * @return the string
	 */
	String showSaveFileChooser();
	
	/**
	 * Sets the main window listener.
	 *
	 * @param mainWindowListener the new main window listener
	 */
	void setMainWindowListener(IMainWindowListener mainWindowListener);
	
	/**
	 * Shows the file chooser to open a file
	 *
	 * @return the file
	 */
	File showOpenFileChooser();
	
	/**
	 * Shows an option pane which gives the 
	 * user the option to save to a file
	 *
	 * @return the int
	 */
	int showSaveToFileOptionPane();
	
	/**
	 * Sets the undo item in the edit menu 
	 * enabled or disabled.
	 *
	 * @param b - if true, undo is enabled 
	 * otherwise it is disabled.
	 */
	void setUndoEnabled(boolean b);
	
	/**
	 * Sets the redo item in the edit menu
	 * enabled or disabled.
	 *
	 * @param b - if tue, redo is enabled
	 * otherwise it is disabled.
	 */
	void setRedoEnabled(boolean b);
	
	/**
	 * Shows the user manual.
	 *
	 * @param url - the url of the user manual
	 */
	void showManual(URL url);
	
	/**
	 * Gets the file chooser option.
	 *
	 * @return JFileChooser.CANCEL_OPTION if user wants to cancel
	 * or JFileChooser.APPROVE_OPTION if user approves to save or open
	 * file.
	 */
	int getFileChooserOption();

}
