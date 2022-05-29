package propra22.q8493367.file;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.main.IMainWindow;



/**
 * The Class FileManager handles all tasks related to creating a new file, opening a
 * file, saving a file and saving a file before terminating the program.
 */
public class FileManager implements IFileManager {
	
	/** The draw panel controller.*/
	IDrawPanelController drawPanelController;
	
	/** The parser for /*
	IParser parser = new Parser();
	
	/** The main view. */
	IMainWindow mainView;
	
	/** The file path. */
	private String filePath = null;
	
	/** Data has changed since last save to file */
	private boolean dataChangedSinceLastSave = false;
	
	/**
	 * Instantiates a new fileManager.
	 *
	 * @param the controller of the drawPanel
	 * @param the main window
	 */
	public FileManager(IDrawPanelController drawPanelController, IMainWindow view) {
		this.drawPanelController = drawPanelController;
		this.mainView = view;
	}
	
	/**
	 * Handles a FileEvent
	 * @param e - the handled FileEvent
	 */
	@Override
	public void handleFileEvent(FileEvent e) {
		
		FileEventType type = e.getFileEventType();
    	
		switch (type) {
		case NEW: {
			if(!drawPanelController.pointSetIsEmpty() && drawPanelController.dataHasChangedSinceLastSave()) {
				
				int dialogOption = mainView.showSaveToFileOptionPane();
				if(dialogOption == JOptionPane.OK_OPTION) {
					if(filePath != null) {
						drawPanelController.savePointSet(filePath);
						}
					else {
						String newFilePath = mainView.showSaveFileChooser();
						if(mainView.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
							if(newFilePath != null) {
								drawPanelController.savePointSet(newFilePath);
								drawPanelController.createNewDrawPanel();
								filePath = null;
							}
						}
					}	
				}
				if(dialogOption == JOptionPane.NO_OPTION) {
					drawPanelController.createNewDrawPanel();
					filePath = null;
				}
			}
			else {
				drawPanelController.createNewDrawPanel();
				filePath = null;
			}		
		break;
		}
		
		case OPEN: {
            
			if(!drawPanelController.pointSetIsEmpty() && drawPanelController.dataHasChangedSinceLastSave()) {
            	int dialogOption = mainView.showSaveToFileOptionPane();
            	if(dialogOption == JOptionPane.OK_OPTION) {
            		if(filePath != null) {
            			drawPanelController.savePointSet(filePath);
            			openFile();
            		}
            		else {
						String newFilePath = mainView.showSaveFileChooser();
						if(mainView.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
							if(newFilePath != null) {
							drawPanelController.savePointSet(newFilePath);
							openFile();
							}
						}	
					}
            	}
            	if(dialogOption == JOptionPane.NO_OPTION) {
            		openFile();
            	}
			}
            else {
            	openFile();
    		}
			break;
		}
		
		case SAVE: {
			if(drawPanelController.dataHasChangedSinceLastSave()) {
				if(filePath != null) {
					drawPanelController.savePointSet(filePath);
				}
				else {
					String path = mainView.showSaveFileChooser();
					if(mainView.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
						if(path != null) {
						saveFile(path);
						}
					}	
				}
			}
			break;
		}
		
		case SAVE_AS: {
			String path = mainView.showSaveFileChooser();
			if(mainView.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
				if(path != null) {
					saveFile(path);
				}	
			}
			break;
		}
			
		case EXIT: {
		
			if(drawPanelController.dataHasChangedSinceLastSave()) {
				int dialogOption = JOptionPane.showConfirmDialog (null, "Datei speichern?", "", 
					JOptionPane.YES_NO_CANCEL_OPTION);
				if(dialogOption == JOptionPane.OK_OPTION) {
					if(filePath != null) {
						drawPanelController.savePointSet(filePath);
						System.exit(0);
					}
					else {
						String path = mainView.showSaveFileChooser();
						if(mainView.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
							if(path != null) {
								drawPanelController.savePointSet(path);
								System.exit(0);	
							}
						}
					}	
				}
				if(dialogOption == JOptionPane.NO_OPTION) {
					System.exit(0);
					}
				}
			else {
				System.exit(0);
				}
			break;
			}
		}
	}

	//Saves the data of the model into a file
	private void saveFile(String path) {
		drawPanelController.savePointSet(path);
		filePath = path;
	}

	/**
	 * Loads the data of a file into the model.
	 */
	private void openFile() {
		File file = mainView.showOpenFileChooser();
		if(file != null) {
			drawPanelController.loadPointsToPointSet(file);
			drawPanelController.updateModel();
			drawPanelController.updateView();
			filePath = file.getAbsolutePath();
		}
	}
}
