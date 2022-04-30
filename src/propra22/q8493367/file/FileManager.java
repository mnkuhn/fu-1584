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
	
	/** The view. */
	IMainWindow view;
	
	/** The file path. */        // dieser Pfad muss noch relativ werden
	private String filePath = null;
	
	/**
	 * Instantiates a new fileManager.
	 *
	 * @param the controller of the drawPanel
	 * @param the main window
	 */
	public FileManager(IDrawPanelController drawPanelController, IMainWindow view) {
		this.drawPanelController = drawPanelController;
		this.view = view;
	}
	
	/**
	 * Handles a FileEvent
	 * @param the handled FileEvent
	 */
	@Override
	public void handleFileEvent(FileEvent e) {
		
		FileEventType type = e.getFileEventType();
    	
		switch (type) {
		
		case NEW: {
			if(!drawPanelController.drawPanelModelIsEmpty() && drawPanelController.dataChangedSinceLastSave()) {
				
				int dialogOption = view.showSaveToFileOptionPane();
				if(dialogOption == JOptionPane.OK_OPTION) {
					if(filePath != null) {
						drawPanelController.saveModel(filePath);
						}
					else {
						String newFilePath = view.showSaveFileChooser();
						if(view.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
							if(newFilePath != null) {
								drawPanelController.saveModel(newFilePath);
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
            if(!drawPanelController.drawPanelModelIsEmpty()) {
            	int dialogOption = view.showSaveToFileOptionPane();
            	if(dialogOption == JOptionPane.OK_OPTION) {
            		if(filePath != null) {
            			drawPanelController.saveModel(filePath);
            			openFile();
            		}
            		else {
						String newFilePath = view.showSaveFileChooser();
						if(view.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
							if(newFilePath != null) {
							drawPanelController.saveModel(newFilePath);
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
			
			if(filePath != null) {
				drawPanelController.saveModel(filePath);
			}
			else {
				String path = view.showSaveFileChooser();
				if(view.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
					if(path != null) {
					saveFile(path);
					}
				}	
			}
			break;
		}
		
		case SAVE_AS: {
			String path = view.showSaveFileChooser();
			if(view.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
				if(path != null) {
					saveFile(path);
				}	
			}
			break;
		}
			
		case EXIT: {
		
			if(drawPanelController.dataChangedSinceLastSave()) {
				int dialogOption = JOptionPane.showConfirmDialog (null, "Datei speichern?", "", 
					JOptionPane.YES_NO_CANCEL_OPTION);
				if(dialogOption == JOptionPane.OK_OPTION) {
					if(filePath != null) {
						drawPanelController.saveModel(filePath);
						System.exit(0);
					}
					else {
						String path = view.showSaveFileChooser();
						if(view.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
							if(path != null) {
								drawPanelController.saveModel(path);
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

	/**
	 * Saves the data of the model into a file
	 *
	 * @param path the path
	 */
	private void saveFile(String path) {
		drawPanelController.saveModel(path);
		filePath = path;
	}

	/**
	 * Loads the data of a file into the model.
	 */
	private void openFile() {
		File file = view.showOpenFileChooser();
		if(file != null) {
			drawPanelController.loadPointsToModel(file);
			drawPanelController.updateModel();
			drawPanelController.updateView();
			filePath = file.getAbsolutePath();
		}
	}
}
