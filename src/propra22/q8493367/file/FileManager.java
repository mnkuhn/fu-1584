package propra22.q8493367.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import propra22.q8493367.contour.IPointSet;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.main.IMainWindow;
import propra22.q8493367.main.IMainWindowListener;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.settings.Settings;


/**
 * The Class FileManager handles all tasks related to creating a new file,
 * opening a file, saving a file and saving a file before terminating the
 * program.
 */
public class FileManager implements IFileManager {
	
	/** The draw panel controller. */
	private IDrawPanelController drawPanelController;
	
	/** The point set. */
	private IPointSet pointSet;
	
	/** The parser which is used for reading a file */
	private IParser parser;

	/** The suffix of the files used by this application. */
	private String suffix = "points";
	

	/** The file path of the currently opened file. */
	private String filePath = null;

	/** The file chooser. */
	private FileChooser fileChooser = new FileChooser(Settings.defaultFilePath);

	
	/**
	 * Instantiates a new file manager.
	 *
	 * @param pointSet the point set
	 * @param drawPanelController the draw panel controller
	 * @param parser the parser which is used for reading a file
	 */
	public FileManager(IPointSet pointSet, IDrawPanelController drawPanelController, IParser parser) {
		
		this.pointSet = pointSet;
		this.drawPanelController = drawPanelController;
		this.parser = parser;
	}
	

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleFileEvent(FileEvent e) {
        
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
			int dialogOption = JOptionPane.DEFAULT_OPTION;
			if (!pointSet.isEmpty() && pointSet.hasChanged()) {
				dialogOption = showSaveToFileOptionPane();
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
					}
				}
				if (dialogOption == JOptionPane.CANCEL_OPTION) {
					break;
				}
			}

			if (dialogOption != JOptionPane.CANCEL_OPTION) {
				fileChooser.setSelectedFile(new File(""));
				int fileChooserOption = fileChooser.showOpenDialog(null);
				if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					if(selectedFile != null) {
						loadPointsToPointSet(selectedFile);
						pointSet.setHasChanged(false);
						drawPanelController.reset();
					}
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
				savePointSet(fileChooser.getSelectedFile());
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
	 * @param file the file in which the point set will be saved.
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
				IPoint point = pointSet.getPointAt(i);
				printWriter.println(point.toString());
			}
			printWriter.close();
			pointSet.setHasChanged(false);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Loads the points from a file to the point set.
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
				IPoint point = parser.parseLine(line);
				if (point != null) {
					pointSet.addPoint(point);
				}
			}
			reader.close();
			pointSet.setHasChanged(true);
			long end = System.currentTimeMillis();
			System.out.println("Punktmenge von Datei einlesen: " + (end - start) + " ms");
			// for FileReader(file)
		} catch (FileNotFoundException e) {
			// show dialog
			e.printStackTrace();
			// for readLine()
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	

	
	/**
	 * Shows an option pane by which the user is asked
	 * if he wants to save the points of the point set 
	 * into a file.
	 *
	 * @return the option chosen by the user (yes or no).
	 */
	private int showSaveToFileOptionPane() {
		int choice = JOptionPane.showConfirmDialog(null, "In Datei speichern?", "", JOptionPane.YES_NO_CANCEL_OPTION);
		return choice;
	}

}
