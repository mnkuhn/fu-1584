package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import controllers.IDrawPanelController;
import controllers.IFileManager;
import entities.Point;
import entities.PointSet;
import main.Main;
import usecases.FileEvent;
import usecases.FileEventType;
import usecases.FileSettings;
import util.IParser;


/**
 * The Class FileManager handles all tasks related to creating a new file,
 * opening a file, saving a file and saving a file before terminating the
 * program.
 */
public class FileManager implements IFileManager {
	
	/** The draw panel controller. */
	private IDrawPanelController drawPanelController;
	
	/** The point set. */
	private PointSet pointSet;
	
	/** The parser which is used for reading a file */
	private IParser parser;

	/** The suffix of the files used by this application. */
	private String suffix = "points";
	
	/** The file path of the currently opened file. */
	private String filePath = null;

	/** The file chooser. */
	private JFileChooser fileChooser;;

	
	/**
	 * Instantiates a new file manager.
	 *
	 * @param pointSet the point set
	 * @param drawPanelController the draw panel controller
	 * @param parser the parser which is used for reading a file
	 */
	public FileManager(PointSet pointSet, IDrawPanelController drawPanelController, IParser parser) {
		
		this.pointSet = pointSet;
		this.drawPanelController = drawPanelController;
		this.parser = parser;
	}
	

	@Override
	public void handleFileEvent(FileEvent e) {
        /*
         * This if query is necessary to avoid an 'Exception while removing reference'
         * after testing. A file chooser object should only be created if a GUI
         * exists and this method is only called from the GUI.
         */
		if(fileChooser == null) {
        	fileChooser = new FileChooser(FileSettings.defaultFilePath);
        }
		
		FileEventType type = e.getFileEventType();

		switch (type) {
		case NEW: {
			if (!pointSet.isEmpty() && pointSet.hasChanged()) {
				int dialogOption = showSaveToFileOptionPane();
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(new File(filePath));
					
						// filePath == null
					} else {
						fileChooser.setSelectedFile(new File(""));
						int fileChooserOption = fileChooser.showSaveDialog(null);
						if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
							savePointSet(fileChooser.getSelectedFile());
						}
						if(fileChooserOption == JFileChooser.CANCEL_OPTION) {
							break;
						}
					}
				}
				if (dialogOption == JOptionPane.CANCEL_OPTION) {
					break;
				}
			}
			
			pointSet.clear();
			pointSet.setHasChanged(false);
			filePath = null;
			drawPanelController.reset();
			break;
		}

		case OPEN: {
			if (!pointSet.isEmpty() && pointSet.hasChanged()) {
				int dialogOption = showSaveToFileOptionPane();
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(new File(filePath));

						// filePath == null
					} else {
						// prevent the file chooser to show the last selected File
						fileChooser.setSelectedFile(new File(""));
						int fileChooserOption = fileChooser.showSaveDialog(null);
						if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
							savePointSet(fileChooser.getSelectedFile());
						}
						if (fileChooserOption == JFileChooser.CANCEL_OPTION) {
							break;
						}
					}
				}
				if (dialogOption == JOptionPane.CANCEL_OPTION) {
					break;
				}
			}

			fileChooser.setSelectedFile(new File(""));
			int fileChooserOption = fileChooser.showOpenDialog(null);
			if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				if (selectedFile != null) {
					loadPointsToPointSet(selectedFile);
					pointSet.setHasChanged(false);
					drawPanelController.reset();
				}
			}
			break;
		}

		case SAVE: {
			if (filePath != null) {
				savePointSet(new File(filePath));
			} else {
				fileChooser.setSelectedFile(new File(""));
				int fileChooserOption = fileChooser.showSaveDialog(null);
				if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
					savePointSet(fileChooser.getSelectedFile());
				}
			}
			break;
		}

		case SAVE_AS: {
			fileChooser.setSelectedFile(new File(""));
			int fileChooserOption = fileChooser.showSaveDialog(null);
			if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				savePointSet(selectedFile);
			}
			break;
		}

		case EXIT: {
	        // point set has changed since the last save
			if (pointSet.hasChanged()) {
				int dialogOption = JOptionPane.showConfirmDialog(null, "Datei speichern?", "",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(new File(filePath));
					} else {
						fileChooser.setSelectedFile(new File(""));
						int fileChooserOption = fileChooser.showSaveDialog(null);
						if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
							savePointSet(fileChooser.getSelectedFile());
						}
						if(fileChooserOption == JFileChooser.CANCEL_OPTION) {
							dialogOption = JOptionPane.CANCEL_OPTION;
						}
					}
				}
				if (dialogOption != JOptionPane.CANCEL_OPTION) {
					System.exit(0);
				}
			
			}
			// point set has not changed since the last save
			else {
				System.exit(0);
			}
			break;
		}
		}
	}
    

	/**
	 * Saves the point set to a file.
	 *
	 * @param file the file to which the point set will be saved.
	 */
	public void savePointSet(File file) {
        if(!file.getName().endsWith("." + suffix)) {
        	String path = file.getAbsolutePath() + "." + suffix;
        	file = new File(path);
        }
		try {
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			for (int i = 0; i < pointSet.getNumberOfPoints(); i++) {
				Point point = pointSet.getPointAt(i);
				printWriter.println(point.toString());
			}
			printWriter.close();
			pointSet.setHasChanged(false);
			filePath = file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Datei konnte nicht gespeichert werden", "", 
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Loads the points from a file into the point set.
	 *
	 * @param file the file, from which the points are loaded
	 */
	public void loadPointsToPointSet(File file) {
 
		try {
			
			long start = System.currentTimeMillis();
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			String line;
			pointSet.clear();

			while ((line = reader.readLine()) != null) {
				Point point = parser.parseLine(line);
				if (point != null) {
					pointSet.addUncheckedWithoutSorting(point);
				}
			}
			reader.close();
			pointSet.sortAndCheck();
			pointSet.setHasChanged(true);
			filePath = file.getAbsolutePath();
			long end = System.currentTimeMillis();
			if(Main.showConsoleOutput)
				System.out.println("Punktmenge von Datei einlesen: " + (end - start) + " ms");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Datei konnte nicht geöffnet werden.", "", 
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Datei konnte nicht gelesen werden.", "", 
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	

	
	/**
	 * Shows an option pane by which the user is asked
	 * if he wants to save the points of the point set 
	 * into a file.
	 *
	 * @return the option chosen by the user.
	 */
	private int showSaveToFileOptionPane() {
		int choice = JOptionPane.showConfirmDialog(null, "In Datei speichern?", "", JOptionPane.YES_NO_CANCEL_OPTION);
		return choice;
	}
	
	


}
